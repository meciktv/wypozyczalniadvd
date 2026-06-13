package Model;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class KlientTest {

    @Test
    public void testDziedziczeniaPoKlasieUser() {
        // GIVEN: Tworzymy obiekt Klienta
        Klient klient = new Klient();

        // THEN: Sprawdzamy dziedziczenie polimorficzne
        assertTrue(klient instanceof User, "Klasa Klient musi dziedziczyć po klasie User.");
    }

    @Test
    public void testDanychKlienta() {
        // GIVEN: Nowy obiekt klienta
        Klient klient = new Klient();

        // WHEN: Ustawiamy unikalne dane klienta
        klient.setID(7);
        klient.setImie("Kuba");
        klient.setNazwisko("Nowak");
        klient.setEmail("kuba@example.com");

        // THEN: Weryfikujemy spójność konta klienta
        assertEquals(7, klient.getID(), "ID klienta powinno wynosić 7.");
        assertEquals("Kuba", klient.getImie(), "Imię klienta powinno być poprawne.");
        assertEquals("kuba@example.com", klient.getEmail(), "Adres email powinien się zgadzać.");
    }

    @Test
    public void testIntegralnosciMenuKlienta() {
        // GIVEN: Tworzymy klienta
        Klient klient = new Klient();

        // WHEN & THEN: Wyciągamy tablicę operacji za pomocą refleksji
        try {
            Field operationsField = Klient.class.getDeclaredField("operations");
            operationsField.setAccessible(true);
            Object[] operations = (Object[]) operationsField.get(klient);

            assertNotNull(operations, "Tablica operacji klienta nie może być nullem.");

            // Logika biznesowa menu: dokładnie 7 opcji (Filmy, Wypożycz, Zwróć, Moje wypożyczenia...)
            assertEquals(7, operations.length,
                    "Klient musi posiadać dokładnie 7 zdefiniowanych operacji w menu.");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Błąd dostępu do pola 'operations' w klasie Klient: " + e.getMessage());
        }
    }
}