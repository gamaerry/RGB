package rgb;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;

public class Vista extends Pane {
    private final IntegerProperty
            R = new SimpleIntegerProperty(0),
            G = new SimpleIntegerProperty(0),
            B = new SimpleIntegerProperty(0);

    public IntegerProperty propertyR() {
        return R;
    }

    public IntegerProperty propertyG() {
        return G;
    }

    public IntegerProperty propertyB() {
        return B;
    }

    public int getR() {
        return R.get();
    }

    public int getG() {
        return G.get();
    }

    public int getB() {
        return B.get();
    }

    public void aplicarRGB() {
        setStyle(String.format("-fx-background-color: rgb(%d,%d,%d)", getR(), getG(), getB()));
    }
}
