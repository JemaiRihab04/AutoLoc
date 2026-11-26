
package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import java.util.List;

public interface IMaintenanceService {

    MaintenanceDTO creer(MaintenanceDTO dto);

    MaintenanceDTO findById(Long id);

    List<MaintenanceDTO> findAll();
}
