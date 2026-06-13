package View;

import Model.*;
import Controller.DodawanieKonta; // Import operacji rejestracji
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginWindow extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton regBtn;
    private JButton exitBtn;
    private BazaDanych bazaDanych;



    public LoginWindow() {
        bazaDanych = new BazaDanych();

        setTitle("Wypożyczalnia DVD - Logowanie");
        setSize(400, 250); // Nieco szersze, by zmieścić 3 przyciski
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JLabel headerLabel = new JLabel("Logowanie do systemu", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(headerLabel, BorderLayout.NORTH);


        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        formPanel.add(emailField);

        formPanel.add(new JLabel("Hasło:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new FlowLayout());

        loginBtn = new JButton("Zaloguj");
        regBtn = new JButton("Zarejestruj się");
        exitBtn = new JButton("Wyjdź");

        buttonPanel.add(loginBtn);
        buttonPanel.add(regBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.SOUTH);




        loginBtn.addActionListener(e -> performLogin());


        regBtn.addActionListener(e -> {

            new DodawanieKonta(0).Operation(bazaDanych, null, null);
        });


        exitBtn.addActionListener(e -> System.exit(0));
    }

    private void performLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Wypełnij wszystkie pola!", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            String query = "SELECT * FROM user WHERE email = '" + email + "' AND haslo = '" + password + "'";
            ResultSet rs = bazaDanych.getStatement().executeQuery(query);

            if (rs.next()) {
                int type = rs.getInt("typ");
                User user;

                if (type == 1) {
                    user = new Admin();
                } else {
                    user = new Klient();
                }

                user.setID(rs.getInt("ID"));
                user.setImie(rs.getString("imie"));
                user.setNazwisko(rs.getString("nazwisko"));
                user.setEmail(rs.getString("email"));
                user.setTelefon(rs.getString("telefon"));
                user.setPassword(rs.getString("haslo"));

                JOptionPane.showMessageDialog(this, "Witaj " + user.getImie() + "!");

                new MainWindow(user, bazaDanych).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Niepoprawny email lub hasło.", "Błąd logowania", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Błąd bazy danych: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}