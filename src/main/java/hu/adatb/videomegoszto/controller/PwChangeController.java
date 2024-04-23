package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.App;
import hu.adatb.videomegoszto.common.Helper;
import hu.adatb.videomegoszto.common.PasswordManager;
import hu.adatb.videomegoszto.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PwChangeController {
    @FXML
    TextField newPw;

    public void logout(ActionEvent actionEvent) {
        Helper.cUser = null;
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");
    }

    public void pwChange(ActionEvent actionEvent) throws Exception {
        String sql = "UPDATE FELHASZNALOK SET NEV = ? WHERE FELHASZNALOID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String base64 = PasswordManager.encrypt(newPw.getText());
            pstmt.setString(1, base64);
            pstmt.setInt(2, Helper.cUser.getFelhasznaloId());

            int affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL error occurred during updating user name: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}
