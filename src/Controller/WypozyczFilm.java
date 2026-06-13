package Controller;

import Model.Film;
import Model.Operation;
import Model.BazaDanych;
import Model.User;
import Model.Wypozyczanie;

import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class WypozyczFilm implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            String idStr = JOptionPane.showInputDialog("Podaj ID filmu, który chcesz wypożyczyć:");
            if (idStr == null || idStr.isEmpty()) return;

            try {
                int id = Integer.parseInt(idStr);
                wykonajWypozyczenie(bazaDanych, id, user);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID musi być liczbą!");
            }
        } else {

        }
    }


    public void wykonajWypozyczenie(BazaDanych bazaDanych, int filmID, User user) {
        try {

            String checkSql = "SELECT * FROM film WHERE ID = ?";
            PreparedStatement psCheck = bazaDanych.getConnection().prepareStatement(checkSql);
            psCheck.setInt(1, filmID);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                int status = rs.getInt("dostepny");
                int cena = rs.getInt("cena");
                String tytul = rs.getString("tytul");

                if (status != 0) {
                    JOptionPane.showMessageDialog(null, "Film '" + tytul + "' jest obecnie niedostępny.");
                    return;
                }


                String godzinyStr = JOptionPane.showInputDialog("Na ile godzin chcesz wypożyczyć film '" + tytul + "'?");
                if (godzinyStr == null) return;
                int godziny = Integer.parseInt(godzinyStr);

                double koszt = cena * godziny;


                int confirm = JOptionPane.showConfirmDialog(null,
                        "Koszt wypożyczenia to: " + koszt + " zł. Kontynuować?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    Wypozyczanie w = new Wypozyczanie();
                    String insert = "INSERT INTO `wypozyczenia`(`user`, `film`, `DateTime`, `godziny`, `koszt`, `status`) VALUES (?, ?, ?, ?, ?, 0)";
                    PreparedStatement psInsert = bazaDanych.getConnection().prepareStatement(insert);
                    psInsert.setInt(1, user.getID());
                    psInsert.setInt(2, filmID);
                    psInsert.setString(3, w.getDateTime());
                    psInsert.setInt(4, godziny);
                    psInsert.setDouble(5, koszt);
                    psInsert.executeUpdate();


                    String updateFilm = "UPDATE film SET dostepny = 1 WHERE ID = ?";
                    PreparedStatement psUpdate = bazaDanych.getConnection().prepareStatement(updateFilm);
                    psUpdate.setInt(1, filmID);
                    psUpdate.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Wypożyczono pomyślnie!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nie znaleziono filmu o ID: " + filmID);
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Błąd: " + e.getMessage());
        }
    }
}