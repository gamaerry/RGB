package rgb;

import javafx.beans.value.ChangeListener;
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
    private Slider deslizadorR, deslizadorAuxR, deslizadorG, deslizadorAuxG, deslizadorB, deslizadorAuxB;
    @FXML
    private CheckBox fijar;

    @FXML
    private void initialize() {
        //Aplicar el color por defecto RGB(0, 0, 0)
        vista.aplicarRGB();

        //Hacer invisible a los deslizadores auxiliares
        deslizadorAuxR.setVisible(false);
        deslizadorAuxG.setVisible(false);
        deslizadorAuxB.setVisible(false);

        //Hacer reaccionar los valores r, g, b del preview a los valores de los Sliders
        vista.propertyR().bind(deslizadorR.valueProperty());
        vista.propertyG().bind(deslizadorG.valueProperty());
        vista.propertyB().bind(deslizadorB.valueProperty());

        //Aplicar los cambios cada que se modifiquen los valores de los Sliders
        // y hacer invisibles si son menores que cero o mayores que 255
        deslizadorR.valueProperty().addListener((observable, valorAnterior, valorActual) ->
                invisibleSiFueraDeRango(valorActual.doubleValue() < 0,
                        valorActual.doubleValue() > 255, deslizadorR, deslizadorAuxR));
        deslizadorG.valueProperty().addListener((observable, valorAnterior, valorActual) ->
                invisibleSiFueraDeRango(valorActual.doubleValue() < 0,
                        valorActual.doubleValue() > 255, deslizadorG, deslizadorAuxG));
        deslizadorB.valueProperty().addListener((observable, valorAnterior, valorActual) ->
                invisibleSiFueraDeRango(valorAnterior.doubleValue() < 0,
                        valorAnterior.doubleValue() > 255, deslizadorB, deslizadorAuxB));

        //Cuando termine de estar "cambiandose" el valor del deslizador se cambiará
        // a 0 o 255 segun corresponda
        deslizadorR.valueChangingProperty().addListener(arreglarSiEranInvisibles());
        deslizadorG.valueChangingProperty().addListener(arreglarSiEranInvisibles());
        deslizadorB.valueChangingProperty().addListener(arreglarSiEranInvisibles());

        //Hacer reaccionar los valores de los deslizadores al texto del TextField
        campos.textProperty().addListener((observable, stringAnterior, stringActual) -> {
            if (stringActual.matches(" *\\d+, *\\d+, *\\d+ *")) {
                final String[] RGB = stringActual.split(",");
                deslizadorR.setValue(Double.parseDouble(RGB[0].trim()));
                deslizadorG.setValue(Double.parseDouble(RGB[1].trim()));
                deslizadorB.setValue(Double.parseDouble(RGB[2].trim()));
            }
        });

        //Activar y desactivar (según se enfoque el TextField) la reacción del texto del TextField a los valores de los
        // deslizadores (es necesario desactivarlo pues al estar activo el enlace, no se puede modificar el TextField)
        campos.focusedProperty().addListener((observable, estabaEnfocado, estaEnfocado) -> {
            if (estaEnfocado)
                campos.textProperty().unbind();
            else
                campos.textProperty().bind(deslizadorR.valueProperty().asString("%.0f").
                        concat(deslizadorG.valueProperty().asString(", %.0f")).
                        concat(deslizadorB.valueProperty().asString(", %.0f")));
        });

        //Fijar deslizadores cuando checkbox este seleccionado
        var enlazarR = enlazarColores(deslizadorR, deslizadorG, deslizadorB);
        var enlazarG = enlazarColores(deslizadorG, deslizadorB, deslizadorR);
        var enlazarB = enlazarColores(deslizadorB, deslizadorR, deslizadorG);
        fijar.selectedProperty().addListener((observable, estabaSeleccionado, estaSeleccionado) -> {
            if (estaSeleccionado) {
                deslizadorR.valueChangingProperty().addListener(enlazarR);
                deslizadorG.valueChangingProperty().addListener(enlazarG);
                deslizadorB.valueChangingProperty().addListener(enlazarB);
            } else {
                deslizadorR.valueChangingProperty().removeListener(enlazarR);
                deslizadorG.valueChangingProperty().removeListener(enlazarG);
                deslizadorB.valueChangingProperty().removeListener(enlazarB);
            }
        });
    }

    private ChangeListener<? super Boolean> arreglarSiEranInvisibles() {
        return (observable, estabaCambiando, estaCambiando) -> {
            if (estabaCambiando) {
                if (!deslizadorR.isVisible()) {
                    deslizadorR.valueProperty().unbind();
                    deslizadorR.setValue(deslizadorR.getValue() < 0 ? 0 : 255);
                    deslizadorR.setVisible(true);
                    deslizadorAuxR.setVisible(false);
                }
                if (!deslizadorG.isVisible()) {
                    deslizadorG.valueProperty().unbind();
                    deslizadorG.setValue(deslizadorG.getValue() < 0 ? 0 : 255);
                    deslizadorG.setVisible(true);
                    deslizadorAuxG.setVisible(false);
                }
                if (!deslizadorB.isVisible()) {
                    deslizadorB.valueProperty().unbind();
                    deslizadorB.setValue(deslizadorB.getValue() < 0 ? 0 : 255);
                    deslizadorB.setVisible(true);
                    deslizadorAuxB.setVisible(false);
                }
            }
        };
    }

    private void invisibleSiFueraDeRango(boolean menorQueCero, boolean mayorQue255, Slider principal, Slider aux) {
        if (menorQueCero) {
            aux.setValue(0);
            aux.setVisible(true);
            principal.setVisible(false);
        } else if (mayorQue255) {
            aux.setValue(255);
            aux.setVisible(true);
            principal.setVisible(false);
        } else {
            aux.setVisible(false);
            principal.setVisible(true);
        }
        vista.aplicarRGB();
    }

    private ChangeListener<? super Boolean> enlazarColores(Slider principal, Slider... secundarios) {
        return (observable, cambioAntes, cambioAhora) -> {
            if (cambioAhora) {
                secundarios[0].valueProperty().bind(
                        principal.valueProperty().add(
                                secundarios[0].valueProperty().get() - principal.valueProperty().get()));
                secundarios[1].valueProperty().bind(
                        principal.valueProperty().add(
                                secundarios[1].valueProperty().get() - principal.valueProperty().get()));
            }
            if (cambioAntes) {
                principal.valueProperty().unbind();
                secundarios[0].valueProperty().unbind();
                secundarios[1].valueProperty().unbind();
            }
        };
    }
}