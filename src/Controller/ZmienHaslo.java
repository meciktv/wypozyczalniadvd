package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import javax.swing.*;

import Model.BazaDanych;
import Model.Operation;
import Model.User;

public class ZmienHaslo implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {
        if (s == null) {

            JPasswordField oldPassField = new JPasswordField();
            JPasswordField newPassField = new JPasswordField();
            JPasswordField confirmPassField = new JPasswordField();

            Object[] message = {
                    "Stare hasło:", oldPassField,
                    "Nowe hasło:", newPassField,
                    "Potwierdź nowe hasło:", confirmPassField
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Zmiana hasła", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String oldPass = new String(oldPassField.getPassword());
                String newPass = new String(newPassField.getPassword());
                String confirmPass = new String(confirmPassField.getPassword());


                if (!oldPass.equals(user.getPassword())) {
                    JOptionPane.showMessageDialog(null, "Stare hasło jest niepoprawne!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nowe hasło nie może być puste!");
                    return;
                }

                if (!newPass.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(null, "Nowe hasła nie są identyczne!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String update = "UPDATE `user` SET `haslo`=? WHERE `ID`=?";
                    PreparedStatement ps = bazaDanych.getConnection().prepareStatement(update);
                    ps.setString(1, newPass);
                    ps.setInt(2, user.getID());

                    ps.executeUpdate();
                    user.setPassword(newPass); // Aktualizujemy hasło w obiekcie sesji
                    JOptionPane.showMessageDialog(null, "Hasło zostało pomyślnie zmienione.");

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage());
                }
            }
        } else {
            //CREATE USER 'user'@'localhost' IDENTIFIED BY '';
            //GRANT ALL PRIVILEGES ON wypozyczalniadvd.* TO 'user'@'localhost';
        }
    }
}