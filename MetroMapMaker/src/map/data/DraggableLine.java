package map.data;

import djf.AppTemplate;
import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Polyline;

import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableLine extends Polyline implements Draggable {

    AppTemplate app;

    double startX, endX, startY, endY;

    ArrayList<String> stations;

    DraggableText startName, endName;

    jTPS transactl;
    jTPS_Transaction t;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.startName.setText(name);
        this.endName.setText(name);
    }

    public DraggableLine(AppTemplate app, String name) {
        this.app = app;

        setOpacity(1.2);
        stations = new ArrayList<>();

        this.name = name;

        this.startName = new DraggableText(app, this.name + "  ");
        this.endName = new DraggableText(app, "   " + this.name);

        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().addAll(startName, endName);

    }

    public ArrayList<String> getStations() {
        return stations;
    }

    public void setStations(ArrayList<String> stations) {
        this.stations = stations;
    }

    public DraggableLine(AppTemplate app) {
        this.app = app;
        setOpacity(1.2);
        stations = new ArrayList<>();

    }

    public void addStation(DraggableStation s, String statName) {

        stations.add(s.getName());
    }

    public String removeStation(String s) {
        String ret = stations.get(stations.indexOf(s));

        stations.remove(s);
        return ret;
    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_LINE;
    }

    private void initText() {

        this.startName.setX(getPoints().get(0) - 20);
        this.startName.setY(getPoints().get(1));

        this.endName.setX(getPoints().get(getPoints().size() - 2) + 5);
        this.endName.setY(getPoints().get(getPoints().size() - 1));

        this.startName.setOnMouseDragged(e -> {
            this.startName.drag((int) e.getX() - 15, (int) e.getY());
            getPoints().add(0, (e.getX()));
            getPoints().add(1, e.getY());

//            getPoints().set(0, e.getX());
//            getPoints().set(1, e.getY());
        });

        this.startName.setOnMouseReleased(e -> {

        });

        this.endName.setOnMouseDragged(e -> {
            this.endName.drag((int) e.getX() + 10, (int) e.getY());
            getPoints().addAll(e.getX(), e.getY());

        });

        this.endName.setOnMouseReleased(e -> {

        });

    }

    @Override
    public void start(int x, int y) {

        startX = x;
        startY = y;
        endX = x + 30;
        endY = y;

        getPoints().add(0, startX);
        getPoints().add(1, startY);
        getPoints().add(2, endX);
        getPoints().add(3, endY);

        double x2 = (getPoints().get(0) + getPoints().get(getPoints().size() - 2)) / 2;
        double y2 = (getPoints().get(1) + getPoints().get(getPoints().size() - 1)) / 2;

        initText();

    }

    public DraggableText getStartName() {
        return startName;
    }

    public DraggableText getEndName() {
        return endName;
    }

    @Override
    public void drag(int x, int y) {

    }

    @Override
    public void size(int x, int y) {

    }

    @Override
    public double getX() {
        return startX;
    }

    @Override
    public double getY() {
        return startY;
    }

    @Override
    public double getWidth() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        return endX - startX;
    }

    @Override
    public double getHeight() {

        return 5;// 5 Pixels should be good for a height of a metro line. The thickness will assume anything else
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        //  getPoints().addAll(initX, initY);

    }

    @Override
    public String getShapeType() {
        return Draggable.LINE;
    }

}
