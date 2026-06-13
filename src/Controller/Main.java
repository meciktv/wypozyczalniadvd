package Controller;

import Model.BazaDanych;
import View.LoginWindow;
import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {

        BazaDanych bazaDanych = new BazaDanych();


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                LoginWindow loginWindow = new LoginWindow();
                loginWindow.setVisible(true);
            }
        });


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            bazaDanych.close();
            System.out.println("Połączenie z bazą danych zostało zamknięte.");
        }));
    }
}