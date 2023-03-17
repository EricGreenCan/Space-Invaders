package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Rectangle {
	
	public double xPos;
	public double yPos;
	public double enemySpeed;
	
	public Enemy(double w, double h, double x, double y, Color clr, String type) {
		super(w, h, clr);
		xPos = x;
		yPos = y;
		enemySpeed = 1;
		setLayoutX(xPos);
		setLayoutY(yPos);
	}
	
	public void update() {
		yPos += enemySpeed;
		setLayoutX(xPos);
		setLayoutY(yPos);
	}
	
	public double getXPos() {
		return xPos;
	}
	
	public double getYPos() {
		return yPos;
	}
	
	public void render() {
		//TODO
	}
}
