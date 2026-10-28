package tn.esprit.tpautoloc.domain;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;

    private String ville;

    private String adresse;

    private String telephone;
    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    //@JsonManagedReference("agence-employes")
    @JsonIgnore
    private Set<Employe> employes = new HashSet<>();

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    //@JsonManagedReference("agence-vehichules")
    @JsonIgnore
    private Set<Vehicule> vehicules = new HashSet<>();


}

