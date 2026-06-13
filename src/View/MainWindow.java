package View;

import Model.*;
import Controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainWindow extends JFrame {
    private User loggedUser;
    private BazaDanych bazaDanych;
    private JTable movieTable;
    private DefaultTableModel tableModel;
    private JLabel welcomeLabel;

    public MainWindow(User user, BazaDanych db) {
        this.loggedUser = user;
        this.bazaDanych = db;

        setTitle("Wypożyczalnia DVD - Panel: " + (user instanceof Admin ? "Administrator" : "Klient"));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(230, 230, 230));
        headerPanel.add(new JLabel("Zalogowany: " + user.getImie() + " " + user.getNazwisko()));
        add(headerPanel, BorderLayout.NORTH);


        String[] columnNames = {"ID", "Tytuł", "Gatunek", "Reżyser", "Cena", "Rok", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        movieTable = new JTable(tableModel);

        refreshMovies();
        add(new JScrollPane(movieTable), BorderLayout.CENTER);


        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (loggedUser instanceof Admin) {
            addAdminButtons(sidePanel);
        } else {
            addKlientButtons(sidePanel);
        }

        JButton logoutBtn = new JButton("Wyloguj");
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addActionListener(e -> {
            new LoginWindow().setVisible(true);
            this.dispose();
        });

        sidePanel.add(Box.createVerticalGlue());
        sidePanel.add(logoutBtn);

        add(sidePanel, BorderLayout.EAST);

        SystemKarBackground monitor = new SystemKarBackground(bazaDanych, loggedUser);
        monitor.start();
    }

    private void addAdminButtons(JPanel panel) {
        JButton addBtn = new JButton("Dodaj Film");
        JButton editBtn = new JButton("Modyfikuj Film");
        JButton deleteBtn = new JButton("Usuń Film");
        JButton changePassBtn = new JButton("Zmień Hasło");
        JButton editMyDataBtn = new JButton("Edytuj moje dane");
        JButton addAdminBtn = new JButton("Dodaj Admina");
        JButton viewAllRentalsBtn = new JButton("Wszystkie Wypożyczenia");


        addBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Otwieranie formularza dodawania...");
            new DodawanieFilmu().Operation(bazaDanych, null, loggedUser);
            refreshMovies();
        });

        deleteBtn.addActionListener(e -> {

            int selectedRow = movieTable.getSelectedRow();

            if (selectedRow != -1) {

                int idZTabeli = (int) tableModel.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Czy na pewno usunąć wybrany film: " + tableModel.getValueAt(selectedRow, 1) + "?",
                        "Usuwanie", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {

                        String update = "UPDATE film SET dostepny = '2' WHERE ID = '" + idZTabeli + "';";
                        bazaDanych.getStatement().executeUpdate(update);

                        JOptionPane.showMessageDialog(this, "Film usunięty.");
                        refreshMovies();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {

                new UsuwanieFilmu().Operation(bazaDanych, null, loggedUser);
                refreshMovies();
            }
        });


        editBtn.addActionListener(e -> {
            int selectedRow = movieTable.getSelectedRow();
            ModyfikacjaFilmu mf = new ModyfikacjaFilmu();

            if (selectedRow != -1) {

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                mf.modyfikujOkienkowo(bazaDanych, id);
            } else {

                mf.Operation(bazaDanych, null, loggedUser);
            }
            refreshMovies();
        });

        changePassBtn.addActionListener(e -> {
            new ZmienHaslo().Operation(bazaDanych, null, loggedUser);
        });

        editMyDataBtn.addActionListener(e -> {
            new EdytowanieUzytkownika().Operation(bazaDanych, null, loggedUser);

            welcomeLabel.setText("Witaj, " + loggedUser.getImie() + "!");
        });
        addAdminBtn.addActionListener(e -> {

            new DodawanieKonta(1).Operation(bazaDanych, null, loggedUser);
        });
        viewAllRentalsBtn.addActionListener(e -> {

            new WypozyczeniaKlientow().Operation(bazaDanych, null, loggedUser);
        });


        styleButton(addBtn, editBtn, deleteBtn, changePassBtn, editMyDataBtn, addAdminBtn, viewAllRentalsBtn);
        panel.add(addBtn); panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(editBtn); panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(deleteBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(changePassBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(editMyDataBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addAdminBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(viewAllRentalsBtn);
    }

    private void addKlientButtons(JPanel panel) {
        JButton rentBtn = new JButton("Wypożycz Film");
        JButton returnBtn = new JButton("Zwróć Film");
        JButton myRentalsBtn = new JButton("Moje Wypożyczenia");
        JButton changePassBtn = new JButton("Zmień Hasło");
        JButton editMyDataBtn = new JButton("Edytuj moje dane");


        rentBtn.addActionListener(e -> {
            int selectedRow = movieTable.getSelectedRow();
            WypozyczFilm wf = new WypozyczFilm();

            if (selectedRow != -1) {

                int id = (int) tableModel.getValueAt(selectedRow, 0);
                wf.wykonajWypozyczenie(bazaDanych, id, loggedUser);
            } else {

                wf.Operation(bazaDanych, null, loggedUser);
            }
            refreshMovies();
        });

        changePassBtn.addActionListener(e -> {
            new ZmienHaslo().Operation(bazaDanych, null, loggedUser);
        });

        returnBtn.addActionListener(e -> {

            new OddacFilm().Operation(bazaDanych, null, loggedUser);


            refreshMovies();
        });

        myRentalsBtn.addActionListener(e -> {

            new WyswietlWypozyczenia(loggedUser.getID()).Operation(bazaDanych, null, loggedUser);
        });

        editMyDataBtn.addActionListener(e -> {
            new EdytowanieUzytkownika().Operation(bazaDanych, null, loggedUser);
            welcomeLabel.setText("Witaj, " + loggedUser.getImie() + "!");
        });



        styleButton(rentBtn, returnBtn, myRentalsBtn, changePassBtn);
        panel.add(rentBtn); panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(returnBtn); panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(myRentalsBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(changePassBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(editMyDataBtn);
    }

    private void refreshMovies() {
        tableModel.setRowCount(0);
        try {
            ResultSet rs = bazaDanych.getStatement().executeQuery("SELECT * FROM film WHERE dostepny < 2");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("ID"),
                        rs.getString("tytul"),
                        rs.getString("gatunek"),
                        rs.getString("rezyser"),
                        rs.getInt("cena"),
                        rs.getInt("rok_produkcji"),
                        rs.getInt("dostepny") == 0 ? "Dostępny" : "Zajęty"
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton... buttons) {
        for (JButton b : buttons) {
            b.setMaximumSize(new Dimension(150, 40));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }
}