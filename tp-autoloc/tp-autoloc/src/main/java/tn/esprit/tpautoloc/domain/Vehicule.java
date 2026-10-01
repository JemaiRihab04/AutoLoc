package tn.esprit.tpautoloc.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    private String immatriculation;

    private String marque;

    private String modele;

    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    private StatutVehicule statut;
}