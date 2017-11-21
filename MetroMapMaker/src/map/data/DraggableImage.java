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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import map.gui.mapWorkspace;
import properties_manager.PropertiesManager;

/**
 * Image Overlay Object for the project. Please let all of this work
 * @author Ben Michalowicz
 */
public class DraggableImage extends Rectangle implements Draggable{
    
    String filePath;
    double startX, startY;
    
   
    
    Image img;
    AppTemplate app;
    ImagePattern imgPattern;
    
    
    public DraggableImage(AppTemplate app){
        this.app = app;
        
        img = getNewImage();
        
        imgPattern = new ImagePattern(img);
        
        setWidth(img.getWidth());
        setHeight(img.getHeight());
        setFill(imgPattern);
        
        
    }
    
    private Image getNewImage(){
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();
        Scene sc = app.getGUI().getPrimaryScene();
        // AND NOW ASK THE USER FOR THE FILE TO OPEN
        FileChooser fc = new FileChooser();

        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        fc.setInitialDirectory(new File(PATH_IMAGES));
        fc.setTitle(props.getProperty("Load an Image"));

        File selectedFile = fc.showOpenDialog(null);
        
        ((mapData)app.getDataComponent()).setState(mapState.DRAGGING);
        
        try{
        URL fileU = selectedFile.toURI().toURL();

        Image image = new Image(fileU.toExternalForm());
        
        filePath = selectedFile.getPath();

        return image;
        }catch(MalformedURLException muee){
            
            AppMessageDialogSingleton.getSingleton().show("Loading image error","There was an error loading the image as follows" + Arrays.toString(muee.getStackTrace()));
            return null;
        }

    }
    
    
    
    
    
     public String getFilePath(){
        return filePath;
    }

    @Override
    public mapState getStartingState() {
        return mapState.STARTING_OVERLAY;
    }

    @Override
    public void start(int x, int y) {
        
    }

    @Override
    public void drag(int x, int y) {
        
            ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        setOnMousePressed( event -> mouseAnchor.set(new Point2D(getX(), getY())));
       setOnMouseDragged( event -> {
            double deltaX = getX() - mouseAnchor.get().getX();
            double deltaY = getX() - mouseAnchor.get().getY();
            relocate(getLayoutX()+deltaX, getLayoutY()+deltaY);
            mouseAnchor.set(new Point2D(event.getSceneX(), event.getSceneY()));
        });
        
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
