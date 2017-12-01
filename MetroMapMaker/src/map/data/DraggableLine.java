package map.data;

import djf.AppTemplate;
import java.util.ArrayList;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableLine extends Path implements Draggable {

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
        setStrokeWidth(3);

        this.startName = new DraggableText(app, this.name + "  ");
        this.endName = new DraggableText(app, "   " + this.name);

    }

    LineTo line;

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

    public void addStation(DraggableStation s) {

        stations.add(s.getName());

        if (getElements().size() <= 4) {
            getElements().add(1, s.getLineStat());
        } else {
            getElements().add(getElements().size() - 2, s.getLineStat());
        }

        s.getLineStat().setX(s.centerXProperty().get());
        s.getLineStat().setY(s.centerYProperty().get());

    }

    public void removeStation(DraggableStation s) {
        //String ret = stations.get(stations.indexOf(s.getName()));

//        line= ((LineTo)((getElements().get((getElements().indexOf(new LineTo(s.centerXProperty().get(), s.centerYProperty().get())))))));
        s.getLineStat().xProperty().unbind();
        s.getLineStat().yProperty().unbind();

        getElements().remove(s.getLineStat());

        stations.remove(s.getName());

    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_LINE;
    }

    private void initText() {

        ((MoveTo) getElements().get(0)).xProperty().bind(this.startName.xProperty());
        (((MoveTo) getElements().get(0)).yProperty()).bind(this.startName.yProperty());

        (((LineTo) getElements().get(getElements().size() - 1)).xProperty()).bind(this.endName.xProperty());
        (((LineTo) getElements().get(getElements().size() - 1)).yProperty()).bind(this.endName.yProperty());

        this.startName.setOnMouseDragged(e -> {
            this.startName.drag((int) e.getX() - 15, (int) e.getY());

//            getPoints().set(0, e.getX());
//            getPoints().set(1, e.getY());
        });

        this.endName.setOnMouseDragged(e -> {
            this.endName.drag((int) e.getX() + 10, (int) e.getY());

        });

        this.endName.setOnMouseReleased(e -> {

        });
        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().addAll(startName, endName);

    }

    @Override
    public void start(int x, int y) {

        startX = x;
        startY = y;
        endX = x + 30;
        endY = y;
        
        MoveTo start = new MoveTo(startX, startY);
        
        startName.setX(startX);
        startName.setY(startY);
        
        endName.setX(endX);
        endName.setY(endY);
        
        LineTo end = new LineTo(endX, endY);

        getElements().add(start);

        getElements().add(end);

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
