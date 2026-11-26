package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;
import tn.esprit.tpautoloc.web.mapper.VehiculeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private static final String VEHICULE_INTROUVABLE = "Véhicule introuvable";
    private static final String AGENCE_INTROUVABLE = "Agence introuvable";

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO creer(VehiculeDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);

        if (dto.getAgenceId() != null) {
            vehicule.setAgence(chercherAgence(dto.getAgenceId()));
        }

        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {
        return vehiculeMapper.toDto(chercherVehicule(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toDto)
                .toList();
    }

    @Override
    public VehiculeDTO affecterVehiculeAAgence(Long vehiculeId, Long agenceId) {
        Vehicule vehicule = chercherVehicule(vehiculeId);
        Agence agence = chercherAgence(agenceId);
        if (vehicule.getAgence() != null
                && vehicule.getAgence().getIdAgence().equals(agenceId)) {
            throw new IllegalStateException(
                    "la Vehicule est déjà rattaché à l'agence " + agenceId);
        }

        vehicule.setAgence(agence);
        Vehicule saved = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(saved);
    }

    @Override
    public VehiculeDTO desaffecterVehiculeDeAgence(Long vehiculeId) {
        Vehicule vehicule = chercherVehicule(vehiculeId);
        if (vehicule.getAgence() == null) {
            throw new IllegalStateException(
                    "L'employé n'est rattaché à aucune agence");
        }


        if (vehicule.getAgence() != null) {
            vehicule.setAgence(null);
            vehicule = vehiculeRepository.save(vehicule);
        }
        return vehiculeMapper.toDto(vehicule);
    }

    private Vehicule chercherVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(VEHICULE_INTROUVABLE, id));
    }


    private Agence chercherAgence(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AGENCE_INTROUVABLE, id));
    }
}