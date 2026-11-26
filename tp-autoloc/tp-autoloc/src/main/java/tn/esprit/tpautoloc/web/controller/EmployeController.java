package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;


import tn.esprit.tpautoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")   // gardez votre mapping actuel
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    @PostMapping
    public ResponseEntity<EmployeDTO> create(@RequestBody EmployeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<EmployeDTO>> findAll() {
        return ResponseEntity.ok(employeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeDTO> update(@PathVariable Long id, @RequestBody EmployeDTO dto) {
        return ResponseEntity.ok(employeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employeId}/agence/{agenceId}")
    public ResponseEntity<EmployeDTO> affecter(@PathVariable Long employeId,
                                               @PathVariable Long agenceId) {
        return ResponseEntity.ok(employeService.affecterEmployeAAgence(employeId, agenceId));
    }

    @PutMapping("/{employeId}/desaffecter")
    public ResponseEntity<EmployeDTO> desaffecter(@PathVariable Long employeId) {
        return ResponseEntity.ok(employeService.desaffecterEmployeDeAgence(employeId));
    }
}