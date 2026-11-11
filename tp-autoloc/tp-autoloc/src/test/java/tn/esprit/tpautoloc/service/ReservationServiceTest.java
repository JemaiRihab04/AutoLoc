package tn.esprit.tpautoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.service.observer.EmailNotificationObserver;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;
import tn.esprit.tpautoloc.service.observer.SmsNotificationObserver;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
        ReservationService.class,
        ReservationSubject.class,
        EmailNotificationObserver.class,
        SmsNotificationObserver.class
})
@ExtendWith(OutputCaptureExtension.class)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setEmail("ali@example.com");
        client.setTelephone("+21620123456");
    }

    @Test
    void doitNotifierEmailEtSmsLorsDeLaConfirmation(CapturedOutput output) {
        Reservation reservation = reservationService.creerReservation(client);

        assertThat(reservation.getStatut()).isEqualTo(StatutReservation.CONFIRMEE);
        assertThat(output).contains("[EMAIL] Confirmation envoyee au client");
        assertThat(output).contains("[SMS] Confirmation envoyee au");
    }

    @Test
    void doitNotifierEmailEtSmsLorsDeLAnnulation(CapturedOutput output) {
        Reservation reservation = reservationService.creerReservation(client);

        reservationService.annulerReservation(reservation);

        assertThat(reservation.getStatut()).isEqualTo(StatutReservation.ANNULEE);
        assertThat(output).contains("[EMAIL] Annulation notifiee au client");
        assertThat(output).contains("[SMS] Annulation notifiee au");
    }
}