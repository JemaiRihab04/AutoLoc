package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.IContratService;
import tn.esprit.tpautoloc.web.dto.ContratDTO;
import tn.esprit.tpautoloc.web.mapper.ContratMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper contratMapper;

    @Override
    public ContratDTO creer(ContratDTO dto) {
        Contrat contrat = contratMapper.toEntity(dto);

        if (dto.getReservationId() != null) {
            Reservation reservation = reservationRepository.findById(dto.getReservationId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Réservation introuvable", dto.getReservationId()));
            contrat.setReservation(reservation);
        }

        Contrat saved = contratRepository.save(contrat);
        return contratMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ContratDTO findById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contrat introuvable", id));
        return contratMapper.toDto(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> findAll() {
        return contratRepository.findAll().stream()
                .map(contratMapper::toDto)
                .toList();
    }
}