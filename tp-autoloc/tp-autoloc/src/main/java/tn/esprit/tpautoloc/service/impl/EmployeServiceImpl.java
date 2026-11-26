package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IEmployeRepository;
import tn.esprit.tpautoloc.service.IEmployeService;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;
import tn.esprit.tpautoloc.web.mapper.EmployeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {

    private static final String EMPLOYE_INTROUVABLE = "Employe";
    private static final String AGENCE_INTROUVABLE = "Agence";

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeDTO create(EmployeDTO dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel employé ne doit pas avoir d'identifiant");
        }
        verifierAttributsObligatoires(dto);

        Employe employe = employeMapper.toEntity(dto);
        employe.setAgence(chercherAgenceOuNull(dto.getAgenceId()));
        return employeMapper.toDto(employeRepository.save(employe));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeDTO findById(Long id) {
        return employeMapper.toDto(chercherEmploye(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> findAll() {
        return employeRepository.findAll().stream()
                .map(employeMapper::toDto)
                .toList();
    }

    @Override
    public EmployeDTO update(Long id, EmployeDTO dto) {
        verifierAttributsObligatoires(dto);
        Employe existant = chercherEmploye(id);
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setRole(dto.getRole());
        existant.setAgence(chercherAgenceOuNull(dto.getAgenceId()));
        return employeMapper.toDto(employeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException(EMPLOYE_INTROUVABLE, id);
        }
        employeRepository.deleteById(id);
    }

    @Override
    public EmployeDTO affecterEmployeAAgence(Long employeId, Long agenceId) {
        Employe employe = chercherEmploye(employeId);
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException(AGENCE_INTROUVABLE, agenceId));

        if (employe.getAgence() != null
                && employe.getAgence().getIdAgence().equals(agenceId)) {
            throw new IllegalStateException(
                    "L'employé " + employeId + " est déjà rattaché à l'agence " + agenceId);
        }

        employe.setAgence(agence);
        return employeMapper.toDto(employeRepository.save(employe));
    }

    @Override
    public EmployeDTO desaffecterEmployeDeAgence(Long employeId) {
        Employe employe = chercherEmploye(employeId);

        if (employe.getAgence() == null) {
            throw new IllegalStateException(
                    "L'employé " + employeId + " n'est rattaché à aucune agence");
        }

        employe.setAgence(null);
        return employeMapper.toDto(employeRepository.save(employe));
    }

    private Employe chercherEmploye(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYE_INTROUVABLE, id));
    }

    private Agence chercherAgenceOuNull(Long agenceId) {
        if (agenceId == null) {
            return null;
        }
        return agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException(AGENCE_INTROUVABLE, agenceId));
    }

    private void verifierAttributsObligatoires(EmployeDTO dto) {
        if (dto.getNom() == null || dto.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'employé est obligatoire");
        }
        if (dto.getPrenom() == null || dto.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prénom de l'employé est obligatoire");
        }
        if (dto.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'employé est obligatoire");
        }
    }
}