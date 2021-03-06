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
import map.data.mapData;
import map.gui.mapWorkspace;

/**
 * For adding and removing nodes from the canvas.
 *
 * @author Ben Michalowicz
 */
public class addNode implements jTPS_Transaction {

    AppTemplate app;
    Node node;
    mapWorkspace work;

    public addNode(AppTemplate app, Node node) {
        this.app = app;
        this.node = node;
        work = (mapWorkspace) app.getWorkspaceComponent();

        work.getRedo().setDisable(true);

    }

    @Override
    public void doTransaction() {
        work.getCanvas().getChildren().add(node);

        switch (((Draggable) node).getShapeType()) {
            case LINE:
                DraggableLine l = (DraggableLine) node;

                ((mapData) app.getDataComponent()).getLines().add(l);

                if (work.getCanvas().getChildren().contains(l.getStartName()) || work.getCanvas().getChildren().contains(l.getEndName())) {

                } else {

                    work.getCanvas().getChildren().addAll(l.getStartName(), l.getEndName());
                }
                break;

            case IMAGE:
                break;

            case STATION:

                DraggableStation s = (DraggableStation) node;

                if (work.getCanvas().getChildren().contains(s.getStatName())) {

                } else {

                    work.getCanvas().getChildren().addAll(s.getStatName());
                }
                break;

            case TEXT:

                break;
        }

    }

    @Override
    public void undoTransaction() {
        work.getCanvas().getChildren().remove(node);

        switch (((Draggable) node).getShapeType()) {
            case LINE:
                DraggableLine l = (DraggableLine) node;
                ((mapData) app.getDataComponent()).getLines().remove(l);

                work.getCanvas().getChildren().removeAll(l.getStartName(), l.getEndName());
                break;

            case IMAGE:
                break;

            case STATION:

                DraggableStation s = (DraggableStation) node;

                work.getCanvas().getChildren().remove(s.getStatName());
               // work.getStations().getItems().remove(s.getStatName());
                work.getFromStat().getItems().remove(s.getStatName());
                work.getToStat().getItems().remove(s.getStatName());
                break;

            case TEXT:

                break;
        }

        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
    }

}
