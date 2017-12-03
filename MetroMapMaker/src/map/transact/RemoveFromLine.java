package map.transact;

import djf.AppTemplate;
import jtps.jTPS_Transaction;
import map.data.DraggableLine;
import map.data.DraggableStation;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class RemoveFromLine implements jTPS_Transaction {
    
    AppTemplate app;
    DraggableLine dl;
    DraggableStation ds;
    int x, y; //For use in the addStatToLine method in mapData
    
    public RemoveFromLine(AppTemplate app, DraggableLine dl, DraggableStation ds, int x, int y){
        this.app = app;
        this.dl = dl;
        this.ds = ds;
        this.x = x;
        this.y= y;
        
        ((mapWorkspace) app.getWorkspaceComponent()).getRedo().setDisable(true);
    }
           

    @Override
    public void doTransaction() {
        
        dl.removeStation(ds);
        
        
        
    }

    @Override
    public void undoTransaction() {
        ds.start(x, y);
        dl.addStation(ds);
        
    }
    
}
