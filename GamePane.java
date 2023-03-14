package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class GamePane extends Pane {

    private Timeline gameLoop;
    private boolean isPaused;
    private PauseMenu pauseMenu;
    private KeyListener keyListener;
    
    public GamePane() {
        //TODO: initialize game objects
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        isPaused = false;
        pauseMenu = new PauseMenu();   
    }
    
    public void runGame() {
        keyListener = new KeyListener(getScene());
    	keyListener.start(); // start the KeyListener
        Duration frameTime = Duration.seconds(1.0 / 60.0);	//60 FPS  
        KeyFrame keyFrame = new KeyFrame(frameTime, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if (keyListener.isPaused()) {
            		System.out.println("paused");
            		pauseMenu.show();
            	} else {
            	    update();
            	    render();
            	}

            }
        });
        gameLoop.getKeyFrames().add(keyFrame);
        gameLoop.play();
    }
    
    private void update() {
        // TODO: update game

    }
    
    private void render() {
        // TODO: draw game objects to screen
    }
    
    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            pauseMenu.show();
        } else {
            pauseMenu.hide();
        }
    }
}