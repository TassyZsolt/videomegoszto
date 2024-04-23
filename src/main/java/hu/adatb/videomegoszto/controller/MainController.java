package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.App;
import hu.adatb.videomegoszto.common.Helper;
import hu.adatb.videomegoszto.common.PasswordManager;
import hu.adatb.videomegoszto.database.DatabaseConnection;
import hu.adatb.videomegoszto.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML
    TextField userName;
    @FXML
    PasswordField pw;
/**
 *    private int felhasznaloId;
 *     private String nev;
 *     private String email;
 *     private String jelszo;
 *     private String regisztracioDatuma;
 * */
    @FXML
    protected void login() throws Exception {
        String sql = "SELECT * FROM FELHASZNALOK WHERE NEV = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, Helper.textExtender(userName.getText()));
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) {
                Helper.showAlert("nem létező felhasználó név!");
                return;
            }

            User user = new User();

                    String pwTmp = rs.getString("JELSZO" );
                    String encoded = PasswordManager.encrypt(pw.getText());
                if (!Helper.textExtender(encoded).equals(pwTmp)) {
                    Helper.showAlert("nem megfelelő jelszó");
                    return;
                }
               user.setFelhasznaloId(rs.getInt("FELHASZNALOID"));
               user.setNev(rs.getString("NEV"));
               user.setEmail(rs.getString("EMAIL"));
               user.setJelszo(pwTmp);
               user.setRegisztracioDatuma(rs.getString("REGISZTRACIO_DATUMA"));

            Helper.cUser = user;
            App.loadFXML("/hu/adatb/videomegoszto/dashboard.fxml");
        } catch (SQLException e) {
            System.out.println("SQL error occurred during query: " + e.getMessage());
            e.printStackTrace();
        }finally {
            DatabaseConnection.closeConnection();
        }
    }

    public void goRegistration(ActionEvent actionEvent) throws IOException {
        App.loadFXML("/hu/adatb/videomegoszto/registration.fxml");

    }


}