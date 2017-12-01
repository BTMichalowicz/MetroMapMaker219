package djf.ui;

import djf.AppTemplate;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import static djf.ui.AppGUI.CLASS_FILE_BUTTON;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * apimport javafx.scene.control.TextInputDialog; plication
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

    ArrayList<File> recentWorks = new ArrayList<>();

    public ArrayList<File> getRecentFiles() {
        return recentWorks;
    }

    public void setRecentFiles(ArrayList<File> recentFiles) {
        this.recentWorks = recentFiles;
    }

    //Because it's a singleton
    private AppWelcomeDialogSingleton(AppTemplate app) {

        if (single == null) {
            this.app = app;
            initLayout();
            initData();
            initControllers();
        } else {
            initLayout();
            initData();
            initControllers();
        }

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
        getIcons().add(i);

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
                // RESET THE WORKSPACE
                app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                app.getFileComponent().loadData(app.getDataComponent(), "C:\\Users\\Ben Michalowicz\\Desktop\\M3\\MetroMapMaker\\work\\" + (file1.getText().split("\\.")[0]) + "\\" + file1.getText());

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                app.getGUI().updateToolbarControls(true);

                close();
                app.getGUI().getPrimaryStage().show();
            } catch (Exception e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This file has not either been created or has not been given the proper attributes yet!");
                a.setTitle("Something Went Wrong");
                a.showAndWait();

            }

        });
        file2.setOnAction(e -> {

            try {
                // RESET THE WORKSPACE
                app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                app.getFileComponent().loadData(app.getDataComponent(), "C:\\Users\\Ben Michalowicz\\Desktop\\M3\\MetroMapMaker\\work\\" + (file2.getText().split("\\.")[0]) + "\\" + file2.getText());

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                app.getGUI().updateToolbarControls(true);

                close();
                app.getGUI().getPrimaryStage().show();
            } catch (Exception e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This file has not either been created or has not been given the proper attributes yet!");
                a.setTitle("Something Went Wrong");
                a.showAndWait();

            }

        });
        file3.setOnAction(e -> {

            try {
                // RESET THE WORKSPACE
                app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                app.getFileComponent().loadData(app.getDataComponent(), "C:\\Users\\Ben Michalowicz\\Desktop\\M3\\MetroMapMaker\\work\\" + (file3.getText().split("\\.")[0]) + "\\" + file3.getText());

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                app.getGUI().updateToolbarControls(true);

                close();
                app.getGUI().getPrimaryStage().show();
            } catch (Exception e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This file has not either been created or has not been given the proper attributes yet!");
                a.setTitle("Something Went Wrong");
                a.showAndWait();
            }

        });
        file4.setOnAction(e -> {

            try {

                // RESET THE WORKSPACE
                app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                app.getFileComponent().loadData(app.getDataComponent(), "C:\\Users\\Ben Michalowicz\\Desktop\\M3\\MetroMapMaker\\work\\" + (file4.getText().split("\\.")[0]) + "\\" + file4.getText());

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                app.getGUI().updateToolbarControls(true);

                close();
                app.getGUI().getPrimaryStage().show();
            } catch (Exception e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This file has not either been created or has not been given the proper attributes yet!");
                a.setTitle("Something Went Wrong");
                a.showAndWait();

            }

        });
        file5.setOnAction(e -> {

            try {

                // RESET THE WORKSPACE
                app.getWorkspaceComponent().resetWorkspace();

                // RESET THE DATA
                app.getDataComponent().resetData();
                app.getFileComponent().loadData(app.getDataComponent(), "C:\\Users\\Ben Michalowicz\\Desktop\\M3\\MetroMapMaker\\work\\" + (file5.getText().split("\\.")[0]) + "\\" + file5.getText());

                // MAKE SURE THE WORKSPACE IS ACTIVATED
                app.getWorkspaceComponent().activateWorkspace(app.getGUI().getAppPane());

                // AND MAKE SURE THE FILE BUTTONS ARE PROPERLY ENABLED
                app.getGUI().updateToolbarControls(true);

                close();
                app.getGUI().getPrimaryStage().show();

            } catch (Exception e2) {
                Alert a = new Alert(AlertType.INFORMATION);
                a.setHeaderText(null);
                a.setContentText("This file has not either been created or has not been given the proper attributes yet!");
                a.setTitle("Something Went Wrong");
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

        recentWorks = getWorkDir(new File(PATH_WORK), new ArrayList<>()); //Attempt to get all files recursively

        sortFiles(recentWorks);

        renameLinks(recentWorks);

    }

    private void sortFiles(ArrayList<File> recentWorks) {

        FileComparator fileC = new FileComparator();
        Collections.sort(recentWorks, fileC);
    }

    class FileComparator implements Comparator<File> {

        @Override
        public int compare(File t, File t1) {
            return (int) (t1.lastModified() - t.lastModified());
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

    private ArrayList<File> getWorkDir(File f, ArrayList<File> helper) {
        File[] all = f.listFiles();

        for (File file : all) {
            if (file.isFile()) {
                helper.add(file);
            } else if (file.isDirectory()) {
                getWorkDir(file, helper);
            }
        }
        return helper;
    }

}
