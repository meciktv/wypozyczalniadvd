package Model;

import org.junit.jupiter.api.Test;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    // Pomocnicza metoda tworząca instancję użytkownika (ponieważ klasa jest abstrakcyjna)
    private User stworzTestowegoUzytkownika() {
        return new User() {
            @Override
            public void showList(BazaDanych bazaDanych, Scanner s) {
                // Pusta implementacja metody abstrakcyjnej na potrzeby testu
            }
        };
    }

    @Test
    public void testGetteryISetteryUzytkownika() {
        // GIVEN: Tworzymy instancję użytkownika (przez klasę anonimową)
        User user = stworzTestowegoUzytkownika();

        // WHEN: Ustawiamy dane testowe za pomocą Twoich metod
        user.setID(55);
        user.setImie("Jan");
        user.setNazwisko("Kowalski");
        user.setEmail("jan.kowalski@example.com");
        user.setTelefon("123456789");
        user.setPassword("tajneHaslo123");

        // THEN: Sprawdzamy, czy dane zostały poprawnie zapisane i są zwracane
        assertEquals(55, user.getID(), "ID użytkownika powinno wynosić 55.");
        assertEquals("Jan", user.getImie(), "Imię powinno wynosić Jan.");
        assertEquals("Kowalski", user.getNazwisko(), "Nazwisko powinno wynosić Kowalski.");
        assertEquals("jan.kowalski@example.com", user.getEmail(), "Email powinien się zgadzać.");
        assertEquals("123456789", user.getTelefon(), "Numer telefonu powinien być poprawny.");
        assertEquals("tajneHaslo123", user.getPassword(), "Hasło powinno być dokładnie takie, jakie ustawiono.");
    }

    @Test
    public void testIntegracjiUzytkownikaZWypozyczeniem() {
        // GIVEN: Tworzymy użytkownika oraz obiekt wypożyczenia
        User user = stworzTestowegoUzytkownika();
        Wypozyczanie wypozyczenie = new Wypozyczanie();

        // WHEN: Podpinamy użytkownika pod wypożyczenie
        wypozyczenie.setUser(user);

        // THEN: Sprawdzamy, czy powiązanie obiektów działa prawidłowo
        assertSame(user, wypozyczenie.getUser(),
                "Metoda getUser() w klasie Wypozyczanie musi zwracać dokładnie tego samego użytkownika.");
    }
}