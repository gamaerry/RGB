package rgb;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controlador {
    @FXML
    private Preview preview;
    @FXML
    private TextField campos;
    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;

    @FXML
    private void initialize() {
        preview.aplicarRGB();

        DoubleProperty
                redSliderProperty = redSlider.valueProperty(),
                greenSliderProperty = greenSlider.valueProperty(),
                blueSliderProperty = blueSlider.valueProperty();

        preview.rProperty().bind(redSliderProperty);
        preview.gProperty().bind(greenSliderProperty);
        preview.bProperty().bind(blueSliderProperty);

        redSliderProperty.addListener(observable -> preview.aplicarRGB());
        greenSliderProperty.addListener(observable -> preview.aplicarRGB());
        blueSliderProperty.addListener(observable -> preview.aplicarRGB());

        campos.textProperty().bind(
                redSliderProperty.asString("%.0f")
                .concat(greenSliderProperty.asString(", %.0f"))
                .concat(blueSliderProperty.asString(", %.0f")));
    }
}