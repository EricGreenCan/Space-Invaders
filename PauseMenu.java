package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class PauseMenu {

    private StackPane overlay;
    private Button resumeButton;
    private Button quitButton;
    private GamePane gamePane;
    private KeyListener keyListener;
    private Label titleLabel;

    public PauseMenu(GamePane gamePane) {
        this.gamePane = gamePane;

        // create the pause menu overlay
        overlay = new StackPane();
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // set overlay to cover entire screen
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        overlay.setPrefSize(screenWidth, screenHeight);
        
        titleLabel = new Label("Paused");
	    titleLabel.setFont(new javafx.scene.text.Font("Impact", 72));
	    titleLabel.setTextFill(Color.WHITE);
	    
        // buttons
        resumeButton = new Button("Resume");
        resumeButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;");
        resumeButton.setOnAction(e -> {
            keyListener.setPauseFalse();
            hide();
        });

        quitButton = new Button("Main Menu");
        quitButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;");
        quitButton.setOnAction(e -> {
            System.exit(0);
        });

        VBox root = new VBox(50);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleLabel, resumeButton, quitButton);

        overlay.getChildren().add(root);
    }

    public void show(KeyListener k) {

        gamePane.getChildren().add(overlay);
        keyListener = k; //pass keylistener through to call method to reset pause variable
    }

    public void hide() {
        gamePane.getChildren().remove(overlay);
    }
}