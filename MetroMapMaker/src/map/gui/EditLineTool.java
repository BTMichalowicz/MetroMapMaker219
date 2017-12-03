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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import map.data.DraggableLine;
import map.data.mapData;
import map.transact.EditLine;

/**
 * A class that will handle all line edits.
 *
 * From map to map, this really should stay constant
 *
 * @author Ben Michalowicz
 */
public class EditLineTool extends Stage {

    jTPS transact;
    jTPS_Transaction t;

    ColorPicker lineColor;

    Label lineDetails;

    DraggableLine metroLine;

    CheckBox cb;

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

        cb = new CheckBox();
        Label l2 = new Label("Circular Map?");

        HBox h = new HBox(20);
        h.getChildren().addAll(ok, cancel, cb, l2);

        container.getChildren().addAll(lineDetails, metroLineText, lineColor, h);

        Scene scene = new Scene(container, 250, 300);
        setScene(scene);
    }

    private void initControllers() {
        ok.setOnAction((ActionEvent e) -> {

            Color prevColor = (Color) metroLine.getStroke();
            String prevName = metroLine.getName();

            transact = ((mapData) app.getDataComponent()).getTransact();
            t = new EditLine(app, cb, prevColor, (Color) lineColor.getValue(), prevName, metroLineText.getText(), metroLine);

            transact.addTransaction(t);

            close();

        });

        cancel.setOnAction((ActionEvent e) -> {
            close();
        });

    }

}
