package application;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;

public class MenuPane extends Pane {

    private Button playButton;
    private Button exitButton;
    private Label titleLabel;
    private double scrollSpeed = .75;
    private Screen screen;
    private Rectangle2D bounds;
    private Timeline timeline;
    private List<ImageView> backgroundImages;
    private double scrollPosition;
    private double WIDTH; 
    private double HEIGHT;

    public MenuPane() { 
    	
    	this.screen = Screen.getPrimary();
        this.bounds = screen.getVisualBounds();
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
        
        scrollPosition = 0;
        
    	VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(50);

        playButton = new Button("Play");
        playButton.setOnMouseEntered(event -> playButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        playButton.setOnMouseExited(event -> playButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        playButton.setOnMousePressed(event -> playButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        playButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        playButton.setOnAction(event -> onPlayClicked());
        playButton.setMinWidth(200);
        playButton.setMinHeight(100);

        exitButton = new Button("Exit");
        exitButton.setOnMouseEntered(event -> exitButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        exitButton.setOnMouseExited(event -> exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        exitButton.setOnMousePressed(event -> exitButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        exitButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        exitButton.setOnAction(event -> System.exit(0));
        exitButton.setMinWidth(200);
        exitButton.setMinHeight(100);

        titleLabel = new Label("Space Invaders");
        titleLabel.setFont(new javafx.scene.text.Font("Impact", 150));
        titleLabel.setTextFill(Color.WHITE);
        VBox.setMargin(titleLabel, new Insets(0, 0, 50, 0));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle("-fx-font-size: 8vw;"); 

        content.getChildren().addAll(titleLabel, playButton, exitButton);
        
        
        backgroundImages = new ArrayList<>();
        Image image = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/space4.png");
        for (int i = 0; i < 2; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(WIDTH);
            imageView.setFitHeight(HEIGHT);
            imageView.setLayoutY(scrollPosition - (i * HEIGHT));
            if(i == 0) {
            	imageView.setScaleY(-1);
            }
            backgroundImages.add(imageView);
            getChildren().add(imageView);
        }
        
        getChildren().addAll(content);
        content.setLayoutX(WIDTH/4);
        content.setLayoutY(HEIGHT/4);
        updateBackground();
    }

    public void setOnPlayClicked(EventHandler<ActionEvent> handler) {
        playButton.setOnAction(handler);
    }

    private void onPlayClicked() {
        playButton.fire();
    }
    
    public void updateBackground() {
    	KeyFrame keyFrame = new KeyFrame(Duration.seconds(1.0 / 60.0), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scrollPosition += scrollSpeed;
                if (scrollPosition > HEIGHT) {
                    scrollPosition -= HEIGHT;
                }
                for (ImageView imageView : backgroundImages) {
                    double newY = imageView.getLayoutY() + scrollSpeed;
                    if (newY > HEIGHT) {
                        newY -= HEIGHT * 2;
                    }
                    imageView.setLayoutY(newY);
                }
            }
        });

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}