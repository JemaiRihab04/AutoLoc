package tn.esprit.tpautoloc.service;

import tn.esprit.tpautoloc.web.dto.EmployeDTO;
import java.util.List;

public interface IEmployeService {
    EmployeDTO create(EmployeDTO dto);
    EmployeDTO findById(Long id);
    List<EmployeDTO> findAll();
    EmployeDTO update(Long id, EmployeDTO dto);
    void deleteById(Long id);
    EmployeDTO affecterEmployeAAgence(Long employeId, Long agenceId);
    EmployeDTO desaffecterEmployeDeAgence(Long employeId);
}