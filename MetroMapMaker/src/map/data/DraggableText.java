/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import djf.AppTemplate;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;
import map.transact.DragStuff;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableText extends Text implements Draggable {

    double startX;
    double startY;
    double width;
    double height;

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    AppTemplate app;

    jTPS transact;
    jTPS_Transaction t;

    public DraggableText(AppTemplate initApp) {

        width = 0;

        height = 0;

        setOpacity(1.0);

        startX = 0.0;
        startY = 0.0;

        setText("Your text here");

        this.app = initApp;
        
        this.setOnMouseDragged(e->{
              transact = ((mapData) app.getDataComponent()).getTransact();
        double diffX = e.getX() - startX;
        double diffY = e.getY() - startY;
        double newX = getX() + diffX;
        double newY = getY() + diffY;

        setX(e.getX());
        setY(e.getY());
        startX = e.getX();
        startY = e.getY();

        t = new DragStuff(app, this, e.getX(), e.getY(), getX(), getY());
        transact.addTransaction(t);
        ((mapWorkspace) app.getWorkspaceComponent()).getUndo().setDisable(false);
        
        
        });

    }

    public DraggableText(AppTemplate initApp, String name) {
        setOpacity(1.0);
        this.app = initApp;
        setText(name + "      ");
    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_TEXT;
    }

    @Override
    public void start(int x, int y) {

        startX = x;
        startY = y;

        setX(x);
        setY(y);

    }

    public void addText() {
        TextInputDialog textBox = new TextInputDialog("");
        textBox.setContentText("");
        textBox.setTitle("");
        textBox.setHeaderText("");
        setWrappingWidth(getWidth() - (getWidth() / 2));

        textBox.setTitle("Add your text!");
        Optional<String> result = textBox.showAndWait();
        if (result.isPresent()) {
            setText(result.get());
        }

    }

    @Override
    public void drag(int x, int y) {
       transact = ((mapData) app.getDataComponent()).getTransact();
        double diffX = x - startX;
        double diffY = y - startY;
        double newX = getX() + diffX;
        double newY = getY() + diffY;

        setX(x);
        setY(y);
        startX = x;
        startY = y;

        t = new DragStuff(app, this, x, y, getX(), getY());
        transact.addTransaction(t);
        ((mapWorkspace) app.getWorkspaceComponent()).getUndo().setDisable(false);

    }

    @Override
    public void size(int x, int y) {

        double w = x - startX;
        double h = y - startY;

        setX(x + (w / (10000)));
        setY(y + (h / (10000)));

    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {

    }

    @Override
    public String getShapeType() {
        return TEXT;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

}
