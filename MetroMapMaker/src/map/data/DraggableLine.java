package map.data;

import djf.AppTemplate;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
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

        this.startName.setOnMouseDragged(e -> {
            this.startName.drag((int) e.getX()-15, (int) e.getY());

            getPoints().set(0, e.getX());
            getPoints().set(1, e.getY());
            
             double x = ((getPoints().get(0) + getPoints().get(getPoints().size() - 2))) / 2;
        double y = ((getPoints().get(1) + getPoints().get(getPoints().size() - 1))) / 2;
        
        getPoints().set(0, e.getX());
        getPoints().set(1, e.getY());
        
         getPoints().set(2, x);
        getPoints().set(3, y);
        
        
       

        });
        this.endName.setOnMouseDragged(e -> {
            this.endName.drag((int) e.getX(), (int) e.getY());

            getPoints().set(getPoints().size() - 2, e.getX() + 4);
            getPoints().set(getPoints().size() - 1, e.getY());
            
             double x = ((getPoints().get(0) + getPoints().get(getPoints().size() - 2))) / 2;
        double y = ((getPoints().get(1) + getPoints().get(getPoints().size() - 1))) / 2;
        
        getPoints().set(4, e.getX());
        getPoints().set(5, e.getY());
        
         getPoints().set(2, x);
        getPoints().set(3, y);
        });

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
        stations.add(statName);

        s.setCenterX(getPoints().get(0) - 25);
        s.setCenterY(getPoints().get(1));

//        for(int i = 2; i< getPoints().size(); i++){
//            
//            getPoints().set(i, getPoints().get(i-1));
//            getPoints().set(i-1, getPoints().get(i-2));
//            
//            
//        }
//       
//        
        double x = ((getPoints().get(0) + getPoints().get(getPoints().size() - 2))) / 2;
        double y = ((getPoints().get(1) + getPoints().get(getPoints().size() - 1))) / 2;

        DoubleProperty x1 = new SimpleDoubleProperty(x);
        DoubleProperty y1 = new SimpleDoubleProperty(y);
        
        

        s.setCenterX(x);
        s.setCenterY(y);
         getPoints().set(4, getPoints().get(2));
        getPoints().set(5, getPoints().get(3));

        getPoints().set(2, x);
        getPoints().set(3, y);

//        this.startName.setOnMouseDragged(e -> {
//            this.startName.drag((int) e.getX(), (int) e.getY());
//
//  
//
//            getPoints().set(0, e.getX());
//            getPoints().set(1, e.getY());
//
//        });
//        this.endName.setOnMouseDragged(e -> {
//            this.endName.drag((int) e.getX(), (int) e.getY());
//
//            getPoints().set(getPoints().size() - 2, e.getX() + 4);
//            getPoints().set(getPoints().size() - 1, e.getY());
//
//        });
//        s.setOnMouseDragged(e -> {
//            s.drag((int)e.getX(), (int)e.getY());
//            
//            getPoints().add(0, e.getX());
//            getPoints().set(1, e.getY());
//            
//            startName.setX(getPoints().get(0)-25);
//            startName.setY(getPoints().get(1)-(e.getY()/100));
//        });
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

    @Override
    public void start(int x, int y) {

        startX = x;
        startY = y;
        endX = x + 90;
        endY = y;

        
        getPoints().addAll(startX, startY, endX, endY);
         double x2 = ((getPoints().get(0) + getPoints().get(getPoints().size() - 2))) / 2;
        double y2 = ((getPoints().get(1) + getPoints().get(getPoints().size() - 1))) / 2;
        
        getPoints().add(2, x2);
        getPoints().add(3, y2);
        

        this.startName.setX(getPoints().get(0) - 25);
        this.startName.setY(getPoints().get(1));
        this.endName.setX(getPoints().get(getPoints().size() - 2) + 5);
        this.endName.setY(getPoints().get(getPoints().size() - 1));

        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().addAll(startName, endName);

    }

    public DraggableText getStartName() {
        return startName;
    }

    public DraggableText getEndName() {
        return endName;
    }

    @Override
    public void drag(int x, int y) {

//        //For dragging the line end
//        if ((x == getPoints().get(0) && y == getPoints().get(1))) {
//            double diffX = x + startX;
//
//            double diffY = y + startY;
//            double newX = getX() - diffX;
//            double newY = getY() -diffY;
//
//            getPoints().addAll(newX, newY);
//            startX = x;
//            startY = y;
//        } else if ((x == getPoints().get(getPoints().size() - 2) && y == getPoints().get(getPoints().size() - 1))) {
//            double diffX = x + startX;
//
//            double diffY = y + startY;
//            double newX = getX() + diffX;
//            double newY = getY() + diffY;
//
//            getPoints().add(getPoints().size() - 2, newX);
//            getPoints().add(getPoints().size() - 1, newY);
//            startX = x;
//            startY = y;
//        }else{
//            
//        }
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
