package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.*;

import Model.BazaDanych;
import Model.Operation;
import Model.User;

public class EdytowanieUzytkownika implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            JTextField imieField = new JTextField(user.getImie());
            JTextField nazwiskoField = new JTextField(user.getNazwisko());
            JTextField emailField = new JTextField(user.getEmail());
            JTextField telefonField = new JTextField(user.getTelefon());

            Object[] message = {
                    "Imię:", imieField,
                    "Nazwisko:", nazwiskoField,
                    "Email:", emailField,
                    "Numer telefonu:", telefonField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Edytuj swoje dane", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String noweImie = imieField.getText();
                String noweNazwisko = nazwiskoField.getText();
                String nowyEmail = emailField.getText();
                String nowyTelefon = telefonField.getText();


                if (noweImie.isEmpty() || noweNazwisko.isEmpty() || nowyEmail.isEmpty() || nowyTelefon.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione!");
                    return;
                }

                try {
                    String update = "UPDATE `user` SET `imie`=?, `nazwisko`=?, `email`=?, `telefon`=? WHERE `ID`=?";
                    PreparedStatement ps = bazaDanych.getConnection().prepareStatement(update);
                    ps.setString(1, noweImie);
                    ps.setString(2, noweNazwisko);
                    ps.setString(3, nowyEmail);
                    ps.setString(4, nowyTelefon);
                    ps.setInt(5, user.getID());

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {

                        user.setImie(noweImie);
                        user.setNazwisko(noweNazwisko);
                        user.setEmail(nowyEmail);
                        user.setTelefon(nowyTelefon);

                        JOptionPane.showMessageDialog(null, "Dane zostały pomyślnie zaktualizowane.");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Błąd podczas aktualizacji: " + e.getMessage());
                }
            }
        } else {

        }
    }
}