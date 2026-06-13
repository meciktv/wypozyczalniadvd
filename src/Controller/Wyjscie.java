package Controller;

import java.util.Scanner;

import Model.BazaDanych;
import Model.Operation;
import Model.User;

public class Wyjscie implements Operation {

    @Override
    public void Operation(BazaDanych bazaDanych, Scanner s, User user) {

        System.out.println("Dziękujemy za wizytę!");
        s.close();

    }

}
