package Controller;

import Model.*;
import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class DodawanieKonta implements Operation {
    private int Typ;

    public DodawanieKonta(int Typ) {
        this.Typ = Typ;
    }

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            JTextField imieF = new JTextField();
            JTextField nazwiskoF = new JTextField();
            JTextField emailF = new JTextField();
            JTextField telefonF = new JTextField();
            JPasswordField passF = new JPasswordField();
            JPasswordField confirmPassF = new JPasswordField();

            Object[] message = {
                    "Imię:", imieF,
                    "Nazwisko:", nazwiskoF,
                    "Email:", emailF,
                    "Telefon:", telefonF,
                    "Hasło:", passF,
                    "Potwierdź hasło:", confirmPassF
            };

            String title = (Typ == 1) ? "Dodaj nowego Administratora" : "Rejestracja nowego konta";
            int option = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String pass = new String(passF.getPassword());
                String confirm = new String(confirmPassF.getPassword());

                if (!pass.equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Hasła nie są identyczne!");
                    return;
                }

                if (emailF.getText().isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email i hasło są wymagane!");
                    return;
                }

                wykonajRejestracje(bazaDanych, imieF.getText(), nazwiskoF.getText(), emailF.getText(), telefonF.getText(), pass);
            }
        }
    }

    private void wykonajRejestracje(BazaDanych bazaDanych, String imie, String nazwisko, String email, String telefon, String haslo) {
        try {

            String check = "SELECT COUNT(*) FROM user WHERE email = ?";
            PreparedStatement psCheck = bazaDanych.getConnection().prepareStatement(check);
            psCheck.setString(1, email);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Ten adres email jest już zajęty!");
                return;
            }


            String insert = "INSERT INTO user(imie, nazwisko, email, telefon, haslo, typ) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = bazaDanych.getConnection().prepareStatement(insert);
            ps.setString(1, imie);
            ps.setString(2, nazwisko);
            ps.setString(3, email);
            ps.setString(4, telefon);
            ps.setString(5, haslo);
            ps.setInt(6, Typ);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Konto utworzone pomyślnie!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage());
        }
    }
}