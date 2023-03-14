package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MenuPane extends VBox {
    
    private Button playButton;
    private Button exitButton;
    private Label titleLabel;
    
    public MenuPane() {
    	
    	//buttons
        playButton = new Button("Play");
        playButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;");
        exitButton = new Button("Exit");
        exitButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;");
        
        //title
        titleLabel = new Label("Space Invaders");
	    titleLabel.setFont(new javafx.scene.text.Font("Impact", 48));
	    titleLabel.setTextFill(Color.WHITE);
	    
	    //add to pane
        getChildren().addAll(titleLabel, playButton, exitButton);
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setStyle("-fx-background-color: black;");
        
        //set functionality
        playButton.setOnAction(event -> onPlayClicked());
        exitButton.setOnAction(event -> System.exit(0));
    }
    
    public void setOnPlayClicked(EventHandler<ActionEvent> handler) {
        playButton.setOnAction(handler);
    }
    
    private void onPlayClicked() {
        playButton.fire();
    }
    
}