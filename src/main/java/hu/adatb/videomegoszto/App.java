package hu.adatb.videomegoszto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private static Stage stage;
    public static FXMLLoader loadFXML(String fxml){

        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = null;
        try {
            Parent root = loader.load();
            scene = new Scene(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(scene);
        return loader;
    }
    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        App.loadFXML("/hu/adatb/videomegoszto/main.fxml");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}