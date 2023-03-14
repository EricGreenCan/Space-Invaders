package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GamePane extends Pane {

    private Timeline gameLoop;
    private PauseMenu pauseMenu;
    private KeyListener keyListener;
    private boolean toggle;
    Player player;
    
    public GamePane() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        pauseMenu = new PauseMenu(this);
        pauseMenu.hide();
        toggle = false;
        
        //Initialize game objects
        player = new Player(50, 50, 1000, 100, Color.RED, "player");
        
        //add game objects to game pane
        getChildren().add(player);
    }  

    public void runGame() {
        keyListener = new KeyListener(getScene());
        keyListener.start(); // start the KeyListener
        Duration frameTime = Duration.seconds(1.0 / 60.0);    //60 FPS  
        KeyFrame keyFrame = new KeyFrame(frameTime, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (keyListener.isPaused()) {
                    if(!toggle) {
                    	toggle = true;
                    	pauseMenu.show(keyListener);
                    }
                } else {
                	toggle = false;
                    update();
                    render();
                }
            }
        });
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }

    //////////////////////////////////////////////////////////////////////
    //Game related events and graphics updated in the two methods below//
    ////////////////////////////////////////////////////////////////////
    
    private void update() {
    	if(keyListener.isKeyPressed("A")) {
    		player.moveLeft();
    	}
    	if(keyListener.isKeyPressed("D")) {
    		player.moveRight();
    	}
    	
    }
    
    private void render() {
        player.render();
    }
}