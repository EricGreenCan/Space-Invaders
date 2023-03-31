package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	    titleLabel.setFont(new javafx.scene.text.Font("Impact", 115));
	    titleLabel.setTextFill(Color.WHITE);
	    
        // buttons
        resumeButton = new Button("Resume");
        resumeButton.setOnMouseEntered(event -> resumeButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        resumeButton.setOnMouseExited(event -> resumeButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        resumeButton.setOnMousePressed(event -> resumeButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        resumeButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        resumeButton.setMinWidth(150);
        resumeButton.setMinHeight(50);
        resumeButton.setOnAction(e -> {
            keyListener.setPauseFalse();
            hide();
        });

        quitButton = new Button("Main Menu");
        quitButton.setOnMouseEntered(event -> quitButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        quitButton.setOnMouseExited(event -> quitButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        quitButton.setOnMousePressed(event -> quitButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        quitButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        quitButton.setMinWidth(150);
        quitButton.setMinHeight(50);
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
        keyListener = k; 
    }

    public void hide() {
        gamePane.getChildren().remove(overlay);
    }
}