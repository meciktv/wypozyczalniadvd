package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Model.BazaDanych;
import Model.Operation;
import Model.Wypozyczanie;
import Model.User;
import Model.Klient;
import Model.Film;
/**
 * Klasa WszystkieWypozyczenia implementuje interfejs Operation i umożliwia
 * wyświetlenie wszystkich wypożyczeń w systemie wypożyczalni DVD.
 *
 */


public class WszystkieWypozyczenia implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        /**
         * Wykonuje operację wyświetlania wszystkich wypożyczeń.
         *
         * Klasa pobiera wszystkie wypożyczenia z bazy danych, a następnie dla
         * każdego wypożyczenia pobiera informacje o użytkowniku oraz filmie.
         * Informacje te są następnie wyświetlane na ekranie.
         *
         * @param bazaDanych obiekt reprezentujący połączenie z bazą danych
         * @param s obiekt Scanner do odczytu danych wejściowych od użytkownika
         * @param user obiekt reprezentujący użytkownika, który wykonuje operację
         */

        ArrayList<Wypozyczanie> wypozyczania = new ArrayList<>();
        ArrayList<Integer> filmIDs = new ArrayList<>();
        ArrayList<Integer> userIDs = new ArrayList<>();
        try {
            String select = "SELECT * FROM `wypozyczenia`;";
            ResultSet rs = bazaDanych.getStatement().executeQuery(select);
            while (rs.next()) {
                Wypozyczanie wypozyczanie = new Wypozyczanie();
                wypozyczanie.setID(rs.getInt("ID"));
                userIDs.add(rs.getInt("user"));
                filmIDs.add(rs.getInt("film"));
                wypozyczanie.setDateTime(rs.getString("DateTime"));
                wypozyczanie.setGodziny(rs.getInt("godziny"));
                wypozyczanie.setKoszt(rs.getDouble("koszt"));
                wypozyczanie.setStatus(rs.getInt("status"));
                wypozyczania.add(wypozyczanie);
            }

            for (int j=0;j<wypozyczania.size();j++) {
                Wypozyczanie r = wypozyczania.get(j);

                String selectUser = "SELECT * FROM `user` WHERE `ID` = '"+userIDs.get(j)+"';";
                ResultSet rs2 = bazaDanych.getStatement().executeQuery(selectUser);
                rs2.next();
                User u = new Klient();
                u.setID(rs2.getInt("ID"));
                u.setImie(rs2.getString("imie"));
                u.setNazwisko(rs2.getString("nazwisko"));
                u.setEmail(rs2.getString("email"));
                u.setTelefon(rs2.getString("telefon"));
                u.setPassword(rs2.getString("haslo"));
                r.setUser(u);

                ResultSet rs3 = bazaDanych.getStatement().executeQuery("SELECT * FROM `film` WHERE `ID` = '"+ filmIDs.get(j)+"';");
                rs3.next();
                Film film = new Film();
                film.setID(rs3.getInt("ID"));
                film.setTytul(rs3.getString("tytul"));
                film.setGatunek(rs3.getString("gatunek"));
                film.setRezyser(rs3.getString("rezyser"));
                film.setRok_produkcji(rs3.getInt("rok_produkcji"));
                film.setCena(rs3.getInt("cena"));
                film.setDostepny(rs3.getInt("dostepny"));
                r.setFilm(film);

                // Wyświetlanie informacji o wypożyczeniu
                System.out.println("ID:\t" + r.getID());
                System.out.println("Imie:\t" + r.getUser ().getImie() + " " + r.getUser ().getNazwisko());
                System.out.println("Email:\t" + r.getUser ().getEmail());
                System.out.println("Numer telefonu:\t" + r.getUser ().getTelefon());
                System.out.println("ID Filmu:\t" + r.getFilm().getID());
                System.out.println("Tytuł filmu :\t" + r.getFilm().getTytul());
                System.out.println("Gatunek filmu:\t" + r.getFilm().getGatunek());
                System.out.println("Reżyser:\t" + r.getFilm().getRezyser());
                System.out.println("Data:\t" + r.getDateTime());
                System.out.println("Godziny:\t" + r.getGodziny());
                System.out.println("Koszt:\t" + r.getKoszt());
                System.out.println("Status:\t" + r.getStatus());
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}