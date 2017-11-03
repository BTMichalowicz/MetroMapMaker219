/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.ui.AppGUI;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import jdk.internal.org.objectweb.asm.Label;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import static map.css.mapStyle.CLASS_BUTTON;
import static map.css.mapStyle.CLASS_COLOR_CHOOSER_CONTROL;
import static map.css.mapStyle.CLASS_EDIT_TOOLBAR;
import static map.css.mapStyle.CLASS_EDIT_TOOLBAR_ROW;
import static map.css.mapStyle.CLASS_RENDER_CANVAS;
import map.data.mapData;

/**
 *
 * @author Ben Michalowicz
 * @version 1.0
 */
public class mapWorkspace extends AppWorkspaceComponent {

    AppTemplate app; //The main app that will be used

    AppGUI gui; //The GUI for the app

    VBox editToolbar; //For Editing the Canvas

    // ALL THE V-BOXES FOR THE EDITOR TOOLBAR
    VBox addLinesMain, addStationsMain, fromTo, destButton,
            decor1, decor2, font1, font2, nav1, nav2;

    // ALL THE HBOXES FOR THE EDITOR TOOLBAR
    HBox lines1, lines2, lineSlider, stat1, stat2, statSlider, fromToDest,
            decorTop, decorBot, fontTop, fontBot, navTop, navBot;

    // Buttons that are not specified in the Framework;
    //Add save As and Export when you get the chance
    Button undo, redo, about;

    //BUTTONS IN THE EDITOR SECTIONS
    Button addLine, removeLine, addStat, removeStat, details,
            snapToGrid, moveLabel, rotate, fromToPop, imgBackground,
            addImage, addLabel, removeElement,
            zoomIn,
            zoomOut, increaseMapSize, decreaseMapSize,
            addToLine, removeFromLine;

    //CheckBoxes for bolding and italicizing
    CheckBox bold, italicize;

    CheckBox showGrid; //Checkboxes for showing the grid

    //Combo
    ComboBox lines, stations, fromStat, toStat, fontSizes, fontFamilies;

    //LABELS TO DEFINE EACH SECTION OF THE H-BOX
    Text metroLine, metroStation, decor, font, navigation;

    //ELLIPSES TO SHOW COLOR WITH COLOR TEXT IN HEX
    Ellipse lineColor, stationColor, backColor, fontColor;

    //Sliders for the size or thickness of each line and station
    Slider lineThickness;
    Slider statThickness;
    
    //COLOR PICKERS
    
    ColorPicker fillColorPicker, outlineColorPicker, backgroundColorPicker;

    public ColorPicker getFillColorPicker() {
        return fillColorPicker;
    }

    public ColorPicker getOutlineColorPicker() {
        return outlineColorPicker;
    }

    public ColorPicker getBackgroundColorPicker() {
        return backgroundColorPicker;
    }
    
    

    public ComboBox getLines() {
        return lines;
    }

    public void setLines(ComboBox lines) {
        this.lines = lines;
    }

    public ComboBox getStations() {
        return stations;
    }

    public void setStations(ComboBox stations) {
        this.stations = stations;
    }

    public ComboBox getFromStat() {
        return fromStat;
    }

    public void setFromStat(ComboBox fromStat) {
        this.fromStat = fromStat;
    }

    public ComboBox getToStat() {
        return toStat;
    }

    public void setToStat(ComboBox toStat) {
        this.toStat = toStat;
    }

    public AppTemplate getApp() {
        return app;
    }

    //THE MAIN CANVAS FOR THE APPLICATION
    Pane canvas;

    Text debugText;

    public Text getDebugText() {
        return debugText;
    }

    public void setDebugText(Text debugText) {
        this.debugText = debugText;
    }

    public Pane getCanvas() {
        return canvas;
    }
    mapData dataManager;

    mapEditController mapEditController;
    CanvasController canvasController;

    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

    jTPS transact;
    jTPS_Transaction transaction;

    public mapWorkspace(AppTemplate app) {
        this.app = app;

        dataManager = (mapData) app.getDataComponent();

        // KEEP THE GUI FOR LATER
        gui = app.getGUI();

        // LAYOUT THE APP
        initLayout();

        // HOOK UP THE CONTROLLERS
        //initControllers();
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
    }

    @Override
    public void resetWorkspace() {

    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void initLayout() {
        editToolbar = new VBox();
        redo = gui.initChildButton(gui.getFileToolbar(), "Redo", "Redo a given action", true);
        undo = gui.initChildButton(gui.getFileToolbar(), "Undo", "Undo a given action", true);
        about = gui.initChildButton(gui.getFileToolbar(), "About", "About the developer", false);

        redo.getStyleClass().add(CLASS_FILE_BUTTON);
        undo.getStyleClass().add(CLASS_FILE_BUTTON);
        about.getStyleClass().add(CLASS_FILE_BUTTON);

        //HBOX 1: Line Editor Part 1
        addLinesMain = new VBox();

        lines1 = new HBox();

        lines = new ComboBox();
        metroLine = new Text("Metro Lines");
        lineColor = new Ellipse(3, 3);

        lines1.getChildren().addAll(metroLine, lines, lineColor);

        //HBOX 2: Line Editor Part 2
        lines2 = new HBox();

        /*
         
         TODO: Replace these with gui.initChildButton(...) later!!!
         */
        addLine = new Button();
        removeLine = new Button();
        addToLine = new Button();
        removeFromLine = new Button();
        details = new Button();

        lines2.getChildren().addAll(addLine, removeLine, addToLine, removeFromLine);

        //HBOX 3: Line Editor Part 3
        lineSlider = new HBox();
        lineThickness = new Slider();
        lineSlider.getChildren().add(lineThickness);

        addLinesMain.getChildren().addAll(lines1, lines2, lineSlider);

        /////////////////////////////////////////////////
        //**** VBOX 2: STATION SETUP!!!
        addStationsMain = new VBox();

        stat1 = new HBox();

        metroStation = new Text();
        stationColor = new Ellipse(3, 3);
        stations = new ComboBox();

        stat1.getChildren().addAll(metroStation, stations, stationColor);

        /// VBOX 2, PART 2, BUTTONS
        //TODO: REPLACE WITH GUI.INITcHILDBUTTON()
        stat2 = new HBox();
        addStat = new Button();
        removeStat = new Button();
        snapToGrid = new Button();
        moveLabel = new Button();
        rotate = new Button();

        stat2.getChildren().addAll(addStat, removeStat, snapToGrid, moveLabel, rotate);

        // PART 3: SLIDER AGAIN
        statSlider = new HBox();
        statThickness = new Slider();

        statSlider.getChildren().add(statThickness);

        addStationsMain.getChildren().addAll(stat1, stat2, statSlider);

        /// VBOX 3: DESTINATION FROM-TO
        fromTo = new VBox();
        fromStat = new ComboBox();
        toStat = new ComboBox();

        fromToDest = new HBox();

        //TODO: REPLACE WITH GUI.INITCHILDBUTTON()...
        fromToPop = new Button();

        fromTo.getChildren().addAll(fromStat, toStat);

        fromToDest.getChildren().addAll(fromTo, fromToPop);

        /// VBOX 4; DECORUM... SWEET SWEET DECORUM
        decor1 = new VBox();
        decorTop = new HBox();
        decor = new Text();
        backColor = new Ellipse(3, 3);

        decorTop.getChildren().addAll(decor, backColor);

        /// Part 2
        decorBot = new HBox();

        imgBackground = new Button();
        addImage = new Button();
        addLabel = new Button();
        removeElement = new Button();

        decorBot.getChildren().addAll(imgBackground, addImage, addLabel, removeElement);

        //VBOX 5: FONT
        font1 = new VBox();

        fontTop = new HBox();

        font = new Text();

        fontColor = new Ellipse();
        fontTop.getChildren().addAll(font, fontColor);

        fontBot = new HBox();
        // PART 2;:
        bold = new CheckBox();
        italicize = new CheckBox();
        fontSizes = new ComboBox();
        fontFamilies = new ComboBox();

        fontBot.getChildren().addAll(bold, italicize, fontSizes, fontFamilies);

        font1.getChildren().addAll(fontTop, fontBot);

        //LAST VBOX: NAVIGATION
        nav1 = new VBox();
        navTop = new HBox();

        navigation = new Text();
        showGrid = new CheckBox();
        Text showLabel = new Text("Show Grid");

        navTop.getChildren().addAll(navigation, showGrid, showLabel);

        navBot = new HBox();

        //TODO: TAKE CARE OF GUI.INITCHILDBUTTON
        zoomIn = new Button();
        zoomOut = new Button();
        increaseMapSize = new Button();
        decreaseMapSize = new Button();

        navBot.getChildren().addAll(zoomIn, zoomOut, increaseMapSize, decreaseMapSize);

        nav1.getChildren().addAll(navTop, navBot);

        editToolbar.getChildren().addAll(addLinesMain, addStationsMain, fromToDest, decor1,
                font1, nav1);

        canvas = new Pane();
        debugText = new Text();
        canvas.getChildren().add(debugText);
        debugText.setX(100);
        debugText.setY(100);

        dataManager = (mapData) app.getDataComponent();
        dataManager.setList(canvas.getChildren());

        workspace = new BorderPane();
        ((BorderPane) workspace).setLeft(editToolbar);
        ((BorderPane) workspace).setCenter(canvas);
        
        
        
        
    }
    
    
    private void initStyle(){
        // NOTE THAT EACH CLASS SHOULD CORRESPOND TO
        // A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
        // CSS FILE
        canvas.getStyleClass().add(CLASS_RENDER_CANVAS);

        // COLOR PICKER STYLE
        fillColorPicker.getStyleClass().add(CLASS_BUTTON);
        outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
        backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);

        editToolbar.getStyleClass().add(CLASS_EDIT_TOOLBAR);
        addLinesMain.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        addStationsMain.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fromTo.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        fromToDest.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
       // backgroundColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);

        decor1.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        //fillColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        font1.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
        //outlineColorLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        nav1.getStyleClass().add(CLASS_EDIT_TOOLBAR_ROW);
       // outlineThicknessLabel.getStyleClass().add(CLASS_COLOR_CHOOSER_CONTROL);
        

    }

    

}
