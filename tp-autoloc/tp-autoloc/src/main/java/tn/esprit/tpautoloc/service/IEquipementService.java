package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.EquipementDTO;
import java.util.List;

public interface IEquipementService {

    EquipementDTO creer(EquipementDTO dto);

    EquipementDTO findById(Long id);

    List<EquipementDTO> findAll();
}