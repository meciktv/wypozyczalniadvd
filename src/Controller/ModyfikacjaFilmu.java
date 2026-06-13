package Controller;

import Model.BazaDanych;
import Model.Film;
import Model.Operation;
import Model.User;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ModyfikacjaFilmu implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {

        if (s == null) {
            String idStr = JOptionPane.showInputDialog("Podaj ID filmu do modyfikacji:");
            if (idStr == null || idStr.isEmpty()) return;

            try {
                int id = Integer.parseInt(idStr);
                modyfikujOkienkowo(bazaDanych, id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID musi być liczbą!");
            }
        } else {

        }
    }


    public void modyfikujOkienkowo(BazaDanych bazaDanych, int id) {
        try {

            String select = "SELECT * FROM film WHERE ID = ?";
            PreparedStatement psSelect = bazaDanych.getConnection().prepareStatement(select);
            psSelect.setInt(1, id);
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {

                String nowyTytul = JOptionPane.showInputDialog("Tytuł:", rs.getString("tytul"));
                if (nowyTytul == null) return;

                String nowyGatunek = JOptionPane.showInputDialog("Gatunek:", rs.getString("gatunek"));
                String nowyRezyser = JOptionPane.showInputDialog("Reżyser:", rs.getString("rezyser"));
                String nowaCena = JOptionPane.showInputDialog("Cena:", rs.getInt("cena"));
                String nowyRok = JOptionPane.showInputDialog("Rok produkcji:", rs.getInt("rok_produkcji"));


                String update = "UPDATE film SET tytul=?, gatunek=?, rezyser=?, cena=?, rok_produkcji=? WHERE ID=?";
                PreparedStatement psUpdate = bazaDanych.getConnection().prepareStatement(update);
                psUpdate.setString(1, nowyTytul);
                psUpdate.setString(2, nowyGatunek);
                psUpdate.setString(3, nowyRezyser);
                psUpdate.setInt(4, Integer.parseInt(nowaCena));
                psUpdate.setInt(5, Integer.parseInt(nowyRok));
                psUpdate.setInt(6, id);

                psUpdate.executeUpdate();
                JOptionPane.showMessageDialog(null, "Dane filmu zostały zaktualizowane!");
            } else {
                JOptionPane.showMessageDialog(null, "Nie znaleziono filmu o takim ID.");
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Błąd podczas modyfikacji: " + e.getMessage());
        }
    }
}