package Controller;

import Model.BazaDanych;
import Model.Film;
import Model.Operation;
import Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class WyswieltFilmy implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {

        System.out.println();
        String select = "SELECT * FROM film;";
        ArrayList<Film> filmy = new ArrayList<>();
        try {
            ResultSet rs = bazaDanych.getStatement().executeQuery(select);
            while (rs.next()) {
                Film nowyFilm = new Film();
                nowyFilm.setID(rs.getInt("ID"));
                nowyFilm.setTytul(rs.getString("tytul"));
                nowyFilm.setGatunek(rs.getString("gatunek"));
                nowyFilm.setRezyser(rs.getString("rezyser"));
                nowyFilm.setCena(rs.getInt("Cena"));
                nowyFilm.setRok_produkcji(rs.getInt("Rok_produkcji"));
                nowyFilm.setDostepny(rs.getInt("Dostepny"));
                filmy.add(nowyFilm); // Dodanie do listy 'filmy'
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Film f : filmy) {
            if (f.isDostepny() < 2) {
                System.out.println("\n============================================");
                System.out.println("ID:\t" + f.getID());
                System.out.println("Tytuł:\t" + f.getTytul());
                System.out.println("Gatunek:\t" + f.getGatunek());
                System.out.println("Reżyser:\t" + f.getRezyser());
                System.out.println("Cena:\t" + f.getCena() + " zł");
                System.out.println("Rok Produkcji:\t" + f.getRok_produkcji());
                if (f.isDostepny() == 0) {
                    System.out.println("Status:\t Dostępny");
                } else {
                    System.out.println("Status:\t Nie Dostępny");
                }
                System.out.println("\n============================================");
            }
        }

        System.out.println();
    }
}