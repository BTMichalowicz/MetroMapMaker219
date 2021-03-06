package map.data;

import djf.AppTemplate;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import map.gui.mapWorkspace;

/**
 * A stage subclass created for the purpose of adding new Lines.
 *
 * @author Ben Michalowicz
 */
public class AddLineWindow extends Stage {

    ColorPicker lineColor;

    TextField lineName;

    ActionEvent resultant;
    
    CheckBox cb;

    public ActionEvent getResultant() {
        return resultant;
    }

    Label notice;

    Button btnOk, btnCancel;

    AppTemplate app;

    mapData dataManager;

    public ColorPicker getLineColor() {
        return lineColor;
    }

    public TextField getLineName() {
        return lineName;
    }

    public Label getNotice() {
        return notice;
    }

    public Button getBtnOk() {
        return btnOk;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public AppTemplate getApp() {
        return app;
    }

    public mapData getDataManager() {
        return dataManager;
    }

    String name;

    public String getName() {
        return name;
    }

    public AddLineWindow(AppTemplate app) {
        this.app = app;
        initLayout();

        initControllers();

    }

    /**
     * Sets up the layout for the stage for the metro line creation.
     */
    private void initLayout() {

        lineColor = new ColorPicker();

        lineName = new TextField("");
        notice = new Label("Enter your line name!!");

        btnOk = new Button("Let's create a line!");
        btnCancel = new Button("Let's not create a new line!");
        
        cb = new CheckBox();
        Label l = new Label("Circular Line?");

        FlowPane window = new FlowPane();

        HBox buttons = new HBox(12);

        buttons.getChildren().addAll(btnOk, btnCancel);
        HBox top = new HBox(10);
        top.getChildren().addAll(notice, lineName, cb, l);

        VBox base = new VBox(12);

        base.getChildren().addAll(top, lineColor, buttons);
        base.setAlignment(Pos.CENTER);

        window.getChildren().add(base);
        window.setStyle("-fx-background-color: #F8ECC2;");

        Scene primary = new Scene(window, 450, 300);

        setScene(primary);

    }

    private void initControllers() {
        dataManager = (mapData) app.getDataComponent();

        mapWorkspace workspace = (mapWorkspace) app.getWorkspaceComponent();

        btnOk.setOnAction(e -> {

            while (lineName.getText().isEmpty()) {

                Alert duplicate = new Alert(Alert.AlertType.ERROR);
                duplicate.setHeaderText(null);
                duplicate.setContentText("You need a name for the Metro Line!!");
                duplicate.showAndWait();
                close();
                showAndWait();

            }

            name = lineName.getText();
            workspace.getEditLine().setStyle("-fx-background-color: #" + lineColor.getValue().toString().split("[x]")[1] + ";");
            workspace.getEditLine().setText(lineColor.getValue().toString());
            workspace.getEditLine().setDisable(false);
            resultant = e;
            close();

        });

        btnCancel.setOnAction(e -> {
            close();
            lineName.setText("");
            lineColor.setValue(Color.WHITE);
            workspace.getEditLine().setDisable(false);
            resultant = e;

        });
    }

}
