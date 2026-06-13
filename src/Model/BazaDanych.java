package Model;

import java.sql.*; // Importowanie klas z pakietu java.sql do obsługi baz danych

// Klasa reprezentująca połączenie z bazą danych
public class BazaDanych {

    private String user = "user"; // Nazwa użytkownika do bazy danych
    private String password = ""; // Hasło do bazy danych
    private String url = "jdbc:mysql://localhost/wypozyczalniadvd"; // URL do bazy danych
    private Connection connection; // Obiekt do połączenia z bazą danych
    private Statement statement; // Obiekt do wykonywania zapytań SQL

    //GRANT ALL PRIVILEGES ON wypozyczalniadvd.* TO user@'localhost'; w sql

    // Konstruktora klasy BazaDanych
    public BazaDanych() {
        try {
            // Nawiązanie połączenia z bazą danych
            connection = DriverManager.getConnection(url, user, password);
            // Utworzenie obiektu Statement do wykonywania zapytań
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace(); // Wydrukowanie śladu stosu w przypadku wystąpienia błędu SQL
        }
    }

    // Metoda zwracająca obiekt Connection
    public Connection getConnection() {
        return connection; // Zwrócenie obiektu Connection
    }

    // Metoda zwracająca obiekt Statement
    public Statement getStatement() {
        return statement; // Zwrócenie obiektu Statement
    }


    public void close() {
        try {
            if (statement != null) {
                statement.close(); // Zamknięcie obiektu Statement
            }
            if (connection != null) {
                connection.close(); // Zamknięcie połączenia
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Wydrukowanie błędu w przypadku problemów z zamykaniem
        }
    }
}