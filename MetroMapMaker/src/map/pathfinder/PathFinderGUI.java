package map.pathfinder;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import map.data.DraggableLine;

/**
 *
 * The resulting Singleton that would show how
 * @author Ben Michalowicz
 */
public class PathFinderGUI extends Stage{
    
    String statName1, statName2;
    
    TextArea result;
    
    Button btnOK;
    
    Label routeName;
    
    HBox hbox;
    
    
    public PathFinderGUI(String startName, String endName){
        this.statName1 = startName;
        this.statName2 = endName;
        
        initLayout();
        initControllers();
    }
    
    private void initLayout(){
        
        
        btnOK = new Button("OK, let's make some maps!");
        routeName = new Label("Route from " + statName1 + " to " + statName2);
        routeName.setStyle("-fx-font-size: 16;");
        hbox = new HBox(20);
        
        result = new TextArea();
        
        hbox.getChildren().addAll(routeName, result, btnOK);
        
        setScene(new Scene(hbox, 400, 400));
        
    }
    
    private void initControllers(){
        btnOK.setOnAction(e->{close();});
    }
    
    public void toStringPath(ArrayList<String> stationNames, DraggableLine dl1, DraggableLine dl2){
        
        StringBuilder s = new StringBuilder();
        
        s.append("Origin: " + statName1 + "\n");
        s.append("Destination: " + statName2+ "\n");
        s.append(dl1.getName() + "(" + (dl1.getElements().size()-2) + " stops)\n");
        s.append(dl2.getName() + "("+ (dl2.getElements().size()-2) + " stops)\n");
        s.append("Estimated Time:: " + (dl1.getElements().size()-2 + dl2.getElements().size()-2)+"\n\n");
        
        
                
    }
    
    
    
}
