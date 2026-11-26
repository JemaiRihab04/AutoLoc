package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpautoloc.service.IContratService;
import tn.esprit.tpautoloc.web.dto.ContratDTO;

import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/contrats")   // garde ton mapping actuel
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @PostMapping
    public ResponseEntity<ContratDTO> creer(@Valid @RequestBody ContratDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contratService.creer(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contratService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ContratDTO>> findAll() {
        return ResponseEntity.ok(contratService.findAll());
    }
}