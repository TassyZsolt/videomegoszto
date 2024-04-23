package hu.adatb.videomegoszto.common;

import hu.adatb.videomegoszto.model.User;
import javafx.scene.control.Alert;

public class Helper {

    public static User cUser;
    public static String textExtender(String input) {
        if (input == null) {
            input = "";
        }

        if (input.length() < 50) {

            return String.format("%-50s", input);
        } else {

            return input;
        }
    }

    public static void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
