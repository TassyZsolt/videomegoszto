package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.App;
import hu.adatb.videomegoszto.common.Helper;
import hu.adatb.videomegoszto.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {
    @FXML
    Label wc;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wc.setText("Üdv: "+ Helper.cUser.getNev());
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        Helper.cUser = null;
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");
    }

    public void goPwChange(ActionEvent actionEvent) {
        App.loadFXML("/hu/adatb/videomegoszto/pwChange.fxml");
    }

    public void goUserChange(ActionEvent actionEvent) {
        App.loadFXML("/hu/adatb/videomegoszto/userNameChange.fxml");
    }

    public void deleteUser(ActionEvent actionEvent) {
        String sql = "DELETE FROM FELHASZNALOK WHERE FELHASZNALOID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, Helper.cUser.getFelhasznaloId());
            int affectedRows = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL error occurred during deleting user: " + e.getMessage());
            e.printStackTrace();

        } finally {
            DatabaseConnection.closeConnection();
        }



        Helper.cUser = null;
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");
    }
}
