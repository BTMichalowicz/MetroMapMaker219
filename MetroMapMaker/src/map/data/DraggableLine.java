
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
public class DraggableLine extends Polyline  implements Draggable{

    AppTemplate app;

    double startX, endX, startY, endY;

    ArrayList<DraggableStation> stations;
    
    DraggableText startName, endName;
    
  

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
        
        this.startName = new DraggableText(app, this.name);
        this.endName = new DraggableText(app, this.name);
        
       
        
       

    }

    public ArrayList<DraggableStation> getStations() {
        return stations;
    }

    public void setStations(ArrayList<DraggableStation> stations) {
        this.stations = stations;
    }

    public DraggableLine(AppTemplate app) {
        this.app = app;
        setOpacity(1.2);
        stations = new ArrayList<>();

       
    }

    public void addStation(DraggableStation s) {
        stations.add(s);
        
        s.setCenterX(getPoints().get(0));
        s.setCenterY(getPoints().get(1));
        
       
        
        s.setOnMouseDragged(e -> {
            s.drag((int)e.getX(), (int)e.getY());
            
            getPoints().set(0, e.getX());
            getPoints().set(1, e.getY());
            
            startName.setX(getPoints().get(0)-25);
            startName.setY(getPoints().get(1)-(e.getY()/100));
        });
        
               
      
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
        endX = x + 90;
        endY = y;

        getPoints().addAll(startX, startY, endX, endY);
        
        
        
        
        this.startName.setX(getPoints().get(0)-25);
        this.startName.setY(getPoints().get(1));
        this.endName.setX(getPoints().get(getPoints().size()-2)+5);
        this.endName.setY(getPoints().get(getPoints().size()-1));
        
        ((mapWorkspace)app.getWorkspaceComponent()).getCanvas().getChildren().addAll(startName, endName);
        
        

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
