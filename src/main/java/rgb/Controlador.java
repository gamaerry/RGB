package rgb;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controlador {
    @FXML
    private Vista vista;
    @FXML
    private TextField campos;
    @FXML
    private Slider deslizadorR, deslizadorG, deslizadorB;
    @FXML
    private CheckBox fijar;

    @FXML
    private void initialize() {
        //Aplicar el color por defecto RGB(0, 0, 0)
        vista.aplicarRGB();

        //Definición de las properties del texto del TextField y de los valores de los Sliders
        var propertyTexto = campos.textProperty();
        var propertyDeslizadorR = deslizadorR.valueProperty();
        var propertyDeslizadorG = deslizadorG.valueProperty();
        var propertyDeslizadorB = deslizadorB.valueProperty();

        //Hacer reaccionar los valores r, g, b del preview a los valores de los Sliders
        vista.propertyR().bind(propertyDeslizadorR);
        vista.propertyG().bind(propertyDeslizadorG);
        vista.propertyB().bind(propertyDeslizadorB);

        //Aplicar los cambios cada que se modifiquen los valores de los Sliders
        propertyDeslizadorR.addListener(observable -> vista.aplicarRGB());
        propertyDeslizadorG.addListener(observable -> vista.aplicarRGB());
        propertyDeslizadorB.addListener(observable -> vista.aplicarRGB());

        //Hacer reaccionar los valores de los Sliders al texto del TextField
        propertyTexto.addListener((observable, stringAnterior, stringActual) -> {
            if (stringActual.matches(" *\\d+, *\\d+, *\\d+ *")) {
                final String[] RGB = stringActual.split(",");
                deslizadorR.setValue(Double.parseDouble(RGB[0].trim()));
                deslizadorG.setValue(Double.parseDouble(RGB[1].trim()));
                deslizadorB.setValue(Double.parseDouble(RGB[2].trim()));
            }
        });

        //Activar y desactivar (según se enfoque el TextField) la reacción del texto del TextField a los valores de
        // los Sliders (es necesario desactivarlo pues al estar activo el enlace, no se puede modificar el TextField)
        campos.focusedProperty().addListener((observable, estabaEnfocado, estaEnfocado) -> {
            if (estaEnfocado)
                propertyTexto.unbind();
            else
                propertyTexto.bind(propertyDeslizadorR.asString("%.0f").
                        concat(propertyDeslizadorG.asString(", %.0f")).
                        concat(propertyDeslizadorB.asString(", %.0f")));
        });

        fijar.selectedProperty().addListener((observable, estabaSeleccionado, estaSeleccionado) -> {
        });
    }
}