package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FilmTest {

    @Test
    public void testGetteryISetteryFilmu() {
        // GIVEN: Tworzymy nowy obiekt filmu
        Film film = new Film();

        // WHEN: Ustawiamy komplet danych za pomocą Twoich metod
        film.setID(101);
        film.setTytul("Gladiator");
        film.setGatunek("Historyczny");
        film.setRezyser("Ridley Scott");
        film.setCena(15);
        film.setRok_produkcji(2000);

        // THEN: Sprawdzamy, czy klasa poprawnie zapisała i zwraca te dane
        assertEquals(101, film.getID(), "ID powinno wynosić 101.");
        assertEquals("Gladiator", film.getTytul(), "Tytuł powinien brzmieć: Gladiator.");
        assertEquals("Historyczny", film.getGatunek(), "Gatunek powinien się zgadzać.");
        assertEquals("Ridley Scott", film.getRezyser(), "Reżyser powinien się zgadzać.");
        assertEquals(15, film.getCena(), "Cena powinna wynosić 15.");
        assertEquals(2000, film.getRok_produkcji(), "Rok produkcji powinien wynosić 2000.");
    }

    @Test
    public void testLogikiStatusuDostepnosci() {
        // GIVEN: Nowy egzemplarz filmu
        Film film = new Film();

        // 1. KROK: Domyślnie sprawdzamy status dostępnego filmu (status 0)
        film.setDostepny(0);
        assertEquals(0, film.isDostepny(), "Status 0 powinien oznaczać, że film jest dostępny.");

        // 2. KROK: Symulujemy sytuację, gdy film zostaje wypożyczony (zmiana statusu na 1)
        film.setDostepny(1);
        assertEquals(1, film.isDostepny(), "Status 1 powinien oznaczać, że film jest wypożyczony.");

        // 3. KROK: Symulujemy sytuację, gdy film zostaje usunięty ze sklepu (zmiana statusu na 2)
        film.setDostepny(2);
        assertEquals(2, film.isDostepny(), "Status 2 powinien oznaczać, że film jest usunięty.");
    }
}