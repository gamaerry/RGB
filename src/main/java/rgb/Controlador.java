package rgb;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controlador {
    private int r = 0, g = 0, b = 0;
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

        DoubleProperty redSliderProperty = redSlider.valueProperty(),
                greenSliderProperty = greenSlider.valueProperty(),
                blueSliderProperty = blueSlider.valueProperty();

        preview.rProperty().bind(redSliderProperty);
        preview.gProperty().bind(greenSliderProperty);
        preview.bProperty().bind(blueSliderProperty);

        redSliderProperty.addListener(l -> {
            preview.aplicarRGB();
        });
        greenSliderProperty.addListener(l -> {
            preview.aplicarRGB();
        });
        blueSliderProperty.addListener(l -> {
            preview.aplicarRGB();
        });

        campos.textProperty().bind(redSliderProperty.asString("%.0f")
                .concat(greenSliderProperty.asString(", %.0f"))
                .concat(blueSliderProperty.asString(", %.0f")));
    }
}