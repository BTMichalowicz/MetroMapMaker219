/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.data;

import djf.AppTemplate;
import javafx.scene.shape.Ellipse;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Ben Michalowicz
 */
public class DraggableStation extends Ellipse implements Draggable{
    
    double startCenterX;
    double startCenterY;
    
    AppTemplate app;
    
    String name;
    
    public String getName(){
        return name;
    }
    
    public DraggableStation(AppTemplate initApp, String name) {
	setCenterX(0.0);
	setCenterY(0.0);
	setRadiusX(5);
	setRadiusY(5);
	setOpacity(1.0);
	startCenterX = 0.0;
	startCenterY = 0.0;
        this.app = initApp;
        this.name = name;
    }
    
    @Override
    public mapState getStartingState() {
	return mapState.STARTING_STATION;
    }
    
    @Override
    public void start(int x, int y) {
	startCenterX = x;
	startCenterY = y;
    }
    
    jTPS transact;
    jTPS_Transaction t;
    
    @Override
    public void drag(int x, int y) {
        
        
	double diffX = x - startCenterX;
	double diffY = y - startCenterY;
	double newX = getCenterX() + diffX;
	double newY = getCenterY() + diffY;
	setCenterX(newX);
	setCenterY(newY);
	startCenterX = x;
	startCenterY = y;
        
        
    }
    
    @Override
    public void size(int x, int y) {
	double width = x - startCenterX;
	double height = y - startCenterY;
	double centerX = startCenterX + (width / 2);
	double centerY = startCenterY + (height / 2);
	setCenterX(centerX);
	setCenterY(centerY);
	
        
        
	
    }
        
    @Override
    public double getX() {
	return getCenterX() - getRadiusX();
    }

    @Override
    public double getY() {
	return getCenterY() - getRadiusY();
    }

    @Override
    public double getWidth() {
	return getRadiusX() * 2;
    }

    @Override
    public double getHeight() {
	return getRadiusY() * 2;
    }
        
    @Override
    public void setLocationAndSize(double initX, double initY, double initWidth, double initHeight) {
	setCenterX(initX + (initWidth/2));
	setCenterY(initY + (initHeight/2));

    }
    
    @Override
    public String getShapeType() {
	return STATION;
    }
    
}
