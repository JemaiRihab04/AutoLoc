package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.web.mapper.AgenceMapper;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.service.IAgenceService;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;
    private final AgenceMapper agenceMapper;

    @Override
    public AgenceDTO creer(AgenceDTO dto) {
        Agence agence = agenceMapper.toEntity(dto);
        Agence saved = agenceRepository.save(agence);
        return agenceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AgenceDTO findById(Long id) {
        Agence agence = agenceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agence introuvable", id));
        return agenceMapper.toDto(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgenceDTO> findAll() {
        return agenceRepository.findAll().stream()
                .map(agenceMapper::toDto)
                .toList();
    }
}