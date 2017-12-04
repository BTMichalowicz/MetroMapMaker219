package map.transact;

import djf.AppTemplate;
import jtps.jTPS_Transaction;
import map.gui.ZoomPane;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class ZoomOut implements jTPS_Transaction {

 AppTemplate app;
    ZoomPane canvas;
    
    double prevScale, curScale;


    public ZoomOut(AppTemplate app, ZoomPane canvas, double prevScale, double curScale){
        //TODO: Re-implement this once your algorithm's been figured out for pane usage. 
        this.app = app;
        this.canvas = canvas;
        this.prevScale = prevScale;
        this.curScale = curScale;
    }

    @Override
    public void doTransaction() {
        canvas.setScaler(curScale);
        ((mapWorkspace) app.getWorkspaceComponent()).getMainSpot().setScaleX(curScale);
        ((mapWorkspace) app.getWorkspaceComponent()).getMainSpot().setScaleY(curScale);
    }

    @Override
    public void undoTransaction() {
        canvas.setScaler(prevScale);

        ((mapWorkspace) app.getWorkspaceComponent()).getMainSpot().setScaleX(prevScale);
        ((mapWorkspace) app.getWorkspaceComponent()).getMainSpot().setScaleY(prevScale);
    }



}
