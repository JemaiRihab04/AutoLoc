package tn.esprit.tpautoloc.service.factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.ITarificationStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifLongueDureeStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifStandardStrategy;
import tn.esprit.tpautoloc.service.strategy.TarifWeekendStrategy;

class TarificationStrategyFactoryTest {

    private TarificationStrategyFactory factory;
    private TarifStandardStrategy standard;
    private TarifWeekendStrategy weekend;
    private TarifLongueDureeStrategy longueDuree;

    @BeforeEach
    void setUp() {
        standard = new TarifStandardStrategy();
        weekend = new TarifWeekendStrategy();
        longueDuree = new TarifLongueDureeStrategy();

        factory = new TarificationStrategyFactory(List.of(standard, weekend, longueDuree));
    }

    // Test 1 : longue durée (>= 7 jours)
    @Test
    void doitRetournerLongueDureePour7JoursOuPlus() {
        Reservation reservation = Reservation.builder()
                .dateDebut(LocalDate.of(2024, 1, 1))
                .dateFin(LocalDate.of(2024, 1, 8))
                .build();

        ITarificationStrategy resultat = factory.resoudre(reservation);

        assertThat(resultat).isInstanceOf(TarifLongueDureeStrategy.class);
        assertThat(resultat.getCode()).isEqualTo("LONGUE_DUREE");
    }

    // Test 2 : borne exacte à 7 jours
    @Test
    void doitRetournerLongueDureePourExactement7Jours() {
        Reservation reservation = Reservation.builder()
                .dateDebut(LocalDate.of(2024, 5, 1))
                .dateFin(LocalDate.of(2024, 5, 8))
                .build();

        ITarificationStrategy resultat = factory.resoudre(reservation);

        assertThat(resultat.getCode()).isEqualTo("LONGUE_DUREE");
    }

    // Test 3 : court séjour commençant un vendredi
    @Test
    void doitRetournerWeekendPourCourtSejourVendredi() {
        Reservation reservation = Reservation.builder()
                .dateDebut(LocalDate.of(2024, 3, 8))
                .dateFin(LocalDate.of(2024, 3, 10))
                .build();

        ITarificationStrategy resultat = factory.resoudre(reservation);

        assertThat(resultat).isInstanceOf(TarifWeekendStrategy.class);
        assertThat(resultat.getCode()).isEqualTo("WEEKEND");
    }

    // Test 4 : durée moyenne (4 à 6 jours), début un lundi
    @Test
    void doitRetournerStandardPourMoyenneDuree() {
        Reservation reservation = Reservation.builder()
                .dateDebut(LocalDate.of(2024, 3, 4))
                .dateFin(LocalDate.of(2024, 3, 8))
                .build();

        ITarificationStrategy resultat = factory.resoudre(reservation);

        assertThat(resultat.getCode()).isEqualTo("STANDARD");
    }
}
