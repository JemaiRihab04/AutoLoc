package tn.esprit.tpautoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

import java.util.List;

@RestController
@RequestMapping("/vehicules")   // garde ton mapping actuel
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @PostMapping
    public ResponseEntity<VehiculeDTO> creer( @Valid  @RequestBody VehiculeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculeService.creer(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> findAll() {
        return ResponseEntity.ok(vehiculeService.findAll());
    }

    @PutMapping("/{vehiculeId}/agence/{agenceId}")
    public ResponseEntity<VehiculeDTO> affecter(@PathVariable Long vehiculeId,
                                                @PathVariable Long agenceId) {
        return ResponseEntity.ok(vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId));
    }

    @PutMapping("/{vehiculeId}/desaffecter")
    public ResponseEntity<VehiculeDTO> desaffecter(@PathVariable Long vehiculeId) {
        return ResponseEntity.ok(vehiculeService.desaffecterVehiculeDeAgence(vehiculeId));
    }
}