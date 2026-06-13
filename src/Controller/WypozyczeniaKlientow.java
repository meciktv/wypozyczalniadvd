package Controller;

import Model.BazaDanych;
import Model.Operation;
import Model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class WypozyczeniaKlientow implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            pokazWszystkieWypozyczenia(bazaDanych);
        } else {

        }
    }

    private void pokazWszystkieWypozyczenia(BazaDanych bazaDanych) {
        JFrame frame = new JFrame("Panel Administratora - Wszystkie Wypożyczenia");
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);


        String[] columnNames = {"ID", "Klient (Email)", "Film", "Data", "Godziny", "Koszt", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        try {

            String sql = "SELECT w.ID, u.email, f.tytul, w.DateTime, w.godziny, w.koszt, w.status " +
                    "FROM wypozyczenia w " +
                    "JOIN user u ON w.user = u.ID " +
                    "JOIN film f ON w.film = f.ID " +
                    "ORDER BY w.DateTime DESC";

            ResultSet rs = bazaDanych.getStatement().executeQuery(sql);

            while (rs.next()) {
                String status = (rs.getInt("status") == 1) ? "Zwrócony" : "Wypożyczony (Aktywne)";

                model.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("email"),
                        rs.getString("tytul"),
                        rs.getString("DateTime"),
                        rs.getInt("godziny"),
                        rs.getDouble("koszt") + " zł",
                        status
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd pobierania danych: " + e.getMessage());
        }

        frame.add(new JScrollPane(table));
        frame.setVisible(true);
    }
}