package map.transact;

import djf.AppTemplate;
import javafx.scene.Cursor;
import javafx.scene.Node;
import jtps.jTPS_Transaction;
import map.data.Draggable;
import static map.data.Draggable.IMAGE;
import static map.data.Draggable.LINE;
import static map.data.Draggable.STATION;
import static map.data.Draggable.TEXT;
import map.data.DraggableLine;
import map.data.DraggableStation;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class removeNode implements jTPS_Transaction {

    AppTemplate app;
    Node node;
    mapWorkspace work;

    public removeNode(AppTemplate app, Node node) {
        this.app = app;
        this.node = node;
        work = (mapWorkspace) app.getWorkspaceComponent();

        work.getRedo().setDisable(true);

    }

    @Override
    public void doTransaction() {
        work.getCanvas().getChildren().remove(node);

        switch (((Draggable) node).getShapeType()) {
            case LINE:
                DraggableLine l = (DraggableLine) node;

                work.getCanvas().getChildren().remove((l).getStartName());
                work.getCanvas().getChildren().remove((l).getEndName());
                work.getLines().getItems().remove((l).getName());

                ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().stream().filter((item) -> (item instanceof DraggableStation)).map((item) -> (DraggableStation) item).filter((stat) -> (l.getStations().contains(stat.getName()))).forEachOrdered((stat) -> {
                    l.removeStation(stat);
                });

                break;

            case IMAGE:
                break;

            case STATION:

                DraggableStation s = (DraggableStation) node;

                work.getCanvas().getChildren().remove((s).getStatName());
                work.getStations().getItems().remove((s).getName());
                work.getFromStat().getItems().remove(s.getName());
                work.getToStat().getItems().remove(s.getName());
                (work.getCanvas().getChildren().stream().filter((n) -> (n instanceof DraggableLine)).filter((n) -> (((DraggableLine) n).getStations().contains(s.getName())))).forEachOrdered((n) -> {
                    ((DraggableLine) n).removeStation((s));
                });

                work.getCanvas().getChildren().remove(s.getStatName());

                break;

            case TEXT:

                break;
        }

    }

    @Override
    public void undoTransaction() {
        work.getCanvas().getChildren().add(node);

        switch (((Draggable) node).getShapeType()) {
            case LINE:
                DraggableLine l = (DraggableLine) node;

                work.getCanvas().getChildren().addAll(l.getStartName(), l.getEndName());
                work.getLines().getItems().add((l).getName());
                break;

            case IMAGE:
                break;

            case STATION:

                DraggableStation s = (DraggableStation) node;

                work.getCanvas().getChildren().add(s.getStatName());
                work.getStations().getItems().add((s).getName());
                work.getFromStat().getItems().add(s.getName());
                work.getToStat().getItems().add(s.getName());
                break;

            case TEXT:

                break;
        }

        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
    }

}
