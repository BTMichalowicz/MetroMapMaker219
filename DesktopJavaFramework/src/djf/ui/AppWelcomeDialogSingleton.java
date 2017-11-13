/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package djf.ui;

import djf.AppTemplate;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Pos;
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

    AppTemplate app;

    Label l;

    List<String> recentFiles = new ArrayList<>();

    public List<String> getRecentFiles() {
        return recentFiles;
    }

    public void setRecentFiles(List<String> recentFiles) {
        this.recentFiles = recentFiles;
    }

    //Because it's a singleton
    private AppWelcomeDialogSingleton(AppTemplate app) {
        this.app = app;
        initLayout();
        initData(); //TODO: For the future
        initControllers();

    } //Empty Constructor for the sake of stuff

    private void initLayout() {
        //Set up the containers
        centerStage = new VBox(25);
        hyperLinkList = new VBox(15);
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
        file1 = new Hyperlink("");

        file2 = new Hyperlink("");

        file3 = new Hyperlink("");

        file4 = new Hyperlink("");

        file5 = new Hyperlink("");
        
        Label recentFiles = new Label("Recent Files");

        hyperLinkList.getChildren().addAll(recentFiles, file1, file2, file3, file4, file5);
        
        

        welcomePane.setLeft(hyperLinkList);
        welcomePane.setCenter(centerStage);

        welcomeScene = new Scene(welcomePane, 1000, 500);
        setScene(welcomeScene);

        newButton.getStyleClass().add(CLASS_FILE_BUTTON);
        centerStage.setAlignment(Pos.CENTER);
        welcomePane.setStyle("-fx-background-color: #E65540");
        centerStage.setStyle("-fx-font-size: 18px; -fx-background-color: #65A8A6;");
        hyperLinkList.setStyle("-fx-font-size: 16px; -fx-background-color:#F8ECC2;");

    }

    public Hyperlink getFile1() {
        return file1;
    }

    public void setFile1(Hyperlink file1) {
        this.file1 = file1;
    }

    public Hyperlink getFile2() {
        return file2;
    }

    public void setFile2(Hyperlink file2) {
        this.file2 = file2;
    }

    public Hyperlink getFile3() {
        return file3;
    }

    public void setFile3(Hyperlink file3) {
        this.file3 = file3;
    }

    public Hyperlink getFile4() {
        return file4;
    }

    public void setFile4(Hyperlink file4) {
        this.file4 = file4;
    }

    public Hyperlink getFile5() {
        return file5;
    }

    public void setFile5(Hyperlink file5) {
        this.file5 = file5;
    }

    private void initControllers() {
        file1.setOnAction(e -> {

            try {
                app.getFileComponent().loadData(app.getDataComponent(), PATH_WORK+file1.getText());
            } catch (IOException e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This action is not yet supported! Please come back later!");
                a.setTitle("Not Supported Yet!");
                a.showAndWait();

            }

        });
        file2.setOnAction(e -> {

            try {
                app.getFileComponent().loadData(app.getDataComponent(), PATH_WORK + file2.getText());
            } catch (IOException e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This action is not yet supported! Please come back later!");
                a.setTitle("Not Supported Yet!");
                a.showAndWait();

            }

        });
        file3.setOnAction(e -> {

           try {
                app.getFileComponent().loadData(app.getDataComponent(), PATH_WORK + file3.getText());
            } catch (IOException e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This action is not yet supported! Please come back later!");
                a.setTitle("Not Supported Yet!");
                a.showAndWait();

            }

        });
        file4.setOnAction(e -> {

           try {
                app.getFileComponent().loadData(app.getDataComponent(), PATH_WORK + file4.getText());
            } catch (IOException e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This action is not yet supported! Please come back later!");
                a.setTitle("Not Supported Yet!");
                a.showAndWait();

            }

        });
        file5.setOnAction(e -> {

             try {
                app.getFileComponent().loadData(app.getDataComponent(), file5.getText());
            } catch (IOException e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This action is not yet supported! Please come back later!");
                a.setTitle("Not Supported Yet!");
                a.showAndWait();

            }

        });

    }

    public static AppWelcomeDialogSingleton getAppWelcomeDialog(AppTemplate app) {
        if (single == null) {
            single = new AppWelcomeDialogSingleton(app);
            return single;
        } else {
            return single;
        }
    }

    public Button getNewButton() {
        return newButton;
    }

    private void initData() {
        //AppMessageDialogSingleton.getSingleton().show("Not supported Yet", "This action isn't supported yet; check back soon!");

        //TODO: LOAD THIS SHIT
        // Read from the work folder,
        //get all the files, sort them by time last modified
        ArrayList<File> recentWorks = new ArrayList<>();

        File directory = new File(PATH_WORK);

        recentWorks.addAll(Arrays.asList(directory.listFiles()));

        //SORT THE FILES BASED ON RECENT WORKS: BubbleSort
        sortFiles(recentWorks);

        renameLinks(recentWorks);

    }

    private void sortFiles(ArrayList<File> recentWorks) {
        for (int idx = 0; idx < recentWorks.size() - 1; idx++) {
            for (int j = 0; j < recentWorks.size() - idx - 1; idx++) {
                if (recentWorks.get(j).lastModified() > recentWorks.get(j + 1).lastModified()) {
                    File temp = recentWorks.get(j);
                    recentWorks.set(j, recentWorks.get(j + 1));
                    recentWorks.set(j + 1, temp);
                }
            }
        }
    }

    private void renameLinks(ArrayList<File> recentWorks) {

        if (recentWorks.size() > 0 && recentWorks.get(0) != null) {
            file1.setText(recentWorks.get(0).getName());
        } else {

        }
        if (recentWorks.size() > 1 && recentWorks.get(1) != null) {
            file2.setText(recentWorks.get(1).getName());
        } else {

        }
        if (recentWorks.size() > 2 && recentWorks.get(2) != null) {
            file3.setText(recentWorks.get(2).getName());
        } else {

        }
        if (recentWorks.size() > 3 && recentWorks.get(3) != null) {
            file4.setText(recentWorks.get(3).getName());
        } else {

        }
        if (recentWorks.size() > 4 && recentWorks.get(4) != null) {
            file5.setText(recentWorks.get(4).getName());
        } else {

        }
    }

}
