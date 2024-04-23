package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.App;
import hu.adatb.videomegoszto.common.Helper;
import hu.adatb.videomegoszto.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserNameChangeController {
    @FXML
    TextField newusername;

    public void logout(ActionEvent actionEvent) {
        Helper.cUser = null;
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");
    }



    public void changeUserneme(ActionEvent actionEvent) {

        String sql = "UPDATE FELHASZNALOK SET NEV = ? WHERE FELHASZNALOID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newusername.getText());
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

