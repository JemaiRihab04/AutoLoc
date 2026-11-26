package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.ContratDTO;
import java.util.List;

public interface IContratService {
    ContratDTO creer(ContratDTO dto);
    ContratDTO findById(Long id);
    List<ContratDTO> findAll();
}