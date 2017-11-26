package map.data;

import djf.AppTemplate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Ellipse;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableStation extends Ellipse implements Draggable {

    double startCenterX;
    double startCenterY;

    AppTemplate app;

    String name;

    DraggableText statName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.statName.setText(name);
    }

    public DraggableStation(AppTemplate initApp, String name) {
        setCenterX(0.0);
        setCenterY(0.0);
        setRadiusX(5);
        setRadiusY(5);
        setOpacity(1.0);
        startCenterX = 0.0;
        startCenterY = 0.0;
        this.app = initApp;
        this.name = name;
        this.statName = new DraggableText(initApp, name);

        //statName.yProperty().set(statName.yProperty().get() - 20);
    }

    public DraggableText getStatName() {
        return statName;
    }

    public void removeClause() {

        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().remove(this.statName);

    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_STATION;
    }

    @Override
    public void start(int x, int y) {
        startCenterX = x;
        startCenterY = y;
        setCenterX(startCenterX);
        setCenterY(startCenterY);

        this.statName.xProperty().bind(new SimpleDoubleProperty(centerXProperty().get() + radiusXProperty().get()));
        this.statName.yProperty().bind(new SimpleDoubleProperty(centerYProperty().get() + radiusYProperty().get() - 20));

        if (!((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().contains(statName)) {
            ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().add(statName);
        }

    }

    jTPS transact;
    jTPS_Transaction t;

    @Override
    public void drag(int x, int y) {

        setCenterX(x);
        setCenterY(y);
        centerXProperty().set(x);
        centerYProperty().set(y);
        startCenterX = x;
        startCenterY = y;

        this.statName.xProperty().bind(new SimpleDoubleProperty(centerXProperty().get() + radiusXProperty().get()));
        this.statName.yProperty().bind(new SimpleDoubleProperty(centerYProperty().get() + radiusYProperty().get() - 20));

    }

    @Override
    public void size(int x, int y) {
        double width = x - startCenterX;
        double height = y - startCenterY;
        double centerX = startCenterX + (width / 2);
        double centerY = startCenterY + (height / 2);
        setCenterX(x);
        setCenterY(y);

    }

    @Override
    public double getX() {
        return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
        return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
        return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
        return getRadiusY() * 2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        setCenterX(initX + (initWidth / 2));
        setCenterY(initY + (initHeight / 2));
        setRadiusX(5);
        setRadiusY(5); // 10 Pixel diameter should work

    }

    @Override
    public String getShapeType() {
        return STATION;
    }

}
