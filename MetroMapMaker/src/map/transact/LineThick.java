package map.transact;

import djf.AppTemplate;
import javafx.scene.shape.Shape;
import jtps.jTPS_Transaction;
import map.data.DraggableLine;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class LineThick implements jTPS_Transaction{
    
    AppTemplate app;
    Shape dl;
    int initThickness, curThickness;
    
    public LineThick(AppTemplate app, Shape dl, int initThickness, int curThickness){
        this.app = app;
        this.dl=dl;
        this.initThickness=initThickness;
        this.curThickness = curThickness;
        
        ((mapWorkspace)app.getWorkspaceComponent()).getRedo().setDisable(true);
    }

    @Override
    public void doTransaction() {
        
        dl.setStrokeWidth(curThickness);
        
    }

    @Override
    public void undoTransaction() {
         dl.setStrokeWidth(initThickness);
    }
    
}
