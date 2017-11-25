package map.data;

import djf.AppTemplate;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Used for listing stations in a neat and tidy order such that one may be able
 * to view it clearly.
 *
 * @author Ben Michalowicz
 */
public class ListStationsWindow extends Stage {

    Label stationList;

    Label label;

    Button btnOK;

    AppTemplate app;
    DraggableLine drag;

    public ListStationsWindow(AppTemplate app, DraggableLine drag) {
        this.app = app;
        this.drag = drag;

        initLayout();
        initControllers();
        listStations(drag);

    }

    private void initLayout() {

        stationList = new Label();

        label = new Label("Details of " + drag.getName() + "line and its stations: ");

        btnOK = new Button("OK; take me back now, please.");
        
        VBox container = new VBox(25);
        container.getChildren().addAll(label, stationList, btnOK);
        
        Scene s = new Scene(container, 150, 200);
        setScene(s);

    }

    private void listStations(DraggableLine drag) {
        
        StringBuilder str = new StringBuilder();
        drag.getStations().forEach((s) -> {
            str.append("\u2022 ").append(s).append("\n");
        });
        
        stationList.setText(str.toString());
    }

    private void initControllers() {

        btnOK.setOnAction(e -> {
            close();

        });

    }

}
