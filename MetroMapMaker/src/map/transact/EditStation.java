package map.transact;

import djf.AppTemplate;
import javafx.scene.paint.Color;
import jtps.jTPS_Transaction;
import map.data.DraggableStation;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class EditStation implements jTPS_Transaction {

    AppTemplate app;
    DraggableStation st;
    Color prevColor, curColor;

    public EditStation(AppTemplate app, DraggableStation st, Color prevColor, Color curColor) {
        this.app = app;
        this.st = st;
        this.prevColor = prevColor;
        this.curColor = curColor;
        ((mapWorkspace) app.getWorkspaceComponent()).getRedo().setDisable(true);

    }

    @Override
    public void doTransaction() {
        st.setFill(curColor);
    }

    @Override
    public void undoTransaction() {
        st.setFill(prevColor);
    }

}
