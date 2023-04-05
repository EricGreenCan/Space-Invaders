package application;

import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    public double xPos;
    public double yPos;
    public double shootDelay;
    public double Speed;
    public int lives;
    public double spread_min;
    public double spread_max;
    public boolean canShoot;
    public int score;

    public Player(double w, double h, double x, double y, Image img, String type) {
        super(w, h);
        xPos = x;
        yPos = y;
        score = 0;
        lives = 3;
        canShoot = true;
        shootDelay = 1;
        Speed = 10;
        setLayoutX(xPos);
        setLayoutY(yPos);
        setFill(new ImagePattern(img));
        spread_min = 87;
        spread_max = 93;
        DropShadow dropShadow = new DropShadow(10, Color.rgb(0, 0, 0, 0.5));
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setRadius(15);
        dropShadow.setSpread(0.2);
        setEffect(dropShadow);
    }
    
    public void setXPos(double x) {
    	setLayoutX(x);
    }
    
    public void setYPos(double y) {
    	setLayoutX(y);
    }
    
    
    public double getSpeed() {
    	return Speed;
    }

    public void moveLeft() {
        xPos -= Speed;
    }

    public void moveRight() {
        xPos += Speed;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void render() {
        setLayoutX(xPos);
        setLayoutY(yPos);
    }
}