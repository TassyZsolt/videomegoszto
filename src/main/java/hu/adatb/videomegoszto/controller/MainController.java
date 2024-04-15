package hu.adatb.videomegoszto.controller;

import hu.adatb.videomegoszto.App;
import hu.adatb.videomegoszto.database.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {


    @FXML
    protected void login() {
        DatabaseConnection.getConnection();
    }

    public void goRegistration(ActionEvent actionEvent) throws IOException {
        App.loadFXML("/hu/adatb/videomegoszto/registration.fxml");

    }

    public void goLogin(ActionEvent actionEvent) {
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");

    }

}