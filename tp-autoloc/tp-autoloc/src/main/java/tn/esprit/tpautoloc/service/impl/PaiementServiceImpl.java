package tn.esprit.tpautoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.repository.IPaiementRepository;
import tn.esprit.tpautoloc.service.IPaiementService;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;
import tn.esprit.tpautoloc.web.mapper.PaiementMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final IContratRepository contratRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementDTO creer(PaiementDTO dto) {
        Paiement paiement = paiementMapper.toEntity(dto);

        if (dto.getContratId() != null) {
            Contrat contrat = contratRepository.findById(dto.getContratId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Contrat introuvable", dto.getContratId()));
            paiement.setContrat(contrat);
        }

        Paiement saved = paiementRepository.save(paiement);
        return paiementMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO findById(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Paiement introuvable", id));
        return paiementMapper.toDto(paiement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> findAll() {
        return paiementRepository.findAll().stream()
                .map(paiementMapper::toDto)
                .toList();
    }
}