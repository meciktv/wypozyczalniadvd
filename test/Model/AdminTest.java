package Model;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    public void testDziedziczeniaPoKlasieUser() {
        // GIVEN: Tworzymy obiekt Admina
        Admin admin = new Admin();

        // THEN: Sprawdzamy, czy admin jest instancją klasy User
        assertTrue(admin instanceof User, "Klasa Admin powinna dziedziczyć po klasie User.");
    }

    @Test
    public void testDanychIUprawnienAdmina() {
        // GIVEN: Nowy obiekt admina
        Admin admin = new Admin();

        // WHEN: Ustawiamy dane za pomocą metod odziedziczonych po User
        admin.setID(999);
        admin.setImie("Super");
        admin.setNazwisko("Admin");
        admin.setPassword("admin123");

        // THEN: Sprawdzamy, czy dane poprawnie zapisały się w strukturze dziedziczenia
        assertEquals(999, admin.getID(), "Admin powinien poprawnie przechowywać odziedziczone ID.");
        assertEquals("Super", admin.getImie(), "Admin powinien poprawnie przechowywać odziedziczone imię.");
        assertEquals("admin123", admin.getPassword(), "Hasło admina powinno być prawidłowo zapisane.");
    }

    @Test
    public void testIntegralnosciMenuOperacjiAdmina() {
        // GIVEN: Tworzymy obiekt Admina
        Admin admin = new Admin();

        // WHEN & THEN: Za pomocą refleksji zaglądamy w prywatną tablicę operacji,
        // aby upewnić się, że żadna kluczowa opcja menu nie została przypadkowo skasowana.
        try {
            Field operationsField = Admin.class.getDeclaredField("operations");
            operationsField.setAccessible(true);
            Object[] operations = (Object[]) operationsField.get(admin);

            assertNotNull(operations, "Tablica operacji nie powinna być nullem.");
            assertEquals(10, operations.length,
                    "Admin musi posiadać dokładnie 10 zdefiniowanych operacji w menu.");

        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Nie udało się uzyskać dostępu do pola 'operations': " + e.getMessage());
        }
    }
}