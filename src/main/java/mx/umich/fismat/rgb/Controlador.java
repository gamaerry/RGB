package mx.umich.fismat.rgb;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class Controlador {
    private double r=0, g=0, b=0;
    @FXML private Preview preview;
    @FXML private TextField campos;
    @FXML private Slider red;
    @FXML private Slider green;
    @FXML private Slider blue;
    @FXML private void initialize(){
        preview.aplicarRGB();
        campos.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+,\\d+,\\d+")){
                String[] rgb=newValue.split(",");
                r=Double.parseDouble(rgb[0]);
                g=Double.parseDouble(rgb[1]);
                b=Double.parseDouble(rgb[2]);
                red.setValue(r);
                green.setValue(g);
                blue.setValue(b);
                preview.setStyle(String.format("-fx-background-color: rgb(%f,%f,%f)",r,g,b));
            }
        });
        red.valueProperty().bind(preview.rProperty());
        green.valueProperty().bind(preview.gProperty());
        blue.valueProperty().bind(preview.bProperty());
        red.valueChangingProperty().addListener(observable -> preview.aplicarRGB());
        green.valueChangingProperty().addListener(observable -> preview.aplicarRGB());
        blue.valueChangingProperty().addListener(observable -> preview.aplicarRGB());
    }
}