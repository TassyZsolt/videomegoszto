package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class RegistrationController {

    @FXML
    TextField name;
    @FXML
    TextField email;
    @FXML
    TextField pw;
    public void registry(ActionEvent actionEvent) {
        String sql = "INSERT INTO FELHASZNALOK (FELHASZNALOID, NEV, EMAIL, JELSZO, REGISZTRACIO_DATUMA) VALUES (?, ?, ?, ?, ?)";
        java.sql.Date today = new java.sql.Date(new Date().getTime());
        int nextUserId = getNextUserId();

        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Nincs érvényes adatbázis kapcsolat.");
            }
            conn.setAutoCommit(false);


            if (nextUserId == -1) {
                throw new RuntimeException("Nem sikerült új felhasználói ID-t generálni.");
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, nextUserId);
                pstmt.setString(2, name.getText());
                pstmt.setString(3, email.getText());
                pstmt.setString(4, pw.getText());
                pstmt.setDate(5, today);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();
                    System.out.println("Felhasználó sikeresen hozzáadva!");
                } else {
                    conn.rollback();
                    System.out.println("Nem sikerült hozzáadni a felhasználót.");
                }
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("SQL hiba történt az adatbeszúrás során: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("Adatbázis kapcsolati hiba: " + e.getMessage());
            e.printStackTrace();
        }
    }



    private int getNextUserId() {
        String sql = "SELECT FELHASZNALOID FROM FELHASZNALOK ORDER BY FELHASZNALOID DESC FETCH FIRST 1 ROWS ONLY";
        int lastUserId = 1; // Kezdő érték, ha nincs még rekord
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                lastUserId = rs.getInt("FELHASZNALOID") + 1;
            }
            System.out.println("A következő felhasználó ID-je: " + lastUserId);
        } catch (SQLException e) {
            System.out.println("SQL hiba történt a lekérdezés során: " + e.getMessage());
            e.printStackTrace();
        }
        DatabaseConnection.closeConnection();
        return lastUserId;
    }

}
