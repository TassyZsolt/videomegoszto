package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.App;
import hu.adatb.videomegoszto.common.PasswordManager;
import hu.adatb.videomegoszto.database.DatabaseConnection;
import hu.adatb.videomegoszto.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import static hu.adatb.videomegoszto.common.Helper.*;

public class RegistrationController {

    @FXML
    TextField name;
    @FXML
    TextField email;
    @FXML
    PasswordField pw;
    public void registry(ActionEvent actionEvent) {
        String sql = "INSERT INTO FELHASZNALOK (FELHASZNALOID, NEV, EMAIL, JELSZO, REGISZTRACIO_DATUMA) VALUES (?, ?, ?, ?, ?)";
        java.sql.Date today = new java.sql.Date(new Date().getTime());
        int nextUserId = getNextUserId();
        String base64Pw = "";
        switch (validator()) {
            case 1:
                showAlert("Ez a felhasználónév már foglalt!");
                return;
            case 2:
                showAlert("Ez az email cím már foglalt!");
                return;
            case 4:
                showAlert("Nem megfelelő jelszó (legalább 6 karakter)!");
                return;
        }


        try {
            base64Pw = PasswordManager.encrypt(pw.getText());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
                pstmt.setString(4, base64Pw);
                pstmt.setDate(5, today);

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    conn.commit();
                    System.out.println("Felhasználó sikeresen hozzáadva!");
                    User user = new User(nextUserId,name.getText(),email.getText(),pw.getText(),today.toString());
                    cUser = user;
                    App.loadFXML("/hu/adatb/videomegoszto/dashboard.fxml");
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

    private int validator() {
        if (isUsernameEx(name.getText())) {
         return 1;
        }
        if (isEmailEx(email.getText())) {
        return 2;
        }
        if (pw.getText().length() < 6){
            return 4;
        }
        return 0;
    }


    private boolean isEmailEx(String text) {
        String sql = "SELECT EMAIL FROM FELHASZNALOK WHERE EMAIL = ?";
        boolean exists = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textExtender(text));
            ResultSet rs = pstmt.executeQuery();

            exists = rs.next();
        } catch (SQLException e) {
            System.out.println("SQL hiba történt a lekérdezés során: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }

        return exists;
    }

    private boolean isUsernameEx(String text) {
        String sql = "SELECT * FROM FELHASZNALOK WHERE NEV = ?";
        boolean exists = false;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, textExtender(text));
            ResultSet rs = pstmt.executeQuery();

            exists = rs.next();
        } catch (SQLException e) {
            System.out.println("SQL hiba történt a lekérdezés során: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }

        return exists;
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


    public void goLogin(ActionEvent actionEvent) {
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");
    }
}
