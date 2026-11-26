package tn.esprit.tpautoloc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;   // adaptez le nom et le package de votre enum

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeDTO {
    private Long id;
    private String nom;
    private String prenom;
    private RoleEmploye role;
    private Long agenceId;
}