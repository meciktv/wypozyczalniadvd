package Controller;

import Model.BazaDanych;
import Model.User;
import Model.Admin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SystemKarBackground extends Thread {
    private BazaDanych bazaDanych;
    private User zalogowanyUzytkownik;
    private boolean running = true;

    // Zbiór do przechowywania ID wypożyczeń, o których już powiadomiliśmy w GUI
    private Set<Integer> wyswietloneAlerty = new HashSet<>();

    public SystemKarBackground(BazaDanych db, User user) {
        this.bazaDanych = db;
        this.zalogowanyUzytkownik = user;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Jeśli użytkownik wylogował się lub okno zamknięto, przerywamy sprawdzanie GUI
                if (zalogowanyUzytkownik == null) {
                    TimeUnit.SECONDS.sleep(5);
                    continue;
                }

                System.out.println("[Background Thread] Sprawdzanie opóźnień...");

                // Pobieramy ID, DateTime, godziny oraz ID użytkownika, który wypożyczył film
                String sql = "SELECT ID, DateTime, godziny, user FROM wypozyczenia WHERE status = 0";
                ResultSet rs = bazaDanych.getStatement().executeQuery(sql);

                while (rs.next()) {
                    int idWypozyczenia = rs.getInt("ID");
                    String dataWypozyczenia = rs.getString("DateTime");
                    int limitGodzin = rs.getInt("godziny");
                    int idKlientaZButa = rs.getInt("user");

                    Model.Wypozyczanie w = new Model.Wypozyczanie();
                    w.setDateTime(dataWypozyczenia);
                    w.setGodziny(limitGodzin);

                    if (w.getOpoznienie() > 0) {
                        System.out.println("[ALARM w konsoli] Spóźnienie ID: " + idWypozyczenia);

                        // WARUNKI WYŚWIETLENIA OKIENKA:
                        // 1. Nie wyświetliliśmy tego powiadomienia jeszcze w tej sesji
                        // ORAZ
                        // 2. (Zalogowany to Admin) LUB (Zalogowany to klient, do którego należy to wypożyczenie)
                        boolean czyAdmin = (zalogowanyUzytkownik instanceof Admin);
                        boolean czyToMojeWypozyczenie = (zalogowanyUzytkownik.getID() == idKlientaZButa);

                        if (!wyswietloneAlerty.contains(idWypozyczenia) && (czyAdmin || czyToMojeWypozyczenie)) {

                            // Zapisujemy, że ten alert już poszedł na ekran, żeby nie wyskoczył za 30 sekund
                            wyswietloneAlerty.add(idWypozyczenia);

                            // Przygotowanie wiadomości zależnie od roli
                            String komunikat = czyAdmin
                                    ? "Wykryto opóźnienie w systemie!\nWypożyczenie nr: " + idWypozyczenia + "\nOpóźnienie: " + w.getOpoznienie() + "h"
                                    : "Przypomnienie!\nTwój termin zwrotu minął o: " + w.getOpoznienie() + "h.\nProsimy o zwrot filmu nr wypożyczenia: " + idWypozyczenia;

                            javax.swing.SwingUtilities.invokeLater(() -> {
                                javax.swing.JOptionPane.showMessageDialog(null,
                                        komunikat,
                                        "Systemowy Alert Opóźnień",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                            });
                        }
                    }
                }

                TimeUnit.SECONDS.sleep(30);

            } catch (SQLException | InterruptedException e) {
                System.out.println("Wątek kar przerwany: " + e.getMessage());
                running = false;
            }
        }
    }
}