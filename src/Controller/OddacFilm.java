package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.JOptionPane;

import Model.*;

public class OddacFilm implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            String idStr = JOptionPane.showInputDialog("Podaj ID wypożyczenia do zwrotu:");
            if (idStr == null || idStr.isEmpty()) return;

            try {
                int idWypozyczenia = Integer.parseInt(idStr);
                wykonajZwrot(bazaDanych, idWypozyczenia, user);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID musi być liczbą!");
            }
        } else {

        }
    }

    public void wykonajZwrot(BazaDanych bazaDanych, int idWypozyczenia, User user) {
        try {

            String select = "SELECT * FROM `wypozyczenia` WHERE `ID` = ? AND `user` = ? AND `status` = 0";
            PreparedStatement ps = bazaDanych.getConnection().prepareStatement(select);
            ps.setInt(1, idWypozyczenia);
            ps.setInt(2, user.getID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Wypozyczanie wyp = new Wypozyczanie();
                wyp.setID(rs.getInt("ID"));
                wyp.setDateTime(rs.getString("DateTime"));
                wyp.setGodziny(rs.getInt("godziny"));
                int filmID = rs.getInt("film");


                String statusCzasowy = wyp.getStatusToString();
                String message = "Czy chcesz zwrócić film?";

                if (statusCzasowy.equals("opoznienie")) {
                    long opoznienie = wyp.getOpoznienie();
                    message = "Wykryto opóźnienie: " + opoznienie + " godz.\n" +
                            "Naliczono karę regulaminową: 300 zł.\n" +
                            "Czy potwierdzasz zwrot i akceptujesz koszty?";
                }

                int confirm = JOptionPane.showConfirmDialog(null, message, "Zwrot filmu", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    String updateWyp = "UPDATE `wypozyczenia` SET `status` = 1 WHERE `ID` = ?";
                    PreparedStatement psUpdateWyp = bazaDanych.getConnection().prepareStatement(updateWyp);
                    psUpdateWyp.setInt(1, idWypozyczenia);
                    psUpdateWyp.executeUpdate();


                    String updateFilm = "UPDATE `film` SET `dostepny` = 0 WHERE `ID` = ?";
                    PreparedStatement psUpdateFilm = bazaDanych.getConnection().prepareStatement(updateFilm);
                    psUpdateFilm.setInt(1, filmID);
                    psUpdateFilm.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Film został zwrócony pomyślnie!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nie znaleziono aktywnego wypożyczenia o tym ID.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage());
        }
    }
}