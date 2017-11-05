package map.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.ABOUT_ICON;
import static djf.settings.AppPropertyType.ABOUT_TOOLTIP;
import djf.ui.AppGUI;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import static map.css.mapStyle.CLASS_BUTTON;
import static map.css.mapStyle.CLASS_COLOR_CHOOSER_CONTROL;
import static map.css.mapStyle.CLASS_EDIT_TOOLBAR;
import static map.css.mapStyle.CLASS_EDIT_TOOLBAR_ROW;
import static map.css.mapStyle.CLASS_RENDER_CANVAS;
import map.data.mapData;
import map.data.mapState;
import map.mapLanguageProperty;
import static map.mapLanguageProperty.ADD_ELEM;
import static map.mapLanguageProperty.ADD_IMG_TOOLTIP;
import static map.mapLanguageProperty.ADD_LABEL_TOOLTIP;
import static map.mapLanguageProperty.ADD_LINE_TOOLTIP;
import static map.mapLanguageProperty.ADD_STATION_TOOLTIP;
import static map.mapLanguageProperty.BACK_IMG_TOOLTIP;
import static map.mapLanguageProperty.DECREASE_ICON;
import static map.mapLanguageProperty.DECREASE_TOOLTIP;
import static map.mapLanguageProperty.FROM_TO_ICON;
import static map.mapLanguageProperty.FROM_TO_TOOLTIP;
import static map.mapLanguageProperty.INCREASE_ICON;
import static map.mapLanguageProperty.INCREASE_TOOLTIP;
import static map.mapLanguageProperty.LINE_DETAILS_TOOLTIP;
import static map.mapLanguageProperty.MOVE_LABEL;
import static map.mapLanguageProperty.REDO_ICON;
import static map.mapLanguageProperty.REDO_TOOLTIP;
import static map.mapLanguageProperty.REMOVE_ELEM;
import static map.mapLanguageProperty.REMOVE_ELEM_TOOLTIP;
import static map.mapLanguageProperty.REMOVE_FROM_TOOLTIP;
import static map.mapLanguageProperty.REMOVE_LINE_TOOLTIP;
import static map.mapLanguageProperty.REMOVE_STATION_TOOLTIP;
import static map.mapLanguageProperty.ROTATE_LABEL_ICON;
import static map.mapLanguageProperty.ROTATE_LABEL_TOOLTIP;
import static map.mapLanguageProperty.SNAP_TOOLTIP;
import static map.mapLanguageProperty.UNDO_ICON;
import static map.mapLanguageProperty.UNDO_TOOLTIP;
import static map.mapLanguageProperty.ZOOM_IN_ICON;
import static map.mapLanguageProperty.ZOOM_IN_TOOLTIP;
import static map.mapLanguageProperty.ZOOM_OUT_ICON;
import static map.mapLanguageProperty.ZOOM_OUT_TOOLTIP;

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
    // ColorPicker fillColorPicker, outlineColorPicker, backgroundColorPicker;
//    public ColorPicker getFillColorPicker() {
//        return fillColorPicker;
//    }
//
//    public ColorPicker getOutlineColorPicker() {
//        return outlineColorPicker;
//    }
//
//    public ColorPicker getBackgroundColorPicker() {
//        return backgroundColorPicker;
//    }
    public ComboBox getLines() {
        return lines;
    }

    public void setLines(ComboBox lines) {
        this.lines = lines;
    }

    public void setLines(ObservableList stuff) {
        this.lines = new ComboBox(stuff);

    }

    public void setStations(ObservableList stations) {
        this.fromStat = new ComboBox(stations);
        this.toStat = new ComboBox(stations);

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
        //initControllers(); //TODO: GET THIS DONE TIMELY!!
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
    }

    @Override
    public void resetWorkspace() {

    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        dataManager  = (mapData) dataComponent;
        
        if(dataManager.isInState(mapState.STARTING_LINE)){
            addLabel.setDisable(false);
            removeElement.setDisable(false);
            addImage.setDisable(false);
            addLine.setDisable(true);
            imgBackground.setDisable(true);
            addToLine.setDisable(true);
            removeFromLine.setDisable(true);
            addStat.setDisable(false);
            removeStat.setDisable(true);
            details.setDisable(true);
            fromToPop.setDisable(true);
            
        }else if(dataManager.isInState(mapState.STARTING_IMAGE)){
            
        }
    }

    private void initLayout() {
        editToolbar = new VBox();
        redo = gui.initChildButton(gui.getFileToolbar(), REDO_ICON.toString(), REDO_TOOLTIP.toString(), true);
        undo = gui.initChildButton(gui.getFileToolbar(), UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), true);
        about = gui.initChildButton(gui.getFileToolbar(), ABOUT_ICON.toString(), ABOUT_TOOLTIP.toString(), false);

        redo.getStyleClass().add(CLASS_FILE_BUTTON);
        undo.getStyleClass().add(CLASS_FILE_BUTTON);
        about.getStyleClass().add(CLASS_FILE_BUTTON);

        //HBOX 1: Line Editor Part 1
        addLinesMain = new VBox();

        lines1 = new HBox();

        lines = new ComboBox();
        metroLine = new Text("Metro Lines");
        lineColor = new Ellipse(50, 50);
        lineColor.setFill(Paint.valueOf("0xFFFFFF"));
        lineColor.setAccessibleText(lineColor.getFill().toString());

        lines1.getChildren().addAll(metroLine, lines, lineColor);

        //HBOX 2: Line Editor Part 2
        lines2 = new HBox();

        
        addLine = gui.initChildButton(lines2, ADD_ELEM.toString(), ADD_LINE_TOOLTIP.toString(), false);
        removeLine = gui.initChildButton(lines2, REMOVE_ELEM.toString(), REMOVE_LINE_TOOLTIP.toString(), false);
        addToLine = gui.initChildButton(lines2, " ", mapLanguageProperty.ADD_TO_TOOLTIP.toString(), false);
        addToLine.setText("Add Station to Line");
        removeFromLine = gui.initChildButton(lines2, " ", REMOVE_FROM_TOOLTIP.toString(), true);
        removeFromLine.setText("Remove Station from Line");

        details = gui.initChildButton(lines2, mapLanguageProperty.LINE_DETAILS.toString(), LINE_DETAILS_TOOLTIP.toString(), workspaceActivated);

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

        stationColor = new Ellipse(50, 50);
        stationColor.setFill(Paint.valueOf("0xFFFFFF"));
        stationColor.setAccessibleText(lineColor.getFill().toString());

        stations = new ComboBox();
        stations.setPromptText("Metro Stations");

        stat1.getChildren().addAll(metroStation, stations, stationColor);

        /// VBOX 2, PART 2, BUTTONS
        //TODO: REPLACE WITH GUI.INITcHILDBUTTON()
        stat2 = new HBox();
        addStat = gui.initChildButton(stat2, ADD_ELEM.toString(), ADD_STATION_TOOLTIP.toString(), true);
        removeStat = gui.initChildButton(stat2, REMOVE_ELEM.toString(), REMOVE_STATION_TOOLTIP.toString(), true);
        snapToGrid = gui.initChildButton(stat2, " ", SNAP_TOOLTIP.toString(), false);
        snapToGrid.setText("Snap");
        moveLabel = gui.initChildButton(stat2, " ", MOVE_LABEL.toString(), true);
        rotate = gui.initChildButton(stat2, ROTATE_LABEL_ICON.toString(), ROTATE_LABEL_TOOLTIP.toString(), true);

        // PART 3: SLIDER AGAIN
        statSlider = new HBox();
        statThickness = new Slider();

        statSlider.getChildren().add(statThickness);

        addStationsMain.getChildren().addAll(stat1, stat2, statSlider);

        /// VBOX 3: DESTINATION FROM-TO
        fromTo = new VBox();
        fromStat = new ComboBox();
        fromStat.setPromptText("Choose a starting station");
        toStat = new ComboBox();
        toStat.setPromptText("Choose an ending destination");

        fromToDest = new HBox();

        //TODO: REPLACE WITH GUI.INITCHILDBUTTON()...
        fromToPop = gui.initChildButton(fromTo, FROM_TO_ICON.toString(), FROM_TO_TOOLTIP.toString(), false);

        fromTo.getChildren().addAll(fromStat, toStat);

        fromToDest.getChildren().addAll(fromTo, fromToPop);

        /// VBOX 4; DECORUM... SWEET SWEET DECORUM
        decor1 = new VBox();
        decorTop = new HBox();
        decorTop.setSpacing(decorTop.getWidth() / 2);
        decor = new Text();

        backColor = new Ellipse(50, 50);
        backColor.setFill(Paint.valueOf("0xFFFFFF"));
        backColor.setAccessibleText(lineColor.getFill().toString());

        decorTop.getChildren().addAll(decor, backColor);

        /// Part 2
        decorBot = new HBox();

        imgBackground = gui.initChildButton(decorBot, " ", BACK_IMG_TOOLTIP.toString(), false);
        imgBackground.setText("Add a background image");
        addImage = gui.initChildButton(decorBot, " ", ADD_IMG_TOOLTIP.toString(), false);
        addImage.setText("Add an image overlay");
        addLabel = gui.initChildButton(decorBot, " ", ADD_LABEL_TOOLTIP.toString(), false);
        addLabel.setText("Add A Label");
        removeElement = gui.initChildButton(decorBot, " ", REMOVE_ELEM_TOOLTIP.toString(), true);
        removeElement.setText("Remove an element");

        //decorBot.getChildren().addAll(imgBackground, addImage, addLabel, removeElement);
        //VBOX 5: FONT
        font1 = new VBox();

        fontTop = new HBox();

        font = new Text();

        fontColor = new Ellipse(50, 50);
        fontColor.setFill(Paint.valueOf("0x000000"));
        fontColor.setAccessibleText(lineColor.getFill().toString());
        fontTop.setSpacing(fontTop.getWidth() / 2);
        fontTop.getChildren().addAll(font, fontColor);

        fontBot = new HBox();
        // PART 2;:
        bold = new CheckBox();
        Text boldText = new Text("Bold your text ->");
        italicize = new CheckBox();
        Text iText = new Text("Italicize ->");

        ObservableList<Integer> sizes = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 20, 21, 22, 23, 48, 72, 96);
        fontSizes = new ComboBox(sizes);
        fontSizes.setPromptText("Choose a font size");

        ObservableList<String> families = FXCollections.observableArrayList("Sans Serif", "Comic Sans MS", "Times New Roman", "Arial", "Courier", "Cambria");
        fontFamilies = new ComboBox(families);
        fontSizes.setPromptText("Choose a font family");

        fontBot.getChildren().addAll(boldText, bold, iText, italicize, fontSizes, fontFamilies);
        fontBot.setSpacing(1.5);

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
        zoomIn = gui.initChildButton(navBot, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), false);
        zoomOut = gui.initChildButton(navBot, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        increaseMapSize = gui.initChildButton(navBot, INCREASE_ICON.toString(), INCREASE_TOOLTIP.toString(), false);
        decreaseMapSize = gui.initChildButton(navBot, DECREASE_ICON.toString(), DECREASE_TOOLTIP.toString(), false);

        

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

    private void initStyle() {
        // NOTE THAT EACH CLASS SHOULD CORRESPOND TO
        // A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
        // CSS FILE
        canvas.getStyleClass().add(CLASS_RENDER_CANVAS);

        // COLOR PICKER STYLE
//        fillColorPicker.getStyleClass().add(CLASS_BUTTON);
        // outlineColorPicker.getStyleClass().add(CLASS_BUTTON);
        //backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
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
