package map.data;

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
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.LineTo;
import javafx.stage.FileChooser;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import static map.data.mapState.SELECTING;
import map.gui.CanvasController;
import map.gui.ZoomPane;
import map.gui.mapWorkspace;
import map.transact.AddToLine;
import map.transact.BackgroundEdit;
import map.transact.LineThick;
import map.transact.addNode;
import map.transact.removeNode;
import properties_manager.PropertiesManager;

/**
 * A class that was created for the purpose of manipulating data and handling
 * updates to the canvas and whatnot
 *
 * @author Ben Michalowicz
 * @version 1.0, 2017
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

    public AppTemplate getApp() {
        return app;
    }

    String lineName;

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

    mapWorkspace workspace;

    public static final String WHITE_HEX = "#FFFFFF";
    public static final String BLACK_HEX = "#000000";
    public static final String YELLOW_HEX = "#EEEE00";
    public static final Paint DEFAULT_BACKGROUND_COLOR = Paint.valueOf(WHITE_HEX);
    public static final Paint HIGHLIGHTED_COLOR = Paint.valueOf(YELLOW_HEX);
    public static final int HIGHLIGHTED_STROKE_THICKNESS = 1;

    private static jTPS transact;
    private static jTPS_Transaction t;

    public jTPS getTransact() {
        return transact;
    }

    public jTPS_Transaction getT() {
        return t;
    }

    public mapData(AppTemplate initApp) {

        // KEEP THE APP FOR LATER
        app = initApp;

        // NO SHAPE STARTS OUT AS SELECTED
        newNode = null;
        selectedNode = null;
        workspace = (mapWorkspace) app.getWorkspaceComponent();

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

    public String getLineName() {
        return app.getLineName();
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

        mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();
        ZoomPane canvas = work.getCanvas();
        Background prev = canvas.getBackground();

        backgroundColor = initBackgroundColor;

        BackgroundFill fill = new BackgroundFill(backgroundColor, null, null);
        Background background = new Background(fill);
        t = new BackgroundEdit(app, prev, b, canvas);
        transact.addTransaction(t);
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
                t = new LineThick(app,((Shape)selectedNode), (int) ((Shape)selectedNode).getStrokeWidth(), initBorderWidth);
                transact.addTransaction(t);
                
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
            mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();
            work.loadSelectedShapeSettings(shape);
        }
        selectedNode = shape;
        if (shape != null) {

            if (shape instanceof Draggable && !(shape instanceof DraggableLine)) {

                ((Draggable) shape).start(x, y);

            } else {

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

    public Object getSelectedShape() {
        return selectedNode;
    }

    public void setSelectedShape(Node initSelectedShape) {
        selectedNode = initSelectedShape;
    }

    public void removeSelectedItem() {

        if (selectedNode != null) {

            t = new removeNode(app, selectedNode);

            transact.addTransaction(t);

            selectedNode = null;
        }

    }

    public String s;

    public String getS() {
        return s;
    }

    Background b;

    public Background getB() {
        return b;
    }

    public void startNewBackground() {

        mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();

        Background b1 = work.getCanvas().getBackground();

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fc.setInitialDirectory(new File(PATH_IMAGES));

        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                setState(mapState.STARTING_BCKGROUND);

                work.getCanvas().setBackground(new Background(new BackgroundImage(loadImg(selectedFile),
                        BackgroundRepeat.ROUND,
                        BackgroundRepeat.ROUND,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT)));

                s = selectedFile.getPath();

                b = work.getCanvas().getBackground();

                t = new BackgroundEdit(app, b1, b, work.getCanvas());
                transact.addTransaction(t);

            } catch (MalformedURLException e) {
                AppMessageDialogSingleton.getSingleton().show("Background Image Error", "You encountered an error loading your background image");
            }
        }
    }

    private Image loadImg(File f) throws MalformedURLException {

        URL fileU = f.toURI().toURL();

        Image image = new Image(fileU.toExternalForm());

        return image;
    }

    public void startNewImage(int x, int y) {
        DraggableImage d = new DraggableImage(app);
        d.setImg(d.getNewImage());

        d.start(x, y);
        d.toBack();
        newNode = d;

        initNode();

    }

    public void startNewText(int x, int y) {
        DraggableText newText = new DraggableText(app);

        newText.start(x, y);
        newNode = newText;

        initNode();
    }

    public void startNewStation(int x, int y) {

        workspace = (mapWorkspace) app.getWorkspaceComponent();
        TextInputDialog statName = new TextInputDialog();
        statName.setTitle("Make a station Name!");
        statName.setHeaderText(null);
        statName.setContentText("Add a name for your station!");

        Optional<String> result = statName.showAndWait();

        if (!result.isPresent()) {
            return;
        } else {

            while (result.get() == "") {
                Alert duplicate = new Alert(Alert.AlertType.ERROR);
                duplicate.setHeaderText(null);
                duplicate.setContentText("You need a name for the station!!");
                duplicate.showAndWait();
                result = statName.showAndWait();

            }
        }

        DraggableStation newStation = new DraggableStation(app, result.get());

        newStation.start(x, y);

        workspace.getStations().getItems().add(newStation.getName());
        workspace.getFromStat().getItems().add(newStation.getName());
        workspace.getToStat().getItems().add(newStation.getName());

        newNode = newStation;

        initNode();

    }

    public void initNode() {
        if (selectedNode != null) {
            unhighlightShape(selectedNode);
            selectedNode = null; //Terminate the reference
        }

        // USE THE CURRENT SETTINGS FOR THIS NEW SHAPE
        mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();

        // GO INTO SHAPE SIZING MODE
        state = mapState.SIZING_ITEM;
        state = mapState.SELECTING;

        if (newNode instanceof DraggableText) {

            ((DraggableText) newNode).addText();

        }
        app.getGUI().getPrimaryScene().setCursor(Cursor.DEFAULT);
        workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.getUndo().setDisable(false);
        t = new addNode(app, newNode);
        transact.addTransaction(t);

    }

    public void startNewMetroLine(int x, int y) {

        workspace = (mapWorkspace) app.getWorkspaceComponent();

        AddLineWindow addLiner = new AddLineWindow(app);

        addLiner.showAndWait();

        if (addLiner.getResultant().getSource() == addLiner.getBtnCancel()) {
            state = SELECTING;

        } else {

            DraggableLine newDraggableLine = new DraggableLine(app, addLiner.getName());

            newDraggableLine.setStroke(addLiner.getLineColor().getValue());
            newDraggableLine.setStrokeWidth(3);

            newDraggableLine.getStartName().setFill(addLiner.getLineColor().getValue());
            newDraggableLine.getEndName().setFill(addLiner.getLineColor().getValue());
            newDraggableLine.setCircular(addLiner.cb.isSelected());

            workspace.getLines().getItems().add(addLiner.getName());

            newDraggableLine.start(x, y);

            newNode = newDraggableLine;
            initNode();
        }
    }

    public void rotateLabel() {
        if (selectedNode != null && selectedNode instanceof DraggableText) {
            if (((DraggableText) selectedNode).getRotate() == 90) {
                ((DraggableText) selectedNode).setRotate(0);
            } else {
                ((DraggableText) selectedNode).setRotate(90);
            }
        }

    }

    public void addStatToLine(int x, int y, DraggableLine draggableLine) {
        state = SELECTING;

        mapData dataManager = (mapData) app.getDataComponent();

        Scene scene = app.getGUI().getPrimaryScene();

        //We're off to the Drag... Races
        Node node = dataManager.selectTopShape(x, y);
        while (node != null) {
            node = (Node) dataManager.getSelectedShape();
            if (node instanceof DraggableStation) {
                DraggableStation station = (DraggableStation) node;
                
                t = new AddToLine(app, draggableLine, station, x, y);
                transact.addTransaction(t);

                node = null;

            } else {
                break;

            }

        }

        scene.setCursor(Cursor.DEFAULT);
        dataManager.setState(mapState.DRAGGING_NOTHING);

        app.getWorkspaceComponent().reloadWorkspace(dataManager);

    }

}
