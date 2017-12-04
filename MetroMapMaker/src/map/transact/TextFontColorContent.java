package map.transact;

import djf.AppTemplate;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jtps.jTPS_Transaction;
import map.data.DraggableText;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class TextFontColorContent implements jTPS_Transaction{
    
    AppTemplate app;
    DraggableText t;
    Color prevFill, curFill;
    Font curFont, prevFont;
    
    public TextFontColorContent(AppTemplate app, DraggableText t, Color prevFill, Color curFill, Font curFont, Font prevFont){
        this.app =app;
        this.t = t;
        this.prevFill = prevFill;
        this.curFill = curFill;
        this.curFont = curFont;
        this.prevFont = prevFont;
        ((mapWorkspace) app.getWorkspaceComponent()).getRedo().setDisable(true);
    }

    @Override
    public void doTransaction() {
        t.setFont(curFont);
        t.setFill(curFill);
    }

    @Override
    public void undoTransaction() {
        t.setFont(prevFont);
        t.setFill(prevFill);

    }
    
    
    

}
