package map.transact;

import djf.AppTemplate;
import jtps.jTPS_Transaction;
import map.data.Draggable;
import static map.data.Draggable.IMAGE;
import static map.data.Draggable.STATION;
import static map.data.Draggable.TEXT;
import map.data.DraggableImage;
import map.data.DraggableStation;
import map.data.DraggableText;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class DragStuff implements jTPS_Transaction {

    AppTemplate app;
    Draggable node;

    double prevX, prevY, curX, curY;

    public DragStuff(AppTemplate app, Draggable node, double prevX, double prevY, double curX, double curY) {
        this.app = app;
        this.node = node;
        this.prevX = prevX;
        this.prevY = prevY;
        this.curX = curX;
        this.curY = curY;
        ((mapWorkspace) app.getWorkspaceComponent()).getRedo().setDisable(true);
    }

    @Override
    public void doTransaction() {
        switch (node.getShapeType()) {

            case IMAGE:

                DraggableImage i = (DraggableImage) node;
                i.setX(this.curX);
                i.setY(this.curY);
                break;

            case TEXT:

                DraggableText t = (DraggableText) node;
                t.setX(this.curX);
                t.setY(this.curY);

                break;

            case STATION:
                DraggableStation s = (DraggableStation) node;
                s.setX(this.curY);
                s.setY(this.curY);

                break;

        }
    }

    @Override
    public void undoTransaction() {
        switch (node.getShapeType()) {

            case IMAGE:

                DraggableImage i = (DraggableImage) node;
                i.setX(this.prevX);
                i.setY(this.prevY);
                break;

            case TEXT:

                DraggableText t = (DraggableText) node;
                t.setX(this.prevX);
                t.setY(this.prevY);

                break;

            case STATION:
                DraggableStation s = (DraggableStation) node;
                s.setX(this.prevX);
                s.setY(this.prevY);

                break;

        }
    }

}
