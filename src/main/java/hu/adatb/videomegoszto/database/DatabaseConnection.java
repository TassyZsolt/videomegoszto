package hu.adatb.videomegoszto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:oracle:thin:@localhost:1521/XE";
                String user = "tzsolt";
                String password = "Bigmeo01";
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Kapcsolat létrehozva!");
            } catch (SQLException e) {
                System.out.println("Nem sikerült csatlakozni az adatbázishoz: " + e.getMessage());
                throw new RuntimeException("Nem sikerült csatlakozni az adatbázishoz", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Kapcsolat lezárva.");
                connection = null; // Nullázd le, hogy újra létre lehessen hozni szükség esetén
            } catch (SQLException e) {
                System.out.println("Nem sikerült lezárni a kapcsolatot: " + e.getMessage());
            }
        }
    }
}
