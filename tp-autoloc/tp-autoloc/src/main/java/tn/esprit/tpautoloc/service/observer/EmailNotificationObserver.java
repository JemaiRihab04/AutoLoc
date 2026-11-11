package tn.esprit.tpautoloc.service.observer;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.tpautoloc.domain.Reservation;

/**
 * Observateur concret : notification par email (simulée via logs).
 */
@Slf4j
@Component
public class EmailNotificationObserver implements IReservationObserver {

    @Override
    public void onReservationConfirmee(Reservation reservation) {
        log.info("[EMAIL] Confirmation envoyee au client {} pour la reservation #{}",
                reservation.getClient().getEmail(), reservation.getIdReservation());
    }

    @Override
    public void onReservationAnnulee(Reservation reservation) {
        log.info("[EMAIL] Annulation notifiee au client {} pour la reservation #{}",
                reservation.getClient().getEmail(), reservation.getIdReservation());
    }
}