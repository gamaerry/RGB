package mx.umich.fismat.rgb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controlador {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}