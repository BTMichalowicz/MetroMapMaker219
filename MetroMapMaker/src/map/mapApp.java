
package map;

import djf.AppTemplate;
import static javafx.application.Application.launch;
import map.data.mapData;
import map.file.mapFiles;
import map.gui.MapWorkspace;

/**This is the base class for the entire application of Metro Map Maker.
 * 
 * The majority of its behavior is taken from its superclass, AppTemplate.
 * 
 * Once all of the app-specific materials (icons, tooltips, and other settings) have been loaded,
 * The full UI is loaded with said settings.
 * 
 * 
 * Please note that this is a JavaFX Application
 * 
 */

/**
 *
 * @author Ben Michalowicz
 * @version 1.0
 */
public class mapApp extends AppTemplate{
    
    
    @Override
    public void buildAppComponentsHook(){
        
        fileComponent = new mapFiles();
        dataComponent = new mapData(this);
        workspaceComponent = new MapWorkspace(this);
    }

    
    
    /**
     * This is where the program starts its execution. Being a JavaFX application,
     * this method simply calls launch, and that results in starting with a properly set-up window
     * to the started method inherited from AppTemplate, defined in the DJF
     * @param args 
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
    
    
    
}
