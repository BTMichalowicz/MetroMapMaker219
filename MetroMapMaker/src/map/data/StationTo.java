package map.data;

import djf.AppTemplate;
import javafx.scene.shape.LineTo;

/**
 * A basic class to skirt around DraggableStations and adding to the JavaFX Path
 * object. Super effective!!!
 *
 * @author Ben Michalowicz
 */
public class StationTo extends LineTo {

    DraggableStation s;
    AppTemplate app;

    public StationTo(DraggableStation s) {
        super(s.getX(), s.getY());

    }

}
