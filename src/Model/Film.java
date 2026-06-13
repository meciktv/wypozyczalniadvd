package Model;

// Klasa reprezentująca film w wypożyczalni
public class Film {

    private int ID; // Unikalny identyfikator filmu
    private String tytul; // Tytuł filmu
    private String gatunek; // Gatunek filmu
    private String rezyser; // Reżyser filmu
    private int cena; // Cena wypożyczenia filmu
    private int rok_produkcji; // Rok produkcji filmu
    private int dostepny; // Informacja, czy film jest dostępny do wypożyczenia

    // 0 -> dostępny
    // 1 -> wypozyczony
    // 2 -> usuniety


    // Konstruktora klasy Film
    public Film() {}

    // Metoda zwracająca ID filmu
    public int getID() {
        return ID;
    }

    // Metoda ustawiająca ID filmu
    public void setID(int ID) {
        this.ID = ID;
    }

    // Metoda zwracająca tytuł filmu
    public String getTytul() {
        return tytul;
    }

    // Metoda ustawiająca tytuł filmu
    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    // Metoda zwracająca gatunek filmu
    public String getGatunek() {
        return gatunek;
    }

    // Metoda ustawiająca gatunek filmu
    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

    // Metoda zwracająca reżysera filmu
    public String getRezyser() {
        return rezyser;
    }

    // Metoda ustawiająca reżysera filmu
    public void setRezyser(String rezyser) {
        this.rezyser = rezyser;
    }

    // Metoda zwracająca cenę wypożyczenia filmu
    public int getCena() {
        return cena;
    }

    // Metoda ustawiająca cenę wypożyczenia filmu
    public void setCena(int cena) {
        this.cena = cena;
    }

    // Metoda zwracająca rok produkcji filmu
    public int getRok_produkcji() {
        return rok_produkcji;
    }

    // Metoda ustawiająca rok produkcji filmu
    public void setRok_produkcji(int rok_produkcji) {
        this.rok_produkcji = rok_produkcji;
    }

    // Metoda zwracająca informację o dostępności filmu
    public int isDostepny() {
        return dostepny;
    }

    // Metoda ustawiająca dostępność filmu
    public void setDostepny(int dostepny) {
        this.dostepny = dostepny;
    }
}