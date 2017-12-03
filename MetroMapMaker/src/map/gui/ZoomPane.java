package map.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *  Some sort of Pane such that zooming things is easier
 * @author Ben Michalowicz
 */
public class ZoomPane extends Pane {

    private final DoubleProperty scaler = new SimpleDoubleProperty(1.0);

    public ZoomPane() {
        super();

        scaleXProperty().bind(scaler);
        scaleYProperty().bind(scaler);

    }

    public ZoomPane(Node... elements) {
        super(elements);

        scaleXProperty().bind(scaler);
        scaleYProperty().bind(scaler);
    }

    public double getScaler() {
        return scaler.get();
    }

    public void setScaler(double value) {
        scaler.set(value);
    }

}
