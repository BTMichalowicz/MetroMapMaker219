/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package djf.ui;


import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * This class serves to present a dialog that welcomes the user into the
 * application
 *
 * @author Ben Michalowicz
 */
public class AppWelcomeDialogSingleton extends Stage {

    private static AppWelcomeDialogSingleton single = null;
    VBox centerStage;
    VBox hyperLinkList;
    Scene welcomeScene;
    BorderPane welcomePane;

    Hyperlink file1, file2, file3, file4, file5;

    ImageView img;
    Image i;

    Button newButton;

    Label l;
    
    

    List<String> recentFiles = new ArrayList<>();
   

    public List<String> getRecentFiles() {
        return recentFiles;
    }

    public void setRecentFiles(List<String> recentFiles) {
        this.recentFiles = recentFiles;
    }

    //Because it's a singleton
    private AppWelcomeDialogSingleton() {
      
        initLayout();
        initControllers();
        //initData(); //TODO: For the future

    } //Empty Constructor for the sake of stuff

    private void initLayout() {
        //Set up the containers
        centerStage = new VBox(25);
        hyperLinkList = new VBox(10);
        welcomePane = new BorderPane();
        

        //Image
        i = new Image(FILE_PROTOCOL + PATH_IMAGES + "M3Logo.png");
        img = new ImageView(i);

        //Label
        l = new Label();
        l.setText("Welcome to MetroMapMaker!");

        //Button
        newButton = new Button("Create a new MetroMap");
        //Add it all together
        centerStage.getChildren().addAll(img, l, newButton);

        //Hyperlinks: Meant to point to different sets of data (recent maps and stuff)
        file1 = new Hyperlink("TODO: Get the file setup working!");

        file2 = new Hyperlink("TODO: Get the file setup working!");

        file3 = new Hyperlink("TODO: Get the file setup working!");

        file4 = new Hyperlink("TODO: Get the file setup working!");

        file5 = new Hyperlink("TODO: Get the file setup working!");

        hyperLinkList.getChildren().addAll(file1, file2, file3, file4, file5);

        welcomePane.setLeft(hyperLinkList);
        welcomePane.setCenter(centerStage);

        welcomeScene = new Scene(welcomePane, 1000, 500);
        setScene(welcomeScene);

    }
    
   
    private void initControllers() {
        file1.setOnAction(e -> {
            
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("This action is not yet supported! Please come back later!");
            a.setTitle("Not Supported Yet!");
            a.showAndWait();
            
        });
        file2.setOnAction(e -> {
            
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("This action is not yet supported! Please come back later!");
            a.setTitle("Not Supported Yet!");
            a.showAndWait();
            
        });
        file3.setOnAction(e -> {
            
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("This action is not yet supported! Please come back later!");
            a.setTitle("Not Supported Yet!");
            a.showAndWait();
            
        });
        file4.setOnAction(e -> {
            
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("This action is not yet supported! Please come back later!");
            a.setTitle("Not Supported Yet!");
            a.showAndWait();
            
        });
        file5.setOnAction(e -> {
            
            Alert a = new Alert(AlertType.INFORMATION);
            a.setHeaderText(null);
            a.setContentText("This action is not yet supported! Please come back later!");
            a.setTitle("Not Supported Yet!");
            a.showAndWait();
            
        });

       
        
        setOnCloseRequest(e-> System.exit(0));

    }

    public static AppWelcomeDialogSingleton getAppWelcomeDialog() {
        if (single == null) {
            single = new AppWelcomeDialogSingleton();
            return single;
        } else {
            return single;
        }
    }

    public Button getNewButton() {
        return newButton;
    }

  
    private void initData(){
         AppMessageDialogSingleton.getSingleton().show("Not supported Yet", "This action isn't supported yet; check back soon!");
    }

}
