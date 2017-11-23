/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import djf.AppTemplate;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import map.gui.mapWorkspace;
import properties_manager.PropertiesManager;

/**
 * Image Overlay Object for the project. Please let all of this work
 *
 * @author Ben Michalowicz
 */
public class DraggableImage extends Rectangle implements Draggable {

    String filePath;
    double startX, startY;

    Image img;

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
    AppTemplate app;
    ImagePattern imgPattern;

    public DraggableImage(AppTemplate app) {
        this.app = app;

      

        

        

    }

    public Image getNewImage() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        Scene sc = app.getGUI().getPrimaryScene();
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fc.setInitialDirectory(new File(PATH_IMAGES));
        fc.setTitle(props.getProperty("Load an Image"));

        File selectedFile = fc.showOpenDialog(null);

        ((mapData) app.getDataComponent()).setState(mapState.DRAGGING);
        
        if(selectedFile!= null){

        try {
            URL fileU = selectedFile.toURI().toURL();

            Image image = new Image(fileU.toExternalForm());

            filePath = selectedFile.getPath();
            imgPattern = new ImagePattern(image);
            setWidth(image.getWidth());
        setHeight(image.getHeight());
        setFill(imgPattern);
            img = image;
            return image;
        } catch (MalformedURLException muee) {

            AppMessageDialogSingleton.getSingleton().show("Loading image error", "There was an error loading the image as follows" + Arrays.toString(muee.getStackTrace()));
            return null;
        }
        }else{
            return null;
        }

    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_OVERLAY;
    }

    @Override
    public void start(int x, int y) {
           setX(x);
        setY(y);
    }

    @Override
    public void drag(int x, int y) {
        double diffX = x - startX;
        double diffY = y - startY;
        double newX = getX() + diffX;
        double newY = getY() + diffY;
        
        
        setX(x);
        setY(y);
        startX = x;
        startY = y;
    }

    @Override
    public void size(int x, int y) {

    }

    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {

    }

    @Override
    public String getShapeType() {
        return IMAGE;
    }

}
