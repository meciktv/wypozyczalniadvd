package Model;

import Controller.*;
import java.util.Scanner; // Importowanie klasy Scanner do wczytywania danych od użytkownika

// Klasa reprezentująca klienta wypożyczalni, dziedziczy po klasie User
public class Klient extends User {

    private Operation[] operations;

    // Konstruktora klasy Klient
    public Klient() {
        super(); // Wywołanie konstruktora klasy nadrzędnej (User )
        // Inicjalizacja operacji w konstruktorze
        operations = new Operation[]{
                new WyswieltFilmy(),
                new WypozyczFilm(),
                new OddacFilm(),
                new WyswietlWypozyczenia(getID()),
                new EdytowanieUzytkownika(),
                new ZmienHaslo(),
                new Wyjscie()
        };
    }

    // Metoda do wyświetlania opcji dostępnych dla klienta
    @Override
    public void showList(BazaDanych bazaDanych, Scanner s) {
        // Wyświetlenie dostępnych opcji dla klienta
        System.out.println("\n1. Filmy");
        System.out.println("2. Wypozycz film");
        System.out.println("3. Zwroc film");
        System.out.println("4. Moje wypozyczenia");
        System.out.println("5. Zmien moje dane");
        System.out.println("6. Zmien moje haslo");
        System.out.println("7. Wyjscie");

        int i = s.nextInt();
        if (i < 1 || i > 7) {
            showList(bazaDanych, s);
            return;
        }
        operations[i - 1].Operation(bazaDanych, s, this);
    }
}



/*
package Model;

import Controller.*;

import java.util.Scanner; // Importowanie klasy Scanner do wczytywania danych od użytkownika


// Klasa reprezentująca klienta wypożyczalni, dziedziczy po klasie User
public class Klient extends User {

    private Operation[] operations = new Operation[]{
        new WyswieltFilmy(),
        new WypozyczFilm(),
        new OddacFilm(),
        new WyswietlWypozyczenia(getID()),
        new EdytowanieUzytkownika(),
        new ZmienHaslo(),
        new Wyjscie()};

    // Konstruktora klasy Klient
    public Klient(){
        super(); // Wywołanie konstruktora klasy nadrzędnej (User )
    }

    // Metoda do wyświetlania opcji dostępnych dla klienta
    @Override
    public void showList(BazaDanych bazaDanych, Scanner s){
        // Wyświetlenie dostępnych opcji dla klienta
        System.out.println("\n1. Filmy");
        System.out.println("2. Wypozycz film");
        System.out.println("3. Zwroc film");
        System.out.println("4. Moje wypozyczenia");
        System.out.println("5. Zmien moje dane");
        System.out.println("6. Zmien moje haslo");
        System.out.println("7. Wyjscie");

        int i = s.nextInt();
        if (i<1 || i>7){
            showList(bazaDanych, s);
            return;
        }
        operations[i-1].Operation(bazaDanych, s, this);

    }
}

 */