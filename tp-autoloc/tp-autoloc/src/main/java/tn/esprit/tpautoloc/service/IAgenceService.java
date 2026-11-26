package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import java.util.List;

public interface IAgenceService {
    AgenceDTO creer(AgenceDTO dto);
    AgenceDTO findById(Long id);
    List<AgenceDTO> findAll();
}