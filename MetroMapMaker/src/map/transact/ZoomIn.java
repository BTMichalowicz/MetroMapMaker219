package map.transact;

import djf.AppTemplate;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import jtps.jTPS_Transaction;

import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class ZoomIn implements jTPS_Transaction {

    AppTemplate app;
    Pane canvas;

    double prevScale, curScale;

    public ZoomIn(AppTemplate app, Pane canvas, double prevScale, double curScale) {
        //TODO: Re-implement this once your algorithm's been figured out for pane usage. 
        this.app = app;
        this.canvas = canvas;
        this.prevScale = prevScale;
        this.curScale = curScale;
    }

    @Override
    public void doTransaction() {
//        //canvas.setScaler(curScale);
//        canvas.setScaleX(curScale);
//        canvas.setScaleY(curScale);
        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getTransforms().add(new Scale(curScale, curScale));

    }

    @Override
    public void undoTransaction() {
//        //canvas.setScaler(prevScale);
//
//        canvas.setScaleX(prevScale);
//        canvas.setScaleY(prevScale);
        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getTransforms().add(new Scale(prevScale, prevScale));

    }

}
