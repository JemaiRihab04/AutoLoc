package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import java.util.List;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;


public interface IReservationService {
    ReservationResponseDTO findById(Long id);
    List<ReservationResponseDTO> findAll();
    ReservationResponseDTO creer(ReservationRequestDTO dto);

}