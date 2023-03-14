package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyListener {

    private Scene scene;
    private boolean isPaused;

    public KeyListener(Scene scene) {
        this.scene = scene;
        this.isPaused = false;
    }

    public void start() {
    	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
    	    public void handle(KeyEvent event) {
    	        System.out.println("Key pressed: " + event.getCode());
    	        if (event.getCode() == KeyCode.ESCAPE) {
    	            togglePause();
    	        }
    	    }
    	});
    }

    private void togglePause() {
        System.out.println("fired");
        isPaused = !isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}