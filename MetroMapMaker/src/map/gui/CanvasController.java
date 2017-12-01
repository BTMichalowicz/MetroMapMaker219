package map.gui;

import djf.AppTemplate;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import map.data.Draggable;
import map.data.DraggableLine;
import map.data.mapData;
import map.data.mapState;

/**
 * This class is made for interactions with the canvas/rendering surface.
 *
 * While the canvas is actually a Pane inside a ScrollPane, we can make this
 * stuff work.
 *
 * @author Ben Michalowicz
 */
public class CanvasController {

    AppTemplate app;
    mapData dataManager;

    public CanvasController(AppTemplate initApp) {
        app = initApp;
    }

    public void processCanvasMousPressed(int x, int y) {

        dataManager = (mapData) app.getDataComponent();

        if (dataManager.isInState(mapState.SELECTING)) {
            Node node = dataManager.selectTopShape(x, y);
            Scene scene = app.getGUI().getPrimaryScene();

            //We're off to the Drag... Races
            if (node != null) {
                scene.setCursor(Cursor.MOVE);
                dataManager.setState(mapState.DRAGGING);
                app.getGUI().updateToolbarControls(false);
            } else {
                scene.setCursor(Cursor.DEFAULT);
                dataManager.setState(mapState.DRAGGING_NOTHING);
                app.getWorkspaceComponent().reloadWorkspace(dataManager);
            }
        } else if (dataManager.isInState(mapState.STARTING_BCKGROUND)) {
            dataManager.startNewBackground();
        } else if (dataManager.isInState(mapState.STARTING_STATION)) {
            dataManager.startNewStation(x, y);
        } else if (dataManager.isInState(mapState.STARTING_LINE)) {
            dataManager.startNewMetroLine(x, y);
        } else if (dataManager.isInState(mapState.STARTING_OVERLAY)) {
            dataManager.startNewImage(x, y);
        } else if (dataManager.isInState(mapState.STARTING_TEXT)) {
            dataManager.startNewText(x, y);
        } else if (dataManager.isInState(mapState.ROTATING_LABEL)) {
            dataManager.rotateLabel();

        } else if (dataManager.isInState(mapState.ADD_STAT_TO_LINE)) {

            if (dataManager.getSelectedShape() != null && dataManager.getSelectedShape() instanceof DraggableLine) {
                dataManager.addStatToLine(x, y, (DraggableLine) (dataManager.getSelectedShape()));
            }
        }

        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);

        app.getWorkspaceComponent().reloadWorkspace(dataManager);
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);

    }

    /**
     * The list of actions that will take place if a mouse is dragged across the
     * pane (*Cough* CANVAS *Cough*).
     *
     *
     * @param x
     * @param y
     */
    public void processCanvasMouseDragged(int x, int y) {
        dataManager = (mapData) app.getDataComponent();

        if (dataManager.isInState(mapState.SIZING_ITEM)) {
            Draggable draggableShape = (Draggable) dataManager.getNewShape();
            draggableShape.size(x, y);

            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(mapState.DRAGGING)) {
            Draggable selectedNode = (Draggable) dataManager.getSelectedShape();
            selectedNode.drag(x, y);
            app.getGUI().updateToolbarControls(false);
        }

    }

    public void processCanvasMouseRelease(int x, int y) {
        dataManager = (mapData) app.getDataComponent();

        if (dataManager.isInState(mapState.SIZING_ITEM)) {
            dataManager.selectSizedShape();
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(mapState.DRAGGING)) {
            dataManager.setState(mapState.SELECTING);
            Scene scene = app.getGUI().getPrimaryScene();
            scene.setCursor(Cursor.DEFAULT);
            app.getGUI().updateToolbarControls(false);
        } else if (dataManager.isInState(mapState.DRAGGING_NOTHING)) {
            dataManager.setState(mapState.SELECTING);

        }
    }

    //Two private data fields used for zooming in and out
    private final double MIN_SCALE = 0.1d;
    private final double MAX_SCALE = 10.0d;

    private final double delta = 1.1; //increase by 10% zoom

    void zoomIn() {
               mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();


        double scale1 = workspace.getCanvas().getScaler();
       

        scale1 *= delta;
    

        scale1 = clamp(scale1, MIN_SCALE, MAX_SCALE);
        

        workspace.getCanvas().setScaler(scale1);

    }

    private double clamp(double value, double min, double max) {

        if (Double.compare(value, min) < 0) {
            return min;
        }

        if (Double.compare(value, max) > 0) {
            return max;
        }

        return value;
    }

    void zoomOut() {
        
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();


        double scale1 = workspace.getCanvas().getScaler();
       

        scale1 /= delta;
    

        scale1 = clamp(scale1, MIN_SCALE, MAX_SCALE);
        

        workspace.getCanvas().setScaler(scale1);
         

    }

}
