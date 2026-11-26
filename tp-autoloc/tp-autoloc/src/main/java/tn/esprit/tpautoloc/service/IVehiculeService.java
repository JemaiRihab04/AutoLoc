package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.VehiculeDTO;
import java.util.List;

public interface IVehiculeService {
    VehiculeDTO creer(VehiculeDTO dto);
    VehiculeDTO findById(Long id);
    List<VehiculeDTO> findAll();
    VehiculeDTO affecterVehiculeAAgence(Long vehiculeId, Long agenceId);
    VehiculeDTO desaffecterVehiculeDeAgence(Long vehiculeId);
}