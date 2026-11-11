package tn.esprit.tpautoloc.service.observer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import tn.esprit.tpautoloc.domain.Reservation;

/**
 * Sujet observable (GoF Observer) : maintient la liste des observateurs
 * et les notifie lors des changements d'état d'une réservation.
 * Tous les beans IReservationObserver de Spring sont injectés automatiquement.
 */
@Component
public class ReservationSubject {

    private final List<IReservationObserver> observers;

    public ReservationSubject(List<IReservationObserver> observers) {
        this.observers = new ArrayList<>(observers);
    }

    public void notifierConfirmation(Reservation reservation) {
        observers.forEach(o -> o.onReservationConfirmee(reservation));
    }

    public void notifierAnnulation(Reservation reservation) {
        observers.forEach(o -> o.onReservationAnnulee(reservation));
    }
}