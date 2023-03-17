package application;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bullet extends Rectangle {
	
	private double speed;
	private double dx;
	private double dy;
	
	public Bullet(double w, double h, double x, double y, Color clr, String type) {
		super(w, h, clr);
		setLayoutX(x);
		setLayoutY(y);
		speed = 50;
		dx = 0;
		dy = -speed;
	}
	
	public void update() {
		double newX = getLayoutX() + dx;
		double newY = getLayoutY() + dy;
		setLayoutX(newX);
		setLayoutY(newY);
	}
	
	public boolean isOutOfScreen() {
		double newX = getLayoutX() + dx;
		double newY = getLayoutY() + dy;
		try 
		{
			
			Bounds bounds = getParent().getBoundsInLocal();
			if (newX < 0 || newX > bounds.getMaxX() || newY < -200 || newY > (bounds.getMaxY())) 
			{
				return true;		
			}
			
			return false;
			
		} 
		catch(Exception e) 
		{
			return false;
		}			
	}
	
	public void render() {
		//NA
	}
}