package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	
	public double xPos;
	public double yPos;
	public double playerSpeed;
	
	public Player(double w, double h, double x, double y, Color clr, String type) {
		super(w, h, clr);
		xPos = x;
		yPos = y;
		playerSpeed = 10;
		setLayoutX(xPos);
		setLayoutY(yPos);
	}
	
	public void moveLeft() {
		xPos -= playerSpeed;
	}
	
	public void moveRight() {
		xPos += playerSpeed;
	}
	
	public void render() {
		setLayoutX(xPos);
		setLayoutY(yPos);
	}
}
