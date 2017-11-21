/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import djf.AppTemplate;
import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableLine extends Polyline implements Draggable {

    AppTemplate app;

    double startX, endX, startY, endY;

    ArrayList<DraggableStation> stations;

    jTPS transactl;
    jTPS_Transaction t;
    String name;

    public String getName() {
        return name;
    }

    public DraggableLine(AppTemplate app, String name) {
        this.app = app;

        setOpacity(1.2);
        stations = new ArrayList<>();

        this.name = name;

    }

    public ArrayList<DraggableStation> getStations() {
        return stations;
    }

    public void setStations(ArrayList<DraggableStation> stations) {
        this.stations = stations;
    }

    public DraggableLine(AppTemplate app, double... points) {
        this.app = app;
        setOpacity(1.2);
        stations = new ArrayList<>();

        for (double d : points) {
            getPoints().addAll(d);
        }
    }

    public void addStation(DraggableStation s) {
        stations.add(s);

        getPoints().add((int) s.getCenterX(), s.getCenterY());
    }

    public DraggableStation removeStation(DraggableStation s) {
        DraggableStation ret = stations.get(stations.indexOf(s));

        stations.remove(s);
        return ret;
    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_LINE;
    }

    @Override
    public void start(int x, int y) {
        startX = x;
        startY = y;
        endX = x + 30;
        endY = y + 30;

        getPoints().addAll(startX, startY, endX, endY);

    }

    @Override
    public void drag(int x, int y) {

        if ((x == getPoints().get(0) && y == getPoints().get(1))) {
            double diffX = x - startX;

            double diffY = y - startY;
            double newX = getX() + diffX;
            double newY = getY() + diffY;

            getPoints().set(0, newX);
            getPoints().set(1, newY);
            startX = x;
            startY = y;
        } else if ((x == getPoints().get(getPoints().size() - 2) && y == getPoints().get(getPoints().size() - 1))) {
            double diffX = x - startX;

            double diffY = y - startY;
            double newX = getX() + diffX;
            double newY = getY() + diffY;

            getPoints().set(getPoints().size() - 2, newX);
            getPoints().set(getPoints().size() - 1, newY);
            startX = x;
            startY = y;
        }

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
        getPoints().addAll(initX, initY);

    }

    @Override
    public String getShapeType() {
        return LINE;
    }

}
