
package tn.esprit.tpautoloc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {

    private Long id;
    private LocalDate dateSignature;

    @PositiveOrZero(message = "Le montant total doit être positif ou égal à zéro")
    private double montantTotal;

    private boolean valide;
    private Long reservationId;
}
