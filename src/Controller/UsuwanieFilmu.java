package Controller;

import Model.BazaDanych;
import Model.Operation;
import Model.User;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UsuwanieFilmu implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {

        if (s == null) {
            String idStr = JOptionPane.showInputDialog("Podaj ID filmu do usunięcia (lub pozostaw puste, aby anulować):");

            if (idStr == null || idStr.isEmpty()) return;

            try {
                int id = Integer.parseInt(idStr);


                int confirm = JOptionPane.showConfirmDialog(null,
                        "Czy na pewno chcesz usunąć film o ID: " + id + "?",
                        "Potwierdzenie", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    usunZByzy(bazaDanych, id);
                    JOptionPane.showMessageDialog(null, "Status filmu zmieniony na 'usunięty' (2).");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ID musi być liczbą!", "Błąd", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Błąd bazy danych: " + e.getMessage());
            }
        } else {

            System.out.println("Podaj ID: (-1 by wyświetlić wszystkie filmy)");
            int ID = s.nextInt();

        }
    }


    private void usunZByzy(BazaDanych db, int id) throws SQLException {

        String update = "UPDATE film SET dostepny = '2' WHERE ID = ?;";
        try (PreparedStatement ps = db.getConnection().prepareStatement(update)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}