/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.gui;

import djf.AppTemplate;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class EditLineSingleton extends Stage {

    private static EditLineSingleton editor = null;

    ColorPicker lineColor;

    Label lineDetails;

    ComboBox<DraggableLine> metroLines;

    Button ok, cancel;

    AppTemplate app;
    VBox container;

    public void setMetroLines(ComboBox<DraggableLine> metroLines) {
        this.metroLines = metroLines;
    }

    public EditLineSingleton(AppTemplate app, ComboBox<DraggableLine> metroLines) {
        this.app = app;
        this.metroLines = metroLines;
        initLayout();
        initControllers();

    }

    public static EditLineSingleton getEditor(AppTemplate app, ComboBox<DraggableLine> metroLines) {
        if (editor == null) {
            editor = new EditLineSingleton(app, metroLines);
        }

        return editor;
    }

    private void initLayout() {
        lineDetails = new Label("Metro Line Details");

        lineDetails.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        ok = new Button("OK");
        cancel = new Button("Cancel");
        lineColor = new ColorPicker();

        container = new VBox(25);

        HBox h = new HBox(20);
        h.getChildren().addAll(ok, cancel);

        container.getChildren().addAll(lineDetails, metroLines, lineColor, h);
    }

    private void initControllers() {
        ok.setOnAction((ActionEvent e) -> {
            if (metroLines.getSelectionModel().getSelectedItem() != null) {
                DraggableLine drag = metroLines.getSelectionModel().getSelectedItem();
                
                ((mapWorkspace) app.getWorkspaceComponent()).getCanvas().getChildren().stream().filter((s) -> (s instanceof DraggableLine)).map((s) -> (DraggableLine) s).filter((that) -> (drag.getName().equals(that.getName()))).forEachOrdered((that) -> {
                    that.setFill(lineColor.getValue());
                });
                
            }
        });

        cancel.setOnAction((ActionEvent e) -> {
            close();
        });

        if (metroLines.getSelectionModel().getSelectedItem() != null) {
            lineColor.setValue(Color.valueOf(metroLines.getSelectionModel().getSelectedItem().getFill().toString()));
        }
    }

}
