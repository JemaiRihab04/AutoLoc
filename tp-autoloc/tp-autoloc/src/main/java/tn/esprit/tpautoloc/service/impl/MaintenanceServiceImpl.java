
package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import tn.esprit.tpautoloc.web.mapper.MaintenanceMapper;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.service.IMaintenanceService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceDTO creer(MaintenanceDTO dto) {
        Maintenance maintenance = maintenanceMapper.toEntity(dto);
        Maintenance saved = maintenanceRepository.save(maintenance);
        return maintenanceMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO findById(Long id) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance introuvable", id));

        return maintenanceMapper.toDto(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findAll() {
        return maintenanceRepository.findAll().stream()
                .map(maintenanceMapper::toDto)
                .toList();
    }
}
