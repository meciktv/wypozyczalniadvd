package Model;

import Controller.*;

import java.util.Scanner;

// Klasa reprezentująca administratora, dziedziczy po klasie User
public class Admin extends User {

    // Tablica operacji, w tym przypadku tylko dodawanie konta
    private Operation[] operations = new Operation[] {new DodawanieFilmu(),
            new WyswieltFilmy(),
            new ModyfikacjaFilmu(),
            new UsuwanieFilmu(),
            new DodawanieKonta(1),
            new WszystkieWypozyczenia(),
            new WypozyczeniaKlientow(),
            new EdytowanieUzytkownika(),
            new ZmienHaslo(),
            new Wyjscie()};


    // Konstruktora klasy Admin
    public Admin(){
        super(); // Wywołanie konstruktora klasy nadrzędnej (User )
    }

    // Metoda do wyświetlania opcji dostępnych dla administratora
    @Override
    public void showList(BazaDanych bazaDanych, Scanner s){
        // Wyświetlenie dostępnych opcji
        System.out.println("\n1. Dodaj nowy film");
        System.out.println("2. Filmy");
        System.out.println("3. Modyfikuj liste filmow");
        System.out.println("4. Usun film");
        System.out.println("5. Dodaj nowego Admina");
        System.out.println("6. Wypozyczone filmy");
        System.out.println("7. Klienci wypozyczone filmy");
        System.out.println("8. Edytuj moje dane");
        System.out.println("9. Zmień hasło");
        System.out.println("10. Wyjscie\n");

        int i = s.nextInt(); // Wczytanie wyboru użytkownika
        if (i<1 || i>10){
            showList(bazaDanych, s);
            return;
        }

        // Wykonanie operacji dodawania konta
        operations[i - 1].Operation(bazaDanych, s, this);
        if (i!=10) showList(bazaDanych, s);
    }
}