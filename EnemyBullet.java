package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class EnemyBullet extends Rectangle {
	
	private double speed;
	private double dx;
	private double dy;
	
	public EnemyBullet(double w, double h, double x, double y, @SuppressWarnings("exports") Color clr, double direction, String type) {
		super(w, h, clr);
		clr = null;
		setLayoutX(x);
		setLayoutY(y);
		speed = 10;
		double angle = Math.toRadians(direction);
		setRotate(angle);
		dx = speed * Math.cos(angle);
		dy = -speed * Math.sin(angle);
		DropShadow dropShadow = new DropShadow(10, Color.rgb(173, 247, 183, 0.5));
        dropShadow.setSpread(0.5);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(dropShadow.radiusProperty(), 11)),
                new KeyFrame(Duration.seconds(1), new KeyValue(dropShadow.colorProperty(), Color.rgb(173, 247, 183, 0.5))),
                new KeyFrame(Duration.seconds(2), new KeyValue(dropShadow.radiusProperty(), 13)),
                new KeyFrame(Duration.seconds(3), new KeyValue(dropShadow.colorProperty(), Color.rgb(173, 247, 183, 1))),
                new KeyFrame(Duration.seconds(4), new KeyValue(dropShadow.radiusProperty(), 11)),
                new KeyFrame(Duration.seconds(5), new KeyValue(dropShadow.colorProperty(), Color.rgb(173, 247, 183, 0.5)))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        setEffect(dropShadow);
	}
	
	public void update() {
		double newX = getLayoutX() - dx;
		double newY = getLayoutY() - dy;
		setLayoutX(newX);
		setLayoutY(newY);
	}
	
	public boolean isOutOfScreen() {
		double newX = getLayoutX() - dx;
		double newY = getLayoutY() - dy;
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