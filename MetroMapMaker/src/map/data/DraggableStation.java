package map.data;

import djf.AppTemplate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Circle;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;
import map.transact.DragStuff;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableStation extends Circle implements Draggable, Comparable<DraggableStation> {

    double startCenterX;
    double startCenterY;

    AppTemplate app;

    String name;

    DraggableText statName;

    StationTo lineStat;
    
    double prevX, prevY, curX, curY;

    public StationTo getLineStat() {
        return lineStat;
    }

    public void setLineStat(StationTo lineStat) {
        this.lineStat = lineStat;
    }

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
        setRadius(5);
        setOpacity(1.0);
        startCenterX = 0.0;
        startCenterY = 0.0;
        this.app = initApp;
        this.name = name;
        this.statName = new DraggableText(initApp, name);
        prevX = prevY = curX = curY = 0;

        lineStat = new StationTo(this);

        //statName.yProperty().set(statName.yProperty().get() - 20);
        this.setOnMouseDragged(e -> {
            
            transact = ((mapData) app.getDataComponent()).getTransact();
            setX(e.getX());
            setY(e.getY());
            
            startCenterX = e.getX();
            startCenterY = e.getY();

            this.statName.xProperty().bind(new SimpleDoubleProperty(centerXProperty().get() + radiusProperty().get()));
            this.statName.yProperty().bind(new SimpleDoubleProperty(centerYProperty().get() + radiusProperty().get() - 20));

            t = new DragStuff(app, this, e.getX(), e.getY(), getX(), getY());
            transact.addTransaction(t);

        });
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

        this.statName.xProperty().bind(new SimpleDoubleProperty(centerXProperty().get() + radiusProperty().get()));
        this.statName.yProperty().bind(new SimpleDoubleProperty(centerYProperty().get() + radiusProperty().get() - 20));

        if (!((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().contains(statName)) {
            ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().add(statName);
        }

        lineStat.setX(x);
        lineStat.setY(y);

    }

    jTPS transact;
    jTPS_Transaction t;

    @Override
    public void drag(int x, int y) {

    }

    @Override
    public void size(int x, int y) {

        setX(x);
        setY(y);

    }

    @Override
    public double getX() {
        return getCenterX();
    }

    public void setX(double value) {

        setCenterX(value);

        lineStat.setX(value);

    }

    public void setY(double value) {
        setCenterY(value);

        lineStat.setY(value);
    }

    @Override
    public double getY() {
        return getCenterY() ;
    }

    @Override
    public double getWidth() {
        return getRadius() * 2;
    }

    @Override
    public double getHeight() {
        return getRadius() * 2;
    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
        setCenterX(initX + (initWidth / 2));
        setCenterY(initY + (initHeight / 2));
        lineStat.setX(initX);
        lineStat.setY(initY);
        setRadius(5);

    }

    @Override
    public String getShapeType() {
        return STATION;
    }

    @Override
    public int compareTo(DraggableStation t) {
        if(this.getX()>t.getX() && this.getY()>t.getY()){
            return 1;
        }else if(this.getX()<t.getX() && this.getY()<t.getY()){
            return-1;
        }else
            return 0;
    }
    
    
    @Override
    public boolean equals(Object o){
        if (this==o) return true;
        if(!(o instanceof DraggableStation)) return false;
        
        DraggableStation that = (DraggableStation) o;
        
        return this.getName().equals(that.getName()) && this.getX() == that.getX() && this.getY() == that.getY();
    }

}
