package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpautoloc.service.IPaiementService;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;

import java.util.List;

@RestController
@RequestMapping("/paiements")   // garde ton mapping actuel
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;

    @PostMapping
    public ResponseEntity<PaiementDTO> creer(@Valid  @RequestBody PaiementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paiementService.creer(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(paiementService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PaiementDTO>> findAll() {
        return ResponseEntity.ok(paiementService.findAll());
    }
}