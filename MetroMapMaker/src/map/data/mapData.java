/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import djf.components.AppDataComponent;
import djf.AppTemplate;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.gui.mapWorkspace;

/**
 *
 * @author Ben Michalowicz
 */
public class mapData implements AppDataComponent {

    // FIRST THE THINGS THAT HAVE TO BE SAVED TO FILES
    // THESE ARE THE SHAPES TO DRAW
    ObservableList<Node> items;

    public ObservableList<Node> getShapeList() {
        return items;
    }

    public void setList(ObservableList<Node> list) {
        this.items = list;
    }

    // THE BACKGROUND COLOR
    Color backgroundColor;

    // AND NOW THE EDITING DATA
    // THIS IS THE SHAPE CURRENTLY BEING SIZED BUT NOT YET ADDED
    Node newNode;

    // FOR FILL AND OUTLINE
    Color currentFillColor;
    Color currentOutlineColor;
    double currentBorderWidth;

    // CURRENT STATE OF THE APP
    mapState state;

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    AppTemplate app;

    // USE THIS WHEN THE SHAPE IS SELECTED
    Effect highlightedEffect;

    //AppTemplate app;
    Node selectedNode;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 2;

    jTPS transact;

    public mapData(AppTemplate initApp) {
        // KEEP THE APP FOR LATER
        app = initApp;

        // NO SHAPE STARTS OUT AS SELECTED
        newNode = null;
        selectedNode = null;

        // INIT THE COLORS
        currentFillColor = Color.web(WHITE_HEX);
        currentOutlineColor = Color.web(BLACK_HEX);
        currentBorderWidth = 1;

        // THIS IS FOR THE SELECTED SHAPE
        DropShadow dropShadowEffect = new DropShadow();
        dropShadowEffect.setOffsetX(0.0f);
        dropShadowEffect.setOffsetY(0.0f);
        dropShadowEffect.setSpread(1.0);
        dropShadowEffect.setColor(Color.YELLOW);
        dropShadowEffect.setBlurType(BlurType.GAUSSIAN);
        dropShadowEffect.setRadius(15);
        highlightedEffect = dropShadowEffect;

        transact = new jTPS();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getCurrentFillColor() {
        return currentFillColor;
    }

    public Color getCurrentOutlineColor() {
        return currentOutlineColor;
    }

    public double getCurrentBorderWidth() {
        return currentBorderWidth;
    }

    public void setItems(ObservableList<Node> initItems) {
        items = initItems;
    }

    public void setBackgroundColor(Color initBackgroundColor) {
        backgroundColor = initBackgroundColor;
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
        Background background = new Background(fill);
        canvas.setBackground(background);
    }

    public void setCurrentFillColor(Color initColor) {
        currentFillColor = initColor;
        if (selectedNode != null) {
            if (selectedNode instanceof Shape) {
                if (!((((Shape) selectedNode).getFill() instanceof ImagePattern))) {
                    ((Shape) selectedNode).setFill(currentFillColor);
                }
            }
        }
    }

    public void setCurrentOutlineColor(Color initColor) {
        currentOutlineColor = initColor;
        if (selectedNode != null) {
            if (selectedNode instanceof Shape) {
                ((Shape) selectedNode).setStroke(initColor);
            }
        }
    }

    public void setCurrentOutlineThickness(int initBorderWidth) {
        currentBorderWidth = initBorderWidth;
        if (selectedNode != null) {

            if (selectedNode instanceof Shape) {
                ((Shape) selectedNode).setStrokeWidth(initBorderWidth);
            }
        }
    }

    public Node selectTopShape(int x, int y) {
        Node shape = getTopShape(x, y);
        if (shape == selectedNode) {
            return shape;
        }

        if (selectedNode != null) {
            unhighlightShape(selectedNode);
        }
        if (shape != null) {
            highlightShape(shape);
            mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
            workspace.loadSelectedShapeSettings(shape);
        }
        selectedNode = shape;
        if (shape != null) {

            if (shape instanceof DraggableText) {

                ((DraggableText) shape).start(x, y);

            } else {
                ((Draggable) shape).start(x, y);
            }

        }
        return shape;
    }

    public Node getTopShape(int x, int y) {
        for (int i = items.size() - 1; i >= 0; i--) {
            Node shape = items.get(i);
            if (shape.contains(x, y)) {
                return shape;
            }
        }
        return null;
    }

    /**
     * This function clears out the HTML tree and reloads it with the minimal
     * tags, like html, head, and body such that the user can begin editing a
     * page.
     */
    @Override
    public void resetData() {
        setState(mapState.SELECTING);
        newNode = null;
        selectedNode = null;

        // INIT THE COLORS
        currentFillColor = Color.web(WHITE_HEX);
        currentOutlineColor = Color.web(BLACK_HEX);

        items.clear();
        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().clear();
    }

    public void selectSizedShape() {
        if (selectedNode != null) {
            unhighlightShape(selectedNode);
        }
        selectedNode = newNode;
        highlightShape(selectedNode);
        newNode = null;
        if (state == mapState.SIZING_ITEM) {
            state = ((Draggable) selectedNode).getStartingState();
        }
    }

    public void unhighlightShape(Node shape) {
        selectedNode.setEffect(null);
    }

    public void highlightShape(Node shape) {
        shape.setEffect(highlightedEffect);
    }

    public void addShape(Node shapeToAdd) {
        items.add(shapeToAdd);
    }

    public void removeShape(Node nodeToRemove) {
        items.remove(nodeToRemove);
    }

    public mapState getState() {
        return state;
    }

    public void setState(mapState initState) {
        state = initState;
    }

    public boolean isInState(mapState testState) {
        return state == testState;
    }

    public Node getNewShape() {
        return newNode;
    }

    public Node getSelectedShape() {
        return selectedNode;
    }

    public void setSelectedShape(Node initSelectedShape) {
        selectedNode = initSelectedShape;
    }

    public void removeSelectedItem() {
        if (selectedNode != null) {

            selectedNode = null;

            //TODO: WORK ON TRANSACTIONS
        }
    }

}
