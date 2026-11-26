
package tn.esprit.tpautoloc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgenceDTO {

    private Long id;

    @NotBlank(message = "Le nom de l'agence est obligatoire")
    private String nom;

    @NotBlank(message = "La ville de l'agence est obligatoire")
    private String ville;

    private String adresse;
    private String telephone;
}
