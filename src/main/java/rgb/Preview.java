package rgb;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;

public class Preview extends Pane {
    private final IntegerProperty
            r = new SimpleIntegerProperty(0),
            g = new SimpleIntegerProperty(0),
            b = new SimpleIntegerProperty(0);

    public int getR() {
        return r.get();
    }

    public IntegerProperty rProperty() {
        return r;
    }

    public int getG() {
        return g.get();
    }

    public IntegerProperty gProperty() {
        return g;
    }

    public int getB() {
        return b.get();
    }

    public IntegerProperty bProperty() {
        return b;
    }

    public void aplicarRGB() {
        setStyle(String.format("-fx-background-color: rgb(%d,%d,%d)", getR(), getG(), getB()));
    }
}
