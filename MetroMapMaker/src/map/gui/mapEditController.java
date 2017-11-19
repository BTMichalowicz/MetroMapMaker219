package map.gui;

import djf.AppTemplate;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_EXPORTS;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
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
public class MapEditController {

    public AppTemplate app;
    public mapData dataManager;

    public MapEditController(AppTemplate app) {
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
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
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
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
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
            if (result.isPresent())
                makeImage(file, i, result, image); //method to take a snapshot of the canvas
            
            /*
            Once the fule has been exported as some sort of PNG image, then we go to exporting the data
            */
            //fileControl.exportData(dataManager, PATH_EXPORTS);
            

        } catch (IOException ioe) {
           ioe.printStackTrace();
        }

            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setTitle("Export Map");
            a.setHeaderText(null);
            a.setContentText("Export is Completed");
            a.showAndWait();
      
    }
    
    
    private void makeImage(File file, int i, Optional<String> result, WritableImage image) throws IOException{
        if (result.isPresent()) {
                file = new File(PATH_EXPORTS + result.get() + ".png");

                if (file.exists()) {
                    // If the file exists already, then just save it with a given number after it

                    file = new File(result.get() + "(" + i + ").png");
                    //If the file exists with the given number, keep going until the file (x(number).png) no longer exists
                    while (file.exists()) {
                        i++;
                        file = new File(result.get() + "(" + i + ").png");
                    }
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(PATH_EXPORTS + file)); //Only save the file
                                                                                       //After the loop has finished

                } else {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", new File(PATH_EXPORTS + file));
                }
            }
            
    }

    /**
     * Used to generically remove elements from the canvas and whatnot;
     */
    public void processRemoveElement() {
        dataManager.removeSelectedItem();
        
        // ENABLE/DISABLE THE PROPER BUTTONS
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
        app.getGUI().updateToolbarControls(false);
    }
    
    
    /**
     * This method removes a line as well as all of its stations. 
     * @param selectedLine The selected Draggable Line in question
     */

    public void processRemoveLine(DraggableLine selectedLine) {
        selectedLine.getStations().forEach((d) -> {
            selectedLine.getStations().remove(d);
        }); 
        
        processRemoveElement();
    }

    
    /**
     * This method will remove any given station that is not directly associated with a line.
     * @param draggableStation The draggable station in question.
     */
    public void processRemoveStat(DraggableStation draggableStation) {
        ((MapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().remove(draggableStation);
            draggableStation = null;
    }

    public void processImageOverlay() {
        // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_OVERLAY);

        // ENABLE/DISABLE THE PROPER BUTTONS
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    
    /**
     * For adding a background image in lieu of colors
     */
    public void processAddBackgroundImage() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
        Scene sc = app.getGUI().getPrimaryScene();
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fc.setInitialDirectory(new File(PATH_IMAGES));
        fc.setTitle(props.getProperty("Load an Image"));

        File selectedFile = fc.showOpenDialog(null);
        
        ((mapData)app.getDataComponent()).setState(mapState.STARTING_BCKGROUND);
        
        try{
        URL fileU = selectedFile.toURI().toURL();

        Image image = new Image(fileU.toExternalForm());
        
        ((MapWorkspace) app.getWorkspaceComponent()).getCanvas().setBackground(new Background(new BackgroundFill(new ImagePattern(image), null, null)));

        
        }catch(MalformedURLException muee){
            
            AppMessageDialogSingleton.getSingleton().show("Loading image error","There was an error loading the image as follows" + Arrays.toString(muee.getStackTrace()));
            return;
        }

    
    }

    public void processAddLabel() {
         // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_TEXT);

        // ENABLE/DISABLE THE PROPER BUTTONS
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
        
    }

    void processAddLine() {
         // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_LINE);

        // ENABLE/DISABLE THE PROPER BUTTONS
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

    void processAddStation() {
         // CHANGE THE CURSOR
        Scene scene = app.getGUI().getPrimaryScene();
        scene.setCursor(Cursor.CROSSHAIR);

        // CHANGE THE STATE
        dataManager.setState(mapState.STARTING_STATION);

        // ENABLE/DISABLE THE PROPER BUTTONS
        MapWorkspace workspace = (MapWorkspace) app.getWorkspaceComponent();
        workspace.reloadWorkspace(dataManager);
    }

}
