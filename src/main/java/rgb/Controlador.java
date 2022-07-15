package rgb;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controlador {
    private final ArrayList<Slider> deslizadores = new ArrayList<>(), deslizadoresAux = new ArrayList<>();
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

        //A lo largo del codigo los indices 0, 1, 2 representan R, G, B respectivamente
        deslizadores.add(deslizadorR);
        deslizadores.add(deslizadorG);
        deslizadores.add(deslizadorB);

        //Hacer invisible a los deslizadores auxiliares
        deslizadoresAux.add(deslizadorAuxR);
        deslizadoresAux.add(deslizadorAuxG);
        deslizadoresAux.add(deslizadorAuxB);
        deslizadorAuxR.setVisible(false);
        deslizadorAuxG.setVisible(false);
        deslizadorAuxB.setVisible(false);

        //Hacer reaccionar los valores r, g, b del preview a los valores de los Sliders
        vista.propertyR().bind(deslizadorR.valueProperty());
        vista.propertyG().bind(deslizadorG.valueProperty());
        vista.propertyB().bind(deslizadorB.valueProperty());

        //Aplicar los cambios cada que se modifiquen los valores de los Sliders
        // y hacer invisibles si son menores que cero o mayores que 255
        deslizadorR.valueProperty().addListener(invisibleSiFueraDeRango(0));
        deslizadorG.valueProperty().addListener(invisibleSiFueraDeRango(1));
        deslizadorB.valueProperty().addListener(invisibleSiFueraDeRango(2));

        //Cuando termine de estar "cambiandose" el valor del deslizador se cambiará
        // a 0 o 255 segun corresponda
        deslizadorR.valueChangingProperty().addListener(arreglarSiEranInvisibles(1, 2));
        deslizadorG.valueChangingProperty().addListener(arreglarSiEranInvisibles(0, 2));
        deslizadorB.valueChangingProperty().addListener(arreglarSiEranInvisibles(0, 1));

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
        var enlazarR = enlazarColores(1, 2);
        var enlazarG = enlazarColores(0, 2);
        var enlazarB = enlazarColores(0, 1);
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

    private ChangeListener<Number> invisibleSiFueraDeRango(int i) {
        return (observable, valorAnterior, valorActual) -> {
            if (fijar.isSelected()) {
                var principal = deslizadores.get(i);
                var aux = deslizadoresAux.get(i);
                if (valorActual.doubleValue() < 0) {
                    aux.setValue(0);
                    aux.setVisible(true);
                    principal.setVisible(false);
                } else if (valorActual.doubleValue() > 255) {
                    aux.setValue(255);
                    aux.setVisible(true);
                    principal.setVisible(false);
                } else {
                    aux.setVisible(false);
                    principal.setVisible(true);
                }
            }
            vista.aplicarRGB();
        };
    }

    private ChangeListener<Boolean> arreglarSiEranInvisibles(int i1, int i2) {
        return (observable, estabaCambiando, estaCambiando) -> {
            if (estabaCambiando && fijar.isSelected()) {
                var deslizador1 = deslizadores.get(i1);
                var deslizador2 = deslizadores.get(i2);
                if (!deslizador1.isVisible()) {
                    deslizador1.valueProperty().unbind();
                    deslizador1.setValue(deslizador1.getValue() < 0 ? 0 : 255);
                    deslizador1.setVisible(true);
                    deslizadoresAux.get(i1).setVisible(false);
                }
                if (!deslizador2.isVisible()) {
                    deslizador2.valueProperty().unbind();
                    deslizador2.setValue(deslizador2.getValue() < 0 ? 0 : 255);
                    deslizador2.setVisible(true);
                    deslizadoresAux.get(i2).setVisible(false);
                }
            }
        };
    }

    private ChangeListener<Boolean> enlazarColores(int i1, int i2) {
        var principal = deslizadores.get(2 * (i1 + i2) % 3);
        return (observable, cambioAntes, cambioAhora) -> {
            if (cambioAhora) {
                deslizadores.get(i1).valueProperty().bind(
                        principal.valueProperty().add(
                                deslizadores.get(i1).valueProperty().get() - principal.valueProperty().get()));
                deslizadores.get(i2).valueProperty().bind(
                        principal.valueProperty().add(
                                deslizadores.get(i2).valueProperty().get() - principal.valueProperty().get()));
            }
            if (cambioAntes) {
                principal.valueProperty().unbind();
                deslizadores.get(i1).valueProperty().unbind();
                deslizadores.get(i2).valueProperty().unbind();
            }
        };
    }
}