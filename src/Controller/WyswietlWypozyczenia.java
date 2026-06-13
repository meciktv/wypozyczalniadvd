package Controller;

import Model.BazaDanych;
import Model.Operation;
import Model.User;
import Model.Wypozyczanie;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class WyswietlWypozyczenia implements Operation {

    private int userID;

    public WyswietlWypozyczenia(int userID) {
        this.userID = userID;
    }

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            pokazOknoWypozyczen(bazaDanych);
        } else {

        }
    }

    private void pokazOknoWypozyczen(BazaDanych bazaDanych) {
        JFrame frame = new JFrame("Moje Wypożyczenia");
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);


        String[] columnNames = {"ID Wyp.", "Tytuł Filmu", "Data Wypożyczenia", "Godziny", "Koszt", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        try {

            String sql = "SELECT w.ID, f.tytul, w.DateTime, w.godziny, w.koszt, w.status " +
                    "FROM wypozyczenia w " +
                    "JOIN film f ON w.film = f.ID " +
                    "WHERE w.user = ? ORDER BY w.DateTime DESC";

            PreparedStatement ps = bazaDanych.getConnection().prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String tytul = rs.getString("tytul");
                String data = rs.getString("DateTime");
                int godz = rs.getInt("godziny");
                double koszt = rs.getDouble("koszt");
                int statusNum = rs.getInt("status");


                String status = (statusNum == 1) ? "Oddany" : "Aktywne";

                model.addRow(new Object[]{id, tytul, data, godz, koszt + " zł", status});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd pobierania wypożyczeń: " + e.getMessage());
        }

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }
}