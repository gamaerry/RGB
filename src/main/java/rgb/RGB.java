package rgb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RGB extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var escena = new Scene(new FXMLLoader(RGB.class.getResource("principal.fxml")).load());
        stage.setTitle(getClass().getSimpleName());
        stage.setScene(escena);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}