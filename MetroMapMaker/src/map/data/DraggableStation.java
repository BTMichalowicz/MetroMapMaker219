/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import djf.AppTemplate;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableStation extends Ellipse implements Draggable {

    double startCenterX;
    double startCenterY;

    AppTemplate app;

    String name;

    Text statName;

    public String getName() {
        return name;
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

        statName = new Text(name);
    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_STATION;
    }

    @Override
    public void start(int x, int y) {
        startCenterX = x;
        startCenterY = y;

        statName.setX(x - 9);
        statName.setY(y - 9);
        
        
        DoubleProperty x1 = new SimpleDoubleProperty(startCenterX);
        DoubleProperty y1 = new SimpleDoubleProperty(startCenterY);
        
        x1.bind(statName.xProperty());
         y1.bind(statName.yProperty());
        
    }

    jTPS transact;
    jTPS_Transaction t;

    @Override
    public void drag(int x, int y) {

        double diffX = x - startCenterX;
        double diffY = y - startCenterY;
        double newX = getCenterX() + diffX;
        double newY = getCenterY() + diffY;
        setCenterX(newX);
        setCenterY(newY);
        startCenterX = x;
        startCenterY = y;
        statName.setX(x - 9);
        statName.setY(y - 9);

    }

    @Override
    public void size(int x, int y) {
        double width = x - startCenterX;
        double height = y - startCenterY;
        double centerX = startCenterX + (width / 2);
        double centerY = startCenterY + (height / 2);
        setCenterX(centerX);
        setCenterY(centerY);
        statName.setX(x - 9);
        statName.setY(y - 9);

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
