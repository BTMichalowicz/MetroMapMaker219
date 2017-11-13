package map.gui;

import djf.AppTemplate;

import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_EXPORTS;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;
import map.data.mapData;
import map.data.mapState;
import map.file.mapFiles;

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
                file = new File(result.get() + ".png");

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

}
