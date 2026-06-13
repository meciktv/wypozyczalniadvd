package Controller;

import Model.Operation;
import Model.BazaDanych;
import Model.User;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DodawanieFilmu implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {

        if (s == null) {
            try {
                String tytul = JOptionPane.showInputDialog("Podaj tytuł:");
                if (tytul == null) return; // Anulowano

                String gatunek = JOptionPane.showInputDialog("Podaj gatunek:");
                String rezyser = JOptionPane.showInputDialog("Podaj reżysera:");

                String cenaStr = JOptionPane.showInputDialog("Podaj cenę (liczba):");
                int cena = Integer.parseInt(cenaStr);

                String rokStr = JOptionPane.showInputDialog("Podaj rok produkcji:");
                int rok_produkcji = Integer.parseInt(rokStr);

                dodajDoBazy(bazaDanych, tytul, gatunek, rezyser, cena, rok_produkcji);

                JOptionPane.showMessageDialog(null, "Film został dodany pomyślnie!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Błąd: Cena i rok muszą być liczbami!", "Błąd danych", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage());
            }
        } else {

            System.out.println("Tryb konsolowy aktywny...");
        }
    }


    private void dodajDoBazy(BazaDanych db, String tytul, String gatunek, String rezyser, int cena, int rok) throws SQLException {
        String insertQuery = "INSERT INTO film(tytul, gatunek, rezyser, cena, rok_produkcji, dostepny) VALUES (?, ?, ?, ?, ?, 0);";
        try (PreparedStatement ps = db.getConnection().prepareStatement(insertQuery)) {
            ps.setString(1, tytul);
            ps.setString(2, gatunek);
            ps.setString(3, rezyser);
            ps.setInt(4, cena);
            ps.setInt(5, rok);
            ps.executeUpdate();
        }
    }
}