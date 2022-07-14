package rgb;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controlador {

    @FXML
    private Preview preview;
    @FXML
    private TextField campos;
    @FXML
    private Slider rSlider, gSlider, bSlider;
    @FXML
    private CheckBox fijar;

    @FXML
    private void initialize() {
        //Aplicar el color por defecto RGB(0, 0, 0)
        preview.aplicarRGB();


        //Definición de las properties del texto del TextField y de los valores de los Sliders
        StringProperty textProperty = campos.textProperty();
        DoubleProperty rSliderProperty = rSlider.valueProperty(),
                gSliderProperty = gSlider.valueProperty(),
                bSliderProperty = bSlider.valueProperty();

        //Hacer reaccionar los valores r, g, b del preview a los valores de los Sliders
        preview.rProperty().bind(rSliderProperty);
        preview.gProperty().bind(gSliderProperty);
        preview.bProperty().bind(bSliderProperty);

        //Aplicar los cambios cada que se modifiquen los valores de los Sliders
        rSliderProperty.addListener(observable -> preview.aplicarRGB());
        gSliderProperty.addListener(observable -> preview.aplicarRGB());
        bSliderProperty.addListener(observable -> preview.aplicarRGB());

        //Hacer reaccionar los valores de los Sliders al texto del TextField
        textProperty.addListener((observable, old, actualString) -> {
            if (actualString.matches(" *\\d+, *\\d+, *\\d+ *")) {
                final String[] RGB = actualString.split(",");
                rSlider.setValue(Double.parseDouble(RGB[0].trim()));
                gSlider.setValue(Double.parseDouble(RGB[1].trim()));
                bSlider.setValue(Double.parseDouble(RGB[2].trim()));
            }
        });

        //Activar y desactivar (según se enfoque el TextField) la reacción del texto del TextField a los valores de los Sliders
        //(Es necesario desactivarlo pues al estar activo el enlace, no se puede modificar el TextField)
        campos.focusedProperty().addListener((observable, old, isNowFocused) -> {
            if (isNowFocused)
                textProperty.unbind();
            else
                textProperty.bind(rSliderProperty.asString("%.0f")
                        .concat(gSliderProperty.asString(", %.0f"))
                        .concat(bSliderProperty.asString(", %.0f"))
                );
        });

        fijar.selectedProperty().addListener((observable, old, isNowSelected) -> {});
    }
}