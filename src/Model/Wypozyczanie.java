package Model;

import java.time.LocalDateTime; // Importowanie klasy LocalDateTime do pracy z datą i czasem
import java.time.format.DateTimeFormatter; // Importowanie klasy DateTimeFormatter do formatowania daty i czasu
import java.time.temporal.ChronoUnit;

// Klasa reprezentująca wypożyczenie filmu
public class Wypozyczanie {

    private int ID; // Unikalny identyfikator wypożyczenia
    private User user; // Użytkownik, który wypożyczył film
    private Film film; // Film, który został wypożyczony
    private LocalDateTime dateTime; // Data i czas wypożyczenia
    private int godziny; // Liczba godzin wypożyczenia
    private double koszt; // Koszt wypożyczenia
    private int status; // Status wypożyczenia (np. aktywne, zakończone)
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); // Formatter do formatowania daty i czasu

    // Konstruktora klasy Wypozyczanie
    public Wypozyczanie(){
        dateTime = LocalDateTime.now(); // Ustawienie daty i czasu na aktualny moment
    }

    // Metoda zwracająca ID wypożyczenia
    public int getID(){
        return ID;
    }

    // Metoda ustawiająca ID wypożyczenia
    public void setID(int ID) {
        this.ID = ID; // Ustawienie ID wypożyczenia
    }

    // Metoda zwracająca użytkownika, który wypożyczył film
    public User getUser (){
        return user;
    }

    // Metoda ustawiająca użytkownika, który wypożyczył film
    public void setUser (User user){
        this.user = user;
    }

    // Metoda zwracająca film, który został wypożyczony
    public Film getFilm(){
        return film;
    }

    // Metoda ustawiająca film, który został wypożyczony
    public void setFilm(Film film){
        this.film = film;
    }

    public LocalDateTime getLocalDateTime(){
        return dateTime;
    }

    // Metoda zwracająca sformatowaną datę i czas wypożyczenia
    public String getDateTime(){
        return formatter.format(dateTime);
    }

    // Metoda ustawiająca datę i czas wypożyczenia na podstawie podanego stringa
    public void setDateTime(String dateTime){
        this.dateTime = LocalDateTime.parse(dateTime, formatter); // Parsowanie stringa do LocalDateTime
    }

    // Metoda zwracająca liczbę godzin wypożyczenia
    public int getGodziny(){
        return godziny;
    }

    // Metoda ustawiająca liczbę godzin wypożyczenia
    public void setGodziny(int godziny){
        this.godziny = godziny;
    }

    // Metoda zwracająca koszt wypożyczenia
    public double getKoszt(){
        return koszt;
    }

    // Metoda ustawiająca koszt wypożyczenia
    public void setKoszt(double koszt){
        this.koszt = koszt;
    }

    public String getStatusToString(){
        long passedHours = ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
        String status = "";
        if (getStatus()!=1 && passedHours<getGodziny()){
            status = "wypozyczony";
        }else if (getStatus()!=1 && passedHours>getGodziny()){
            status = "opoznienie";
        }else if (getStatus()==1){
            status = "oddany";
        }
        return status;
    }

    public long getOpoznienie() {
        long passedHours = ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
        return passedHours-godziny;
    }

    // Metoda zwracająca status wypożyczenia
    public int getStatus(){
        return status;
    }

    // Metoda ustawiająca status wypożyczenia
    public void setStatus(int status){
        this.status = status;
    }
}