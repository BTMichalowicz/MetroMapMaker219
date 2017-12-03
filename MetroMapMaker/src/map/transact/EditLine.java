package map.transact;

import djf.AppTemplate;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import map.data.DraggableLine;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class EditLine implements jTPS_Transaction{
    
    AppTemplate app;
    
    CheckBox cb;
    Color prevColor, curColor;
    String prevName, curName;
    DraggableLine dl;
    
    public EditLine(AppTemplate app, CheckBox cb, Color prevColor, Color curColor, String prevName, String curName, DraggableLine dl){
        this.app = app;
        this.cb = cb;
        this.prevColor = prevColor;
        this.curColor = curColor;
        this.prevName = prevName;
        this.curName = curName;
        this.dl = dl;
    }

    @Override
    public void doTransaction() {
        dl.setName(curName);
        dl.setStroke(curColor);
        dl.setCircular(cb.isSelected());
        dl.setStyle(dl.getStyle() +  "-fx-color: #" + curColor.toString().split("[x]")[1] + ";");
        
        (((mapWorkspace)app.getWorkspaceComponent())).getEditLine().setStyle("-fx-color: #" + curColor.toString().split("[x]")[1] + ";");
        
        (((mapWorkspace)app.getWorkspaceComponent())).getEditLine().setText(curColor.toString());
    }

    @Override
    public void undoTransaction() {
   dl.setName(prevName);
        dl.setStroke(prevColor);
        dl.setCircular(!cb.isSelected());
        dl.setStyle(dl.getStyle() +  "-fx-color: #" + prevColor.toString().split("[x]")[1] + ";");
        
        (((mapWorkspace)app.getWorkspaceComponent())).getEditLine().setStyle("-fx-color: #" + prevColor.toString().split("[x]")[1] + ";");
        
        (((mapWorkspace)app.getWorkspaceComponent())).getEditLine().setText(prevColor.toString());
        
    }
    
}
