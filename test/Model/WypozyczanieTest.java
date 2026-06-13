package Model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.*;

public class WypozyczanieTest {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Test
    public void testLogikiOpoznieniaIMetodyStatusu() {
        // GIVEN: Tworzymy wypożyczenie z limitem 5 godzin
        Wypozyczanie wypozyczenie = new Wypozyczanie();
        wypozyczenie.setGodziny(5);
        wypozyczenie.setStatus(0); // status inny niż 1 (czyli nieoddany)

        // Scenariusz A: Film wypożyczony przed chwilą (w terminie)
        // passedHours = 0, limit = 5, opóźnienie = 0 - 5 = -5
        LocalDateTime teraz = LocalDateTime.now();
        wypozyczenie.setDateTime(teraz.format(formatter));

        assertEquals(-5, wypozyczenie.getOpoznienie(), "Zapas czasu powinien wynosić 5 godzin (wynik -5).");
        assertEquals("wypozycony", wypozyczenie.getStatusToString().replace("ż", "z"),
                "Jeśli czas nie minął, status powinien brzmieć 'wypozyczony'.");

        // Scenariusz B: Film przetrzymany (cofamy czas o 10 godzin wstecz)
        // passedHours = 10, limit = 5, opóźnienie = 10 - 5 = 5
        LocalDateTime dziesiecGodzinTemu = LocalDateTime.now().minusHours(10);
        wypozyczenie.setDateTime(dziesiecGodzinTemu.format(formatter));

        assertEquals(5, wypozyczenie.getOpoznienie(), "Opóźnienie powinno wynosić dokładnie 5 godzin.");
        assertEquals("opoznienie", wypozyczenie.getStatusToString(),
                "Gdy czas minął, status powinien zmienić się na 'opoznienie'.");

        // Scenariusz C: Film został oddany (status = 1)
        wypozyczenie.setStatus(1);
        assertEquals("oddany", wypozyczenie.getStatusToString(),
                "Niezależnie od czasu, jeśli status wynosi 1, film ma status 'oddany'.");
    }
}