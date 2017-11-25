package map.gui;

import djf.AppTemplate;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_EXPORTS;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppMessageDialogSingleton;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import map.data.DraggableLine;
import map.data.DraggableStation;
import map.data.DraggableText;
import map.data.ListStationsWindow;
import map.data.mapData;
import map.data.mapState;
import map.file.mapFiles;
import properties_manager.PropertiesManager;

/**
 * This class responds to interactions with the UI's editing controls. Hopefully
 * this won't end up overly bad or anything.
 *
 * @author Ben Michalowicz
 */
public class mapEditController {

    public AppTemplate app;
    public mapData dataManager;

    public mapEditController(AppTemplate app) {
        this.app = app;
        this.dataManager = (mapData) app.getDataComponent();
    }

    public void processSelectSelectionTool() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.DEFAULT);

        // CHANGE THE STATE
        dataManager.setState(mapState.SELECTING);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    public void processAboutRequest() {

        String s;

        Alert aboutAlert = new Alert(AlertType.INFORMATION);
        aboutAlert.setTitle("About the app and its developer:");
        aboutAlert.setHeaderText(null);
        aboutAlert.setGraphic(new ImageView(FILE_PROTOCOL + PATH_IMAGES + "M3Logo.PNG"));

        s = "Welcome to Metro Map Maker!\n\nThrough this application, you will be able to view maps of real-life metro stations and create your own custom maps!\n"
                + "This application was designed and developed by Benjamin Michalowicz, a student in the Computer Science Department at "
                + "Stony Brook University from October to December, 2017 using the Java programming language."
                + "\n\nEnjoy!\n\n"
                + "\u00a9 Benjamin Michalowicz, 2017";

        DialogPane d = aboutAlert.getDialogPane();

        aboutAlert.setContentText(s);

        d.setStyle("-fx-font-size: 16px;");
        aboutAlert.setDialogPane(d);

        aboutAlert.show();

    }

    void processExportRequest() {

        dataManager = (mapData) app.getDataComponent();
        mapFiles fileControl = (mapFiles) app.getFileComponent();
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        Pane canvas = workspace.getCanvas();
        WritableImage image = canvas.snapshot(new SnapshotParameters(), null);

        TextInputDialog fileName = new TextInputDialog("Enter the name of your map here");
        fileName.setTitle("Export your map!");
        fileName.setHeaderText(null);
        fileName.setContentText("What's the name of your awesome map?");

        Optional<String> result = fileName.showAndWait();

        int i = 1; //for use in writing
        File file = null;

        //Writing the file
        try {
            if (result.isPresent()) {
                makeImage(file, i, result, image); //method to take a snapshot of the canvas
            } else {
                return;
            }
            /*
            Once the fule has been exported as some sort of PNG image, then we go to exporting the data
             */
            fileControl.exportData(dataManager, PATH_EXPORTS + result.get() + ".m3");

        } catch (IOException ioe) {

        }

        Alert a = new Alert(AlertType.INFORMATION);
        a.setHeaderText(null);
        a.setTitle("Export Map");
        a.setHeaderText(null);
        a.setContentText("Export is Completed");
        a.showAndWait();

    }

    private void makeImage(File file, int i, Optional<String> result, WritableImage image) throws IOException {
        if (result.isPresent()) {
            file = new File(PATH_EXPORTS + result.get() + ".png");

            if (file.exists()) {
                // If the file exists already, then just save it with a given number after it

                file = new File(PATH_EXPORTS + result.get() + "(" + i + ").png");
                //If the file exists with the given number, keep going until the file (x(number).png) no longer exists
                while (file.exists()) {
                    i++;
                    file = new File(PATH_EXPORTS + result.get() + "(" + i + ").png");
                }
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file); //Only save the file
                //After the loop has finished

            } else {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            }
        }

    }

    /**
     * Used to generically remove elements from the canvas and whatnot;
     */
    public void processRemoveElement() {
        dataManager.removeSelectedItem();

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
        app.getGUI().updateToolbarControls(false);

    }

    /**
     * This method removes a line as well as all of its stations.
     *
     * @param selectedLine The selected Draggable Line in question
     */
    public void processRemoveLine(DraggableLine selectedLine) {

        selectedLine.getStations().forEach((d) -> {
            selectedLine.getStations().remove(d);
            for (Node item : ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren()) {
                if (item instanceof DraggableStation) {
                    DraggableStation s = (DraggableStation) item;
                    if (s.getName().equals(d)) {
                        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().remove(s);
                        ((mapWorkspace) app.getWorkspaceComponent()).getStations().getItems().remove(d);
                        ((mapWorkspace) app.getWorkspaceComponent()).getFromStat().getItems().remove(d);
                        ((mapWorkspace) app.getWorkspaceComponent()).getToStat().getItems().remove(d);
                        break;
                    }
                }
            }

        });

        ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().removeAll(selectedLine.getEndName(), selectedLine.getStartName());

        processRemoveElement();
    }

    /**
     * This method will remove any given station that is not directly associated
     * with a line, and it should do this with lines as well.
     *
     * @param draggableStation The draggable station in question.
     */
    public void processRemoveStat(DraggableStation draggableStation) {
        
        
        mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();
        (work).getCanvas().getChildren().remove(draggableStation);
        (work).getCanvas().getChildren().remove(draggableStation.getStatName());
        (work).getStations().getItems().remove(draggableStation.getName());
        work.getFromStat().getItems().remove(draggableStation.getName());
        work.getToStat().getItems().remove(draggableStation.getName());
        
        for(Node n: work.getCanvas().getChildren()){
            
            if(n instanceof DraggableLine){
                DraggableLine node = (DraggableLine) n;
                
                if(node.getStations().contains(draggableStation.getName())){
                    node.getStations().remove(draggableStation.getName());
                }
            }
        }
        // draggableStation = null; // Remove the reference
    }

    public void processImageOverlay() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_OVERLAY);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    /**
     * For adding a background image in lieu of colors
     */
    public void processAddBackgroundImage() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        Scene sc = app.getGUI().getPrimaryScene();
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fc.setInitialDirectory(new File(PATH_IMAGES));
        fc.setTitle(props.getProperty("Load an Image"));

        File selectedFile = fc.showOpenDialog(null);

        ((mapData) app.getDataComponent()).setState(mapState.STARTING_BCKGROUND);

        try {
            URL fileU = selectedFile.toURI().toURL();

            Image image = new Image(fileU.toExternalForm());

            workspace.getCanvas().setBackground(new Background(new BackgroundFill(new ImagePattern(image), null, null)));

        } catch (MalformedURLException muee) {

            AppMessageDialogSingleton.getSingleton().show("Loading image error", "There was an error loading the image as follows" + Arrays.toString(muee.getStackTrace()));

        }

    }

    public void processAddLabel() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_TEXT);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);

    }

    public void processAddLine() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_LINE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    public void processAddStation() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_STATION);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    public void processAddStatToLine() {

        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.ADD_STAT_TO_LINE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);

    }

    /**
     * This method is substantially easier to code than its adding counterpart
     *
     * @param removedStation
     */
    public void processRemoveStatFromLine(DraggableStation removedStation) {

        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();

        for(Node n: workspace.getCanvas().getChildren()){
            
            if(n instanceof DraggableLine){
                DraggableLine node = (DraggableLine) n;
                
                if(node.getStations().contains(removedStation.getName())){
                    node.getStations().remove(removedStation.getName());
                }
            }
        }
                

        //Just in case


    }

    public void processEditLine(AppTemplate app, DraggableLine line) {

        EditLineTool editor = new EditLineTool(app, line);

        editor.showAndWait();

        ((mapWorkspace) app.getWorkspaceComponent()).reloadWorkspace(app.getDataComponent());
    }

    void processLineThickness() {
        mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();

        dataManager.setCurrentOutlineThickness((int) work.getLineThickness().getValue());
        app.getGUI().updateToolbarControls(false);
    }

    void processStatThickness(DraggableStation draggableStation) {

        mapWorkspace work = (mapWorkspace) app.getWorkspaceComponent();

        draggableStation.setRadiusX(work.getStatThickness().getValue() / 2);
        draggableStation.setRadiusY(work.getStatThickness().getValue() / 2);
        app.getGUI().updateToolbarControls(false);

    }

    void processStationColor(DraggableStation draggableStation) {
        ColorPicker stationColor = ((mapWorkspace) app.getWorkspaceComponent()).getStationColorPicker();

        draggableStation.setFill(stationColor.getValue());

    }

    void processColorTextSelection(DraggableText draggableText) {
        ColorPicker textColor = ((mapWorkspace) app.getWorkspaceComponent()).getFontColorPicker();

        draggableText.setFill(textColor.getValue());
    }

    void rotateText() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.ROTATING_LABEL);

        // ENABLE/DISABLE THE PROPER BUTTONS
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }
    

    void listStationsOnLine(DraggableLine draggableLine) {
        
        ListStationsWindow lister = new ListStationsWindow (app, draggableLine);
        
        lister.show();
        
    }

}
