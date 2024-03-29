package application;

import java.awt.Dimension;
import java.awt.Toolkit;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class MenuPane extends Pane {

    private Button playButton;
    private Button exitButton;
    private Label titleLabel;
    private double scrollSpeed = .75;
    private Timeline timeline;
    private List<ImageView> backgroundImages;
    private double scrollPosition;
    private double WIDTH; 
    private double HEIGHT;
    //public SoundManager soundManager;

    public MenuPane() { 
    	
    	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    	//SoundManager soundManager = new SoundManager();
    	//soundManager.playSound();
        this.WIDTH = size.getWidth();
        this.HEIGHT = size.getHeight();
        
        //this.setAlignment(Pos.CENTER);
        
        scrollPosition = 0;
        
    	VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(50);
        content.setMaxWidth(1000);
        //content.setPrefWidth(600);
        

        playButton = new Button("Play");
        playButton.setOnMouseEntered(event -> playButton.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: white; -fx-background-radius: 0;"));
        playButton.setOnMouseExited(event -> playButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;"));
        playButton.setOnMousePressed(event -> playButton.setStyle("-fx-background-color: #D3D3D3; -fx-text-fill: white; -fx-background-radius: 0;"));
        playButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-background-radius: 0;");
        playButton.setOnAction(event -> {
        	playS();
        	//soundManager.playSound();
        	onPlayClicked();
        	});
        
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
        titleLabel.setMinWidth(1000);
        VBox.setMargin(titleLabel, new Insets(0, 0, 50, 0));
        titleLabel.setAlignment(Pos.CENTER);
        //titleLabel.setStyle("-fx-font-size: 8vw;");
        titleLabel.setWrapText(false);
        titleLabel.setMaxWidth(titleLabel.prefWidth(Double.MAX_VALUE));
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
        double contentWidth = content.prefWidth(-1);
        double contentHeight = content.prefHeight(-1);
        content.setMaxHeight(Region.USE_PREF_SIZE);
        content.setPrefSize(contentWidth, contentHeight);
        content.setTranslateX((WIDTH - contentWidth) / 2);
        content.setTranslateY((HEIGHT - contentHeight) / 2 - contentHeight / 2);
        
        getChildren().addAll(content);
       
        updateBackground();
    }

    public void setOnPlayClicked(EventHandler<ActionEvent> handler) {
        playButton.setOnAction(handler);
    }

    private void onPlayClicked() {
    	
        playButton.fire();
    }
    
    private void playS() {
    	//soundManager.playSound();
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