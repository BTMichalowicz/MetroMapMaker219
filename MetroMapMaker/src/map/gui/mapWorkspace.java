package map.gui;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import static djf.settings.AppPropertyType.ABOUT_ICON;
import static djf.settings.AppPropertyType.ABOUT_TOOLTIP;
import static djf.settings.AppPropertyType.EXPORT_ICON;
import static djf.settings.AppPropertyType.EXPORT_TOOLTIP;
import djf.ui.AppGUI;
import static djf.ui.AppGUI.CLASS_BORDERED_PANE;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import static map.css.mapStyle.CLASS_BUTTON;
import static map.css.mapStyle.CLASS_EDIT_TOOLBAR;
import static map.css.mapStyle.CLASS_EDIT_TOOLBAR_ROW;
import static map.css.mapStyle.CLASS_RENDER_CANVAS;
import map.data.DraggableLine;
import map.data.DraggableStation;
import map.data.DraggableText;
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
import map.transact.TextFontColorContent;

/**
 *
 * @author Ben Michalowicz
 * @version 1.0
 */
public class mapWorkspace extends AppWorkspaceComponent {

    Group mainSpot;

    public Group getMainSpot() {
        return mainSpot;
    }
    ScrollPane outerCanvas;

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
            addToLine, removeFromLine, export, editLine;
    Label mapName;

    public Button getUndo() {
        return undo;
    }

    public Button getRedo() {
        return redo;
    }

    public void setGui(AppGUI gui) {
        this.gui = gui;
    }

    public void setEditToolbar(VBox editToolbar) {
        this.editToolbar = editToolbar;
    }

    public void setAddLinesMain(VBox addLinesMain) {
        this.addLinesMain = addLinesMain;
    }

    public void setAddStationsMain(VBox addStationsMain) {
        this.addStationsMain = addStationsMain;
    }

    public void setFromTo(VBox fromTo) {
        this.fromTo = fromTo;
    }

    public void setFont1(VBox font1) {
        this.font1 = font1;
    }

    public void setFromToDest(HBox fromToDest) {
        this.fromToDest = fromToDest;
    }

    public void setFontTop(HBox fontTop) {
        this.fontTop = fontTop;
    }

    public void setFontBot(HBox fontBot) {
        this.fontBot = fontBot;
    }

    public void setAbout(Button about) {
        this.about = about;
    }

    public void setAddLine(Button addLine) {
        this.addLine = addLine;
    }

    public void setAddStat(Button addStat) {
        this.addStat = addStat;
    }

    public void setFromToPop(Button fromToPop) {
        this.fromToPop = fromToPop;
    }

    public void setImgBackground(Button imgBackground) {
        this.imgBackground = imgBackground;
    }

    public void setAddImage(Button addImage) {
        this.addImage = addImage;
    }

    public void setAddLabel(Button addLabel) {
        this.addLabel = addLabel;
    }

    public void setAddToLine(Button addToLine) {
        this.addToLine = addToLine;
    }

    public void setExport(Button export) {
        this.export = export;
    }

    public void setEditLine(Button editLine) {
        this.editLine = editLine;
    }

    public void setBold(CheckBox bold) {
        this.bold = bold;
    }

    public void setFontSizes(ComboBox<Integer> fontSizes) {
        this.fontSizes = fontSizes;
    }

    public void setFontFamilies(ComboBox<String> fontFamilies) {
        this.fontFamilies = fontFamilies;
    }

    public void setDecor(Text decor) {
        this.decor = decor;
    }

    public void setFont(Text font) {
        this.font = font;
    }

    public void setFontColor(Ellipse fontColor) {
        this.fontColor = fontColor;
    }

    public void setBackgroundColorPicker(ColorPicker backgroundColorPicker) {
        this.backgroundColorPicker = backgroundColorPicker;
    }

    public void setCanvas(ZoomPane canvas) {
        this.canvas = canvas;
    }

    public void setDataManager(mapData dataManager) {
        this.dataManager = dataManager;
    }

    public void setCanvasController(CanvasController canvasController) {
        this.canvasController = canvasController;
    }

    //CheckBoxes for bolding and italicizing
    CheckBox bold, italicize;

    CheckBox showGrid; //Checkboxes for showing the grid

    //Combo
    ComboBox<String> lines;

    ComboBox<String> stations, fromStat, toStat;

    ComboBox<Integer> fontSizes;

    ComboBox<String> fontFamilies;

    //LABELS TO DEFINE EACH SECTION OF THE H-BOX
    Text metroLine, metroStation, decor, font, navigation;

    //ELLIPSES TO SHOW COLOR WITH COLOR TEXT IN HEX
    Ellipse lineColor, stationColor, backColor, fontColor;

    //Sliders for the size or thickness of each line and station
    Slider lineThickness;
    Slider statThickness;

    //COLOR PICKERS
    ColorPicker lineColorPicker, stationColorPicker, backgroundColorPicker, fontColorPicker;

    public ColorPicker getStationColorPicker() {
        return stationColorPicker;
    }

    public ColorPicker getOutlineColorPicker() {
        return lineColorPicker;
    }

    public ColorPicker getBackgroundColorPicker() {
        return backgroundColorPicker;
    }

    public ComboBox getLines() {
        return lines;
    }

    public void setLines(ComboBox<String> lines) {
        this.lines = lines;
    }

    public void setLines(ObservableList<String> stuff) {
        this.lines = new ComboBox<>(stuff);

    }

    public void setStations(ObservableList<String> stations) {
        this.fromStat = new ComboBox<>(stations);
        this.toStat = new ComboBox<>(stations);

    }

    public ComboBox<String> getStations() {
        return stations;
    }

    public void setStations(ComboBox<String> stations) {
        this.stations = stations;
    }

    public ComboBox getFromStat() {
        return fromStat;
    }

    public void setFromStat(ComboBox<String> fromStat) {
        this.fromStat = fromStat;
    }

    public ComboBox getToStat() {
        return toStat;
    }

    public void setToStat(ComboBox<String> toStat) {
        this.toStat = toStat;
    }

    public AppTemplate getApp() {
        return app;
    }

    //THE MAIN CANVAS FOR THE APPLICATION
    ZoomPane canvas;

    Text debugText;

    public Text getDebugText() {
        return debugText;
    }

    GridPane gp; //FOR SHOWING THE GRID ON THE CANVAS

    public void setDebugText(Text debugText) {
        this.debugText = debugText;
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

        canvasController = new CanvasController(app);

        backgroundColorPicker = new ColorPicker();

        lineColorPicker = new ColorPicker();
        fontColorPicker = new ColorPicker();
        stationColorPicker = new ColorPicker();

        // LAYOUT THE APP
        initLayout();

        // HOOK UP THE CONTROLLERS
        initControllers(); //TODO: GET THIS DONE TIMELY!!
        // AND INIT THE STYLE FOR THE WORKSPACE
        initStyle();
    }

    @Override
    public void resetWorkspace() {

    }

    public CanvasController getCanvasController() {
        return canvasController;
    }

    public Button getEditLine() {
        return editLine;
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        dataManager = (mapData) dataComponent;

        Node data = (Node) dataManager.getSelectedShape();

        if (dataManager.isInState(mapState.STARTING_LINE)) {
            addLine.setDisable(true);
            removeLine.setDisable(false);
            addToLine.setDisable(false);
            removeFromLine.setDisable(false);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(true);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(false);
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(false);
            addLabel.setDisable(false);
            editLine.setDisable(false);

            removeElement.setDisable(!(dataManager.getSelectedShape() != null));

        } else if (dataManager.isInState(mapState.STARTING_OVERLAY)) {
            addLine.setDisable(true);
            removeLine.setDisable(true);
            addToLine.setDisable(false);
            removeFromLine.setDisable(false);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(true);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(!(data != null && data instanceof DraggableText));
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(true);
            addLabel.setDisable(false);
            removeElement.setDisable(dataManager.getSelectedShape() == null);
            editLine.setDisable(false);

        } else if (dataManager.isInState(mapState.STARTING_TEXT)) {
            addLine.setDisable(false);
            removeLine.setDisable(false);
            addToLine.setDisable(false);
            removeFromLine.setDisable(false);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(false);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(false);
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(false);
            addLabel.setDisable(false);
            removeElement.setDisable(dataManager.getSelectedShape() == null);
            editLine.setDisable(false);

        } else if (dataManager.isInState(mapState.STARTING_STATION)) {
            addLine.setDisable(false);
            removeLine.setDisable(false);
            addToLine.setDisable(false);
            removeFromLine.setDisable(true);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(false);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(false);
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(false);
            addLabel.setDisable(false);
            removeElement.setDisable(dataManager.getSelectedShape() == null);
            editLine.setDisable(false);
        } else if (dataManager.isInState(mapState.SELECTING)
                || dataManager.isInState(mapState.DRAGGING)
                || dataManager.isInState(mapState.DRAGGING_NOTHING)) {

            addLine.setDisable(false);
            removeLine.setDisable(false);
            addToLine.setDisable(false);
            removeFromLine.setDisable(false);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(false);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(false);
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(false);
            addLabel.setDisable(false);
            removeElement.setDisable(dataManager.getSelectedShape() == null);
            editLine.setDisable(false);

        } else if (dataManager.isInState(mapState.ROTATING_LABEL)) {

            addLine.setDisable(false);
            addLine.setDisable(false);
            removeLine.setDisable(false);
            addToLine.setDisable(false);
            removeFromLine.setDisable(false);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(false);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(false);
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(false);
            addLabel.setDisable(false);
            removeElement.setDisable(dataManager.getSelectedShape() == null);
            editLine.setDisable(false);

        } else {
            addLine.setDisable(false);
            removeLine.setDisable(false);
            addToLine.setDisable(false);
            removeFromLine.setDisable(false);
            details.setDisable(false);
            addStat.setDisable(false);
            removeStat.setDisable(false);
            snapToGrid.setDisable(false);
            moveLabel.setDisable(false);
            rotate.setDisable(false);
            fromToPop.setDisable(false);
            imgBackground.setDisable(false);
            addImage.setDisable(false);
            addLabel.setDisable(false);
            removeElement.setDisable(dataManager.getSelectedShape() == null);
            editLine.setDisable(false);
        }

    }

    private void initLayout() {

        gp = new GridPane();
        editToolbar = new VBox();

        mapName = new Label(app.getLineName());

        mapName.setFont(Font.font(48));

        FlowPane undoRedo = new FlowPane();

        export = gui.initChildButton(gui.getFileToolbar(), EXPORT_ICON.toString(), EXPORT_TOOLTIP.toString(), false);
        redo = gui.initChildButton(undoRedo, REDO_ICON.toString(), REDO_TOOLTIP.toString(), true);
        undo = gui.initChildButton(undoRedo, UNDO_ICON.toString(), UNDO_TOOLTIP.toString(), true);
        about = gui.initChildButton(undoRedo, ABOUT_ICON.toString(), ABOUT_TOOLTIP.toString(), false);

        gui.getTopToolbarPane().getChildren().add(undoRedo);
        gui.getTopToolbarPane().setHgap(10);
        gui.getFileToolbar().setHgap(10);

        undoRedo.setHgap(10);

        undoRedo.getStyleClass().add(CLASS_BORDERED_PANE);

        redo.getStyleClass().add(CLASS_FILE_BUTTON);
        undo.getStyleClass().add(CLASS_FILE_BUTTON);
        about.getStyleClass().add(CLASS_FILE_BUTTON);
        export.getStyleClass().add(CLASS_FILE_BUTTON);

        //HBOX 1: Line Editor Part 1
        addLinesMain = new VBox();

        lines1 = new HBox();

        lines = new ComboBox<>();
        lines.setPromptText("Choose a MetroLine!");
        metroLine = new Text("Metro Lines");

        lines1.getChildren().addAll(metroLine, lines);
        editLine = gui.initChildButton(lines1, " ", " ", false);

        editLine.setTooltip(new Tooltip("Edit the given line"));

        //HBOX 2: Line Editor Part 2
        lines2 = new HBox();

        addLine = gui.initChildButton(lines2, ADD_ELEM.toString(), ADD_LINE_TOOLTIP.toString(), false);
        removeLine = gui.initChildButton(lines2, REMOVE_ELEM.toString(), REMOVE_LINE_TOOLTIP.toString(), false);
        addToLine = gui.initChildButton(lines2, " ", mapLanguageProperty.ADD_TO_TOOLTIP.toString(), false);
        addToLine.setText("Add Station to Line");
        removeFromLine = gui.initChildButton(lines2, " ", REMOVE_FROM_TOOLTIP.toString(), false);
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

        stations = new ComboBox();
        stations.setPromptText("Metro Stations");

        stat1.getChildren().addAll(metroStation, stations, stationColorPicker);

        /// VBOX 2, PART 2, BUTTONS
        stat2 = new HBox();
        addStat = gui.initChildButton(stat2, ADD_ELEM.toString(), ADD_STATION_TOOLTIP.toString(), false);
        removeStat = gui.initChildButton(stat2, REMOVE_ELEM.toString(), REMOVE_STATION_TOOLTIP.toString(), false);
        snapToGrid = gui.initChildButton(stat2, " ", SNAP_TOOLTIP.toString(), false);
        snapToGrid.setText("Snap");
        moveLabel = gui.initChildButton(stat2, " ", MOVE_LABEL.toString(), false);
        moveLabel.setText("Move\nLabel");
        rotate = gui.initChildButton(stat2, ROTATE_LABEL_ICON.toString(), ROTATE_LABEL_TOOLTIP.toString(), false);

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

        fromToPop = gui.initChildButton(fromTo, FROM_TO_ICON.toString(), FROM_TO_TOOLTIP.toString(), false);

        fromTo.getChildren().addAll(fromStat, toStat);

        fromToDest.getChildren().addAll(fromTo, fromToPop);

        /// VBOX 4; DECORUM... SWEET SWEET DECORUM
        decor1 = new VBox(25);
        decorTop = new HBox(35);
        //decorTop.setSpacing(decorTop.getWidth() / 2);
        decor = new Text("Decor");

        decorTop.getChildren().addAll(decor, backgroundColorPicker);

        /// Part 2
        decorBot = new HBox();

        imgBackground = gui.initChildButton(decorBot, " ", BACK_IMG_TOOLTIP.toString(), false);
        imgBackground.setText("Add a background image");
        addImage = gui.initChildButton(decorBot, " ", ADD_IMG_TOOLTIP.toString(), false);
        addImage.setText("Add an image overlay");
        addLabel = gui.initChildButton(decorBot, " ", ADD_LABEL_TOOLTIP.toString(), false);
        addLabel.setText("Add A Label");
        removeElement = gui.initChildButton(decorBot, " ", REMOVE_ELEM_TOOLTIP.toString(), false);
        removeElement.setText("Remove an element");

        //decorBot.getChildren().addAll(imgBackground, addImage, addLabel, removeElement);
        decor1.getChildren().addAll(decorTop, decorBot);

        //VBOX 5: FONT
        font1 = new VBox();

        fontTop = new HBox();

        font = new Text("Font         ");

        ObservableList<Integer> sizes = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, 17, 20, 21, 22, 23, 48, 72, 96);
        fontSizes = new ComboBox(sizes);
        fontSizes.setPromptText("Choose a font size");
        fontTop.getChildren().addAll(font, fontColorPicker, fontSizes);

        fontBot = new HBox();
        // PART 2;:
        bold = new CheckBox();
        Text boldText = new Text("Bold your text ->");
        italicize = new CheckBox();
        Text iText = new Text("Italicize ->");

        ObservableList<String> families = FXCollections.observableArrayList("Sans Serif", "Comic Sans MS", "Times New Roman", "Arial", "Courier", "Cambria");
        fontFamilies = new ComboBox(families);
        fontFamilies.setPromptText("Choose a font family");

        fontBot.getChildren().addAll(boldText, bold, iText, italicize, fontFamilies);
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

        zoomIn = gui.initChildButton(navBot, ZOOM_IN_ICON.toString(), ZOOM_IN_TOOLTIP.toString(), false);
        zoomOut = gui.initChildButton(navBot, ZOOM_OUT_ICON.toString(), ZOOM_OUT_TOOLTIP.toString(), false);
        increaseMapSize = gui.initChildButton(navBot, INCREASE_ICON.toString(), INCREASE_TOOLTIP.toString(), false);
        decreaseMapSize = gui.initChildButton(navBot, DECREASE_ICON.toString(), DECREASE_TOOLTIP.toString(), false);

        nav1.getChildren().addAll(navTop, navBot);

        editToolbar.getChildren().addAll(addLinesMain, addStationsMain, fromToDest, decor1,
                font1, nav1);

        canvas = new ZoomPane();

        debugText = new Text();

        canvas.getChildren().add(debugText);
        debugText.setX(100);
        debugText.setY(100);

        dataManager = (mapData) app.getDataComponent();
        dataManager.setList(canvas.getChildren());

        workspace = new BorderPane();

        mainSpot = new Group();
        mainSpot.setOpacity(000);
        mainSpot.getChildren().add(canvas);
        outerCanvas = new ScrollPane(mainSpot);
        
        mainSpot.prefHeight(outerCanvas.getHeight());
        mainSpot.prefWidth(outerCanvas.getWidth());
        
        mainSpot.autosize();

        outerCanvas.setOpacity(0);

        outerCanvas = new ScrollPane();
        outerCanvas.setContent(canvas);
        outerCanvas.setHbarPolicy(ScrollBarPolicy.NEVER);
        outerCanvas.setVbarPolicy(ScrollBarPolicy.NEVER);


        ((BorderPane) workspace).setCenter(canvas);

        editToolbar.setTranslateY(editToolbar.getTranslateY() + 100);
        outerCanvas.setTranslateY(canvas.getTranslateY() + 100);

        ((BorderPane) workspace).setLeft(editToolbar);

    }

    private void initStyle() {
        // NOTE THAT EACH CLASS SHOULD CORRESPOND TO
        // A STYLE CLASS SPECIFIED IN THIS APPLICATION'S
        // CSS FILE
        canvas.getStyleClass().add(CLASS_RENDER_CANVAS);

        // COLOR PICKER STYLE
        lineColorPicker.getStyleClass().add(CLASS_BUTTON);
        stationColorPicker.getStyleClass().add(CLASS_BUTTON);
        backgroundColorPicker.getStyleClass().add(CLASS_BUTTON);
        fontColorPicker.getStyleClass().add(CLASS_BUTTON);
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

    boolean isBold, isItalic;

    private void initControllers() {
        mapEditController = new mapEditController(app);
        dataManager = (mapData) app.getDataComponent();

        isBold = false;
        isItalic = false;

        backgroundColorPicker.setOnAction(e -> {
            dataManager.setBackgroundColor(backgroundColorPicker.getValue());
        });

        fontColorPicker.setOnAction(e -> {
            if (dataManager.getSelectedShape() != null && dataManager.getSelectedShape() instanceof DraggableText) {
                mapEditController.processColorTextSelection((DraggableText) dataManager.getSelectedShape());
            }

        });

        stationColorPicker.setOnAction(e -> {
            if (dataManager.getSelectedShape() != null && dataManager.getSelectedShape() instanceof DraggableStation) {
                mapEditController.processStationColor((DraggableStation) dataManager.getSelectedShape());
            }

        });

        about.setOnAction(e -> {
            mapEditController.processAboutRequest();
        });

        export.setOnAction((e -> {
            mapEditController.processExportRequest();
        }));

        removeElement.setOnAction(e -> {

            if (dataManager.getSelectedShape() != null) {
                mapEditController.processRemoveElement();
            }

        });

        removeLine.setOnAction(e -> {
            if (dataManager.getSelectedShape() instanceof DraggableLine) {
                mapEditController.processRemoveLine((DraggableLine) dataManager.getSelectedShape());
            }

        });

        removeStat.setOnAction(e -> {
            if (dataManager.getSelectedShape() instanceof DraggableStation) {
                mapEditController.processRemoveStat((DraggableStation) dataManager.getSelectedShape());
            }

        });

        addImage.setOnAction(e -> {
            mapEditController.processImageOverlay();

        });

        imgBackground.setOnAction(e -> {
            mapEditController.processAddBackgroundImage();

        });

        editLine.setOnAction(e -> {

            if (dataManager.getSelectedShape() != null && dataManager.getSelectedShape() instanceof DraggableLine) {
                mapEditController.processEditLine(app, (DraggableLine) dataManager.getSelectedShape());
            }

        });
        addLabel.setOnAction(e -> {
            mapEditController.processAddLabel();
        });

        addLine.setOnAction(e -> {
            mapEditController.processAddLine();

        });

        addStat.setOnAction(e -> {
            mapEditController.processAddStation();
        });

        lineThickness.valueProperty().addListener(e -> {
            mapEditController.processLineThickness();

        });

        statThickness.valueProperty().addListener(e -> {

            if (dataManager.getSelectedShape() instanceof DraggableStation) {
                mapEditController.processStatThickness(((DraggableStation) dataManager.getSelectedShape()));
            }

        });

        addToLine.setOnAction(e -> {

            mapEditController.processAddStatToLine(); //TODO: Include some sort of identifier for this

        });

        removeFromLine.setOnAction(e -> {

            if (dataManager.getSelectedShape() != null && dataManager.getSelectedShape() instanceof DraggableStation) {
                mapEditController.processRemoveStatFromLine((DraggableStation) dataManager.getSelectedShape()); //TODO: Look Up
            }
        });

        lines.setOnAction(e -> {

            if (dataManager.getSelectedShape() != null) {
                dataManager.unhighlightShape((Node) dataManager.getSelectedShape());
            }

            String name = lines.getSelectionModel().getSelectedItem();

            if (name != null) {
                getCanvas().getChildren().stream().filter((item) -> (item instanceof DraggableLine)).forEachOrdered((item) -> {
                    DraggableLine drag = (DraggableLine) item;
                    if (drag.getName().equals(name)) {
                        dataManager.highlightShape(item);
                        dataManager.setSelectedShape(item);
                    }
                });
            }

        });

        undo.setOnAction(e -> {

            dataManager = (mapData) app.getDataComponent();

            dataManager.getTransact().undoTransaction();

            check();

            redo.setDisable(false);

            app.getGUI().updateToolbarControls(false);

        });

        redo.setOnAction(e -> {

            dataManager = (mapData) app.getDataComponent();

            dataManager.getTransact().doTransaction();

            check();

            app.getGUI().updateToolbarControls(false);

        });

        stations.setOnAction(e -> {
            if (dataManager.getSelectedShape() != null) {
                dataManager.unhighlightShape((Node) dataManager.getSelectedShape());
            }
            String name = stations.getSelectionModel().getSelectedItem();

            if (name != null) {
                getCanvas().getChildren().stream().filter((item) -> (item instanceof DraggableStation)).forEachOrdered((item) -> {
                    DraggableStation drag = (DraggableStation) item;
                    if (drag.getName().equals(name)) {
                        dataManager.highlightShape(item);
                        dataManager.setSelectedShape(item);
                    }
                });
            }

        });

        canvas.setOnMouseClicked(e -> {

            canvasController.processCanvasMousPressed((int) e.getX(), (int) e.getY());

        });

        canvas.setOnMouseReleased(e -> {

            canvasController.processCanvasMouseRelease((int) e.getX(), (int) e.getY());

        });
        outerCanvas.setOnMouseDragged(e -> {

            canvasController.processCanvasMouseDragged((int) e.getX(), (int) e.getY());

        });

        details.setOnAction(e -> {

            if (lines.getSelectionModel().getSelectedItem() != null) {

                for (Node n : canvas.getChildren()) {
                    if (n instanceof DraggableLine) {
                        if (((DraggableLine) n).getName().equals(lines.getSelectionModel().getSelectedItem())) {
                            mapEditController.listStationsOnLine((DraggableLine) n);
                            break;
                        }
                    }
                }

            }
        });

        rotate.setOnAction(e -> {
            mapEditController.rotateText();
        });

//        fromToPop.setOnAction(e -> {
//            if (fromStat.getSelectionModel().getSelectedItem() != null && toStat.getSelectionModel().getSelectedItem() != null) {
//
//                DraggableStation toStat1 = null, fromStat1 = null;
//
//                for (Node n : canvas.getChildren()) {
//                    if (n instanceof DraggableStation) {
//                        DraggableStation drag = (DraggableStation) n;
//
//                        if (drag.getName().equals(fromStat.getSelectionModel().getSelectedItem())) {
//                            toStat1 = drag;
//                            break;
//                        }
//                    }
//                }
//
//                for (Node n : canvas.getChildren()) {
//                    if (n instanceof DraggableStation) {
//                        DraggableStation drag = (DraggableStation) n;
//
//                        if (drag.getName().equals(toStat.getSelectionModel().getSelectedItem())) {
//                            fromStat1 = drag;
//                            break;
//                        }
//                    }
//                }
//
//                mapEditController.processDirections(toStat1, fromStat1);
//            }
//
//        });
        snapToGrid.setOnAction(e -> {
            mapEditController.processSnapToGrid();

        });

        showGrid.setOnAction(e -> {

            if (showGrid.isSelected()) {
                mapEditController.showGrid();
            } else {
                mapEditController.hideGrid();
            }
        });

        zoomIn.setOnAction(e -> canvasController.zoomIn());

        zoomOut.setOnAction(e -> canvasController.zoomOut());
//
//        increaseMapSize.setOnAction(e -> {
//            canvasController.increaseMapSize();
//
//        });
//
//        decreaseMapSize.setOnAction(e -> {
//            canvasController.decreaseMapSize();
//        });
        fontFamilies.setOnAction(e -> {
            Node node = (Node) dataManager.getSelectedShape();

            if (node != null & node instanceof DraggableText) {

                String prevFontChoice = ((DraggableText) node).getFont().getFamily();
                String s = fontFamilies.getSelectionModel().getSelectedItem();

                if (s != null) {

                    Font f = ((DraggableText) node).getFont();
                    Color fill = (Color) ((DraggableText) node).getFill();

                    ((DraggableText) node).setFont(Font.font(s, isBold ? FontWeight.BOLD : FontWeight.NORMAL, isItalic ? FontPosture.ITALIC : FontPosture.REGULAR, ((DraggableText) node).getFont().getSize()));
                    String curFontChoice = ((DraggableText) node).getFont().getFamily();

                    Font f2 = ((DraggableText) node).getFont();
                    Color fill2 = (Color) ((DraggableText) node).getFill();

                    transaction = new TextFontColorContent(app, ((DraggableText) node), fill, fill2, f, f2);
                    dataManager.getTransact().addTransaction(transaction);

                }

            }

        });

        canvas.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W:

                    canvas.setLayoutY(canvas.getLayoutY() + 10);
                    break;
                case D:
                    canvas.setLayoutX(canvas.getLayoutX() + 10);
                    break;
                case A:
                    canvas.setLayoutX(canvas.getLayoutX() - 10);
                    break;
                case S:
                    canvas.setLayoutY(canvas.getLayoutY() - 10);
                    break;
                default:
                    break;
            }
        });

        fontSizes.setOnAction(e -> {
            dataManager = (mapData) app.getDataComponent();
            Node node = (Node) dataManager.getSelectedShape();

            if (node != null & node instanceof DraggableText) {
                double prevFont = ((DraggableText) node).getFont().getSize();

                int i = fontSizes.getSelectionModel().getSelectedItem();

                Font f = ((DraggableText) node).getFont();
                Color fill = (Color) ((DraggableText) node).getFill();

                ((DraggableText) node).setFont(Font.font(((DraggableText) node).getFont().getFamily(), isBold ? FontWeight.BOLD : FontWeight.NORMAL, isItalic ? FontPosture.ITALIC : FontPosture.REGULAR, i));

                Font f2 = ((DraggableText) node).getFont();
                Color fill2 = (Color) ((DraggableText) node).getFill();

                transaction = new TextFontColorContent(app, ((DraggableText) node), fill, fill2, f, f2);
                dataManager.getTransact().addTransaction(transaction);

                double curFont = ((DraggableText) node).getFont().getSize();

            }

        });

        //EventHandler<ActionEvent> for Bolding and Italicizing
        //Also several Fonts to go with it
        EventHandler<ActionEvent> fontHandler = e -> {

            Node node = (Node) dataManager.getSelectedShape();

            if (node != null && node instanceof DraggableText) {
                DraggableText text = (DraggableText) node;
                if (bold.isSelected() && italicize.isSelected()) {
                    isBold = true;
                    isItalic = true;

                    text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, FontPosture.ITALIC, text.getFont().getSize())); // Both check boxes checked
                } else if (bold.isSelected()) {
                    isBold = true;
                    isItalic = false;

                    text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, text.getFont().getSize()));
                } else if (italicize.isSelected()) {
                    isBold = false;
                    isItalic = true;

                    text.setFont(Font.font(text.getFont().getFamily(), FontWeight.NORMAL, FontPosture.ITALIC, text.getFont().getSize()));
                } else {
                    isItalic = false;
                    isBold = false;
                    text.setFont(Font.font(text.getFont().getFamily(), FontWeight.NORMAL, FontPosture.REGULAR, text.getFont().getSize()));
                }
            }
        };

        bold.setOnAction(fontHandler);
        italicize.setOnAction(fontHandler);
    }

    void check() {
        if (dataManager.getTransact().getMostRecentTransaction() == -1) {
            undo.setDisable(true);
        } else {
            undo.setDisable(false);
        }

        if (dataManager.getTransact().getMostRecentTransaction() == dataManager.getTransact().getTransactions().size() - 1) {
            redo.setDisable(true);
        } else {
            redo.setDisable(false);
        }

    }

    public CheckBox getShowGrid() {
        return showGrid;
    }

    public GridPane getGp() {
        return gp;
    }

    public Slider getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(Slider lineThickness) {
        this.lineThickness = lineThickness;
    }

    public Slider getStatThickness() {
        return statThickness;
    }

    public void setStatThickness(Slider statThickness) {
        this.statThickness = statThickness;
    }

    public ColorPicker getFontColorPicker() {
        return fontColorPicker;
    }

    public void setFontColorPicker(ColorPicker fontColorPicker) {
        this.fontColorPicker = fontColorPicker;
    }

    public mapEditController getMapEditController() {
        return mapEditController;
    }

    public void setMapEditController(mapEditController mapEditController) {
        this.mapEditController = mapEditController;
    }

// HELPER METHOD
    public void loadSelectedShapeSettings(Node shape) {
        if (shape != null) {
            Color strokeColor = (Color) ((Shape) shape).getStroke();
            lineColorPicker.setValue(strokeColor);
        }
    }

    public ScrollPane getOuterCanvas() {
        return outerCanvas;
    }

    public void setOuterCanvas(ScrollPane outerCanvas) {
        this.outerCanvas = outerCanvas;
    }

    public ZoomPane getCanvas() {
        return canvas;
    }

}
