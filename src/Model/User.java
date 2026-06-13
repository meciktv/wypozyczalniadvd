package Model;

import java.util.Scanner; // Importowanie klasy Scanner do wczytywania danych od użytkownika

// Abstrakcyjna klasa reprezentująca użytkownika systemu
public abstract class User {

    private int ID; // Unikalny identyfikator użytkownika
    public String imie; // Imię użytkownika (publiczne, dostępne z innych klas)
    private String nazwisko; // Nazwisko użytkownika
    private String email; // Adres email użytkownika
    private String telefon; // Numer telefonu użytkownika
    private String password; // Hasło użytkownika

    // Konstruktora klasy User
    public User() {}

    // Metoda zwracająca ID użytkownika
    public int getID() {
        return ID;
    }

    // Metoda ustawiająca ID użytkownika
    public void setID(int id) {
        this.ID = id; // Ustawienie ID użytkownika
    }

    /*
    // Metoda ustawiająca ID użytkownika
    public void setID(int id) {
        this.ID = ID; // Ustawienie ID użytkownika
    }

     */

    // Metoda zwracająca imię użytkownika
    public String getImie() {
        return imie;
    }

    // Metoda ustawiająca imię użytkownika
    public void setImie(String imie) {
        this.imie = imie;
    }

    // Metoda zwracająca nazwisko użytkownika
    public String getNazwisko() {
        return nazwisko;
    }

    // Metoda ustawiająca nazwisko użytkownika
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    // Metoda zwracająca adres email użytkownika
    public String getEmail() {
        return email;
    }

    // Metoda ustawiająca adres email użytkownika
    public void setEmail(String email) {
        this.email = email;
    }

    // Metoda zwracająca numer telefonu użytkownika
    public String getTelefon() {
        return telefon;
    }

    // Metoda ustawiająca numer telefonu użytkownika
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    // Metoda zwracająca hasło użytkownika
    public String getPassword() {
        return password;
    }

    // Metoda ustawiająca hasło użytkownika
    public void setPassword(String password) {
        this.password = password;
    }

    // Abstrakcyjna metoda do wyświetlania listy dostępnych opcji dla użytkownika
    public abstract void showList(BazaDanych bazaDanych, Scanner s);
}