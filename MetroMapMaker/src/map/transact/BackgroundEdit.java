package map.transact;

import djf.AppTemplate;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class BackgroundEdit implements jTPS_Transaction{
    
        
    AppTemplate app;
    Background prevBack, curBack;
    Pane canvas;
    
    public  BackgroundEdit(AppTemplate app, Background prevBack, Background curBack, Pane canvas){
        this.app = app;
        this.prevBack = prevBack;
        this.curBack = curBack;
        this.canvas = canvas;
        ((mapWorkspace) app.getWorkspaceComponent()).getRedo().setDisable(true);
    }

    @Override
    public void doTransaction() {
        canvas.setBackground(curBack);
      
    }

    @Override
    public void undoTransaction() {
        canvas.setBackground(prevBack);
    }
    
    
    
    
    
}
