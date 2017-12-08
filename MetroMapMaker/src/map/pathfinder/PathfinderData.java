package map.pathfinder;

import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import map.data.DraggableLine;
import map.data.DraggableStation;

/**
 *
 * @author Ben Michalowicz
 */
public class PathfinderData {

    private ArrayList<DraggableLine> lines;

    private ArrayList<DraggableStation> stations;

    private DraggableStation d1, d2;

    public PathfinderData(ArrayList<DraggableLine> lines, DraggableStation d1, DraggableStation d2) {
        this.lines = lines;
        this.d1 = d1;
        this.d2 = d2;
        this.stations = extractData(this.lines);

        createGraph(stations);

    }

    private ArrayList<DraggableStation> extractData(ArrayList<DraggableLine> lines) {
        if (lines==null || lines.isEmpty()){
            Alert result = new Alert(AlertType.INFORMATION);
        result.setHeaderText(null);
        result.setContentText("Your Route: NO PATH FOUND!!");
        result.show();
            
        }
        ArrayList<DraggableStation> stat = new ArrayList<>();

        for (DraggableLine l : lines) {
            for (DraggableStation ds : l.getStat()) {
                stat.add(ds);
            }
        }

        if (!stat.contains(d1)) {
           Alert result = new Alert(AlertType.INFORMATION);
        result.setHeaderText(null);
        result.setContentText("Your Route: NO PATH FOUND!!");
        result.show();
            return null;
        }

        if (!stat.contains(d2)) {
            Alert result = new Alert(AlertType.INFORMATION);
        result.setHeaderText(null);
        result.setContentText("Your Route: NO PATH FOUND!!");
        result.show();
            return null;
        }

        return stat;
    }

    private Edge[] createGraph(ArrayList<DraggableStation> stations) {

        if (stations == null) {
            Alert result = new Alert(AlertType.INFORMATION);
        result.setHeaderText(null);
        result.setContentText("Your Route: NO PATH FOUND!!");
        result.show();
            return null; //No stations = no graph = no path to be found.
        }

        if (stations.isEmpty()) {
            
            
             Alert result = new Alert(AlertType.INFORMATION);
        result.setHeaderText(null);
        result.setContentText("Your Route: NO PATH FOUND!!");
        result.show();
            return null; //Again...
        }
        Edge[] arr = new Edge[stations.size()]; //Have a set of edges between each of the nodes in the list of stations

        
        stations.trimToSize();
        int j;
        StringBuilder s = new StringBuilder();
        if (stations.indexOf(d1) > stations.indexOf(d2)) {
            j = 0;
            for (int i = stations.indexOf(d1); i >=stations.indexOf(d2); i--) {

                arr[j] = new Edge(0, j, (j == 0 ? 0 : j * 3)); //set up the edges, where weights from the center are in incremenets of 3 minutes

                j++;

                s.append(stations.get(i).getName()).append("\n\n");
            }

        } else if (stations.indexOf(d1)<stations.indexOf(d2)){
            j = 0;
            for (int i = stations.indexOf(d1); i <=stations.indexOf(d2); i++) {

                arr[j] = new Edge(0, j, (j == 0 ? 0 : j * 3)); //set up the edges, where weights from the center are in incremenets of 3 minutes

                j++;
                s.append(stations.get(i).getName()).append("\n\n");
            }
        }else{
            j = 0;
            arr[0] = new Edge(0,0,0);
            s.append(stations.get(0).getName());
        }

        Edge[] arr2 = new Edge[j];
        System.arraycopy(arr, 0, arr2, 0, j);

        Graph graph = new Graph(arr2);

        graph.calculateShortestDistances();
        
        Alert result = new Alert(AlertType.INFORMATION);
        result.setHeaderText(null);
        result.setContentText("Your Route: \n\n" + s.toString());
        result.show();
        
        return arr;
    }

}
