/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import map.data.DraggableLine;

/**
 * A class that will handle all line edits.
 *
 * From map to map, this really should stay constant
 *
 * @author Ben Michalowicz
 */
public class EditLineTool extends Stage {

    private static EditLineTool editor = null;

    ColorPicker lineColor;

    Label lineDetails;

    DraggableLine metroLine;
    
    TextField metroLineText;

    Button ok, cancel;

    AppTemplate app;
    VBox container;

    public void setMetroLine(DraggableLine metroLine) {
        this.metroLine = metroLine;
    }

    public EditLineTool(AppTemplate app, DraggableLine metroLine) {
        this.app = app;
        this.metroLine = metroLine;
        initLayout();
        initControllers();

    }

    

    private void initLayout() {
        lineDetails = new Label("Metro Line Details");

        lineDetails.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        ok = new Button("OK");
        cancel = new Button("Cancel");
        lineColor = new ColorPicker();
        
        metroLineText = new TextField(metroLine.getName());

        container = new VBox(25);

        HBox h = new HBox(20);
        h.getChildren().addAll(ok, cancel);

        container.getChildren().addAll(lineDetails, metroLineText, lineColor, h);
        
        Scene scene = new Scene(container, 200, 200);
        setScene(scene);
    }

    private void initControllers() {
        ok.setOnAction((ActionEvent e) -> {
             metroLine.setStroke(lineColor.getValue());
             metroLine.setName(metroLineText.getText());
             metroLine.setStyle(metroLine.getStyle()+ "-fx-color: #" + lineColor.getValue().toString().split("[x]")[1] + ";");
             ((mapWorkspace)app.getWorkspaceComponent()).getEditLine().setStyle("-fx-background-color: #"+lineColor.getValue().toString().split("[x]")[1]+ ";");
             
             close();
                
            
        });

        cancel.setOnAction((ActionEvent e) -> {
            close();
        });

        
    }

}
