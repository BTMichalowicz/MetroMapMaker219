/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.file;

import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.IOException;

/**
 *
 * @author Ben Michalowicz
 */
public class mapFiles implements AppFileComponent{
    
    

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        throw new IOException("Not supported yet!");
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        throw new IOException("Not supported yet!");
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new IOException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    // THIS METHOD WILL NOT BE USED FOR THE DURATION OF THIS PROJECT
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
