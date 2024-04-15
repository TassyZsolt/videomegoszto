module hu.adatb.videomegoszto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens hu.adatb.videomegoszto to javafx.fxml;
    exports hu.adatb.videomegoszto;
    exports hu.adatb.videomegoszto.controller;
    opens hu.adatb.videomegoszto.controller to javafx.fxml;
}