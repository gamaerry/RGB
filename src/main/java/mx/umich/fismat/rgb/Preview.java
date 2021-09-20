package mx.umich.fismat.rgb;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;

public class Preview extends Pane {
    private final DoubleProperty r=new SimpleDoubleProperty(0);
    private final DoubleProperty g=new SimpleDoubleProperty(0);
    private final DoubleProperty b=new SimpleDoubleProperty(0);

    public double getR() {
        return r.get();
    }

    public DoubleProperty rProperty() {
        return r;
    }

    public void setR(double r) {
        this.r.set(r);
    }

    public double getG() {
        return g.get();
    }

    public DoubleProperty gProperty() {
        return g;
    }

    public void setG(double g) {
        this.g.set(g);
    }

    public double getB() {
        return b.get();
    }

    public DoubleProperty bProperty() {
        return b;
    }

    public void setB(double b) {
        this.b.set(b);
    }
    public void aplicarRGB(){
        setStyle(String.format("-fx-background-color: rgb(%f,%f,%f)",getR(),getG(),getB()));
    }
}
