package application;

import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class MenuPane extends VBox {

    private Button playButton;
    private Button exitButton;
    private Label titleLabel;
    private Media media;
    private MediaPlayer mediaPlayer;

    public MenuPane() {  	
    	//media = new Media(Paths.get("theme.mp3").toUri().toString());
        //mediaPlayer = new MediaPlayer(media);

        //mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        //mediaPlayer.play();
        
        playButton = new Button("Play");
        
        playButton.setOnMouseEntered(event -> playButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        playButton.setOnMouseExited(event -> playButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        playButton.setOnMousePressed(event -> playButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        playButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        playButton.setMinWidth(200);
        playButton.setMinHeight(100);
        
        exitButton = new Button("Exit");
        exitButton.setOnMouseEntered(event -> exitButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        exitButton.setOnMouseExited(event -> exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        exitButton.setOnMousePressed(event -> exitButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        exitButton.setMinWidth(200);
        exitButton.setMinHeight(100);

        titleLabel = new Label("Space Invaders");
        titleLabel.setFont(new javafx.scene.text.Font("Impact", 150));
        titleLabel.setTextFill(Color.WHITE);
        VBox.setMargin(titleLabel, new Insets(0, 0, 50, 0));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-font-size: 8vw;"); 

        getChildren().addAll(titleLabel, playButton, exitButton);
        setAlignment(Pos.CENTER);
        setSpacing(50);
        setStyle("-fx-background-color: black;");

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