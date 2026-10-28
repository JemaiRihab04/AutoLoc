package tn.esprit.tpautoloc.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import jakarta.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    @ManyToOne

   // @JsonBackReference ("agence-vehichules")

    private Agence agence;

    @OneToMany(mappedBy = "vehicule")
    //@JsonManagedReference("vehicule-reservation")
    @JsonIgnore
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    //@JsonManagedReference("Vehicule-maintenances")
    @JsonIgnore

    private Set<Maintenance> maintenances = new HashSet<>();
    @ManyToMany
    private Set<Equipement> equipements = new HashSet<>();

}