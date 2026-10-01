package tn.esprit.tpautoloc.domain;

import jakarta.persistence.*;
import tn.esprit.tpautoloc.domain.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "equipement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
}
