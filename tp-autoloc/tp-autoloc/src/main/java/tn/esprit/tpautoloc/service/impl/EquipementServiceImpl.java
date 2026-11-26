
package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;
import tn.esprit.tpautoloc.web.mapper.EquipementMapper;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IEquipementRepository;
import tn.esprit.tpautoloc.service.IEquipementService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementDTO creer(EquipementDTO dto) {
        Equipement equipement = equipementMapper.toEntity(dto);
        Equipement saved = equipementRepository.save(equipement);
        return equipementMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementDTO findById(Long id) {
        Equipement equipement = equipementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Equipement introuvable", id));

        return equipementMapper.toDto(equipement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> findAll() {
        return equipementRepository.findAll().stream()
                .map(equipementMapper::toDto)
                .toList();
    }
}
