package application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import java.util.stream.Collectors;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	
    //game scenes
    private static Scene gameScene;
    private static Scene menuScene;
    private static Scene gameOverScene;
    
    // Get the width and height of the screen
    private static Screen screen = Screen.getPrimary();
    private static Rectangle2D bounds = screen.getVisualBounds();
    private static double WIDTH = bounds.getWidth();
    private static double HEIGHT = bounds.getHeight();
    
    //player and enemy attributes
    private static Sprite player = new Sprite(50, 50,  WIDTH/2, HEIGHT-100, Color.BLACK, "player");
    private static int shoot_delay = 500; //time between the players shots in ms
    private static int player_bullet_speed = 10;
    private static int lives = 3;
    private static int kills = 0;
    private static final int enemies = 10;
    private static int enemy_bullet_speed = 10;
    
	private static final Pane root = new Pane();
	private static double t = 0;

	private static Parent createContent() {
		
		
		root.getChildren().add(player);
		
		AnimationTimer updater = new AnimationTimer() {
			@Override
			public void handle(long now) {
				update();
				
			}

		};
		updater.start();
		return root;
	}

	private static List<Sprite> sprites() {
		return root.getChildren().stream().map(node -> (Sprite) node).collect(Collectors.toList());
	}

	private static void update() {
		t += 0.016;
		sprites().forEach(sprite -> {
			switch (sprite.type) {
			case "enemybullet":
				sprite.moveDown();
				if (sprite.getBoundsInParent().intersects(player.getBoundsInParent())) {
					sprite.dead = true;
					lives--;
					if (lives < 1) {
						player.dead = true;
						gameOver();
					}
				}
				break;

			case "playerbullet":
				sprite.moveUp();
				sprites().stream().filter(value -> value.type.equals("enemy")).forEach(enemy -> {
					if (sprite.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
						sprite.dead = true;
						enemy.dead = true;
						kills++;
					}
				});
				break;

			case "enemy":
				if (t > 2) {
					if (Math.random() < 0.6) 
					sprite.shoot();	
				}
				break;
			}
			
			if (kills == enemies) {
				Label rndTxt = new Label("Round Complete!");
				rndTxt.setFont(new javafx.scene.text.Font("Impact", 48));
			}
			
			//delete bullets if they are off the screen
			if (sprite.type.equals("enemybullet") || sprite.type.equals("playerbullet")) {
			    if (sprite.getBoundsInParent().getMaxY() < 0 || sprite.getBoundsInParent().getMinY() > HEIGHT) {
			        sprite.dead = true;
			    }
			}
		});
		
		

		if (t>2) {
			t=0;
		}
		
		root.getChildren().removeIf(bool -> {
			Sprite node = (Sprite) bool;
			return node.dead;
		});
	}

	private static void createEnemies() {
		for (int i = 0; i < enemies; i++) {
			
			//TODO, fix the enemy spawn locations
			root.getChildren().add(new Sprite(50, 50, i * (WIDTH / enemies) + ((WIDTH / enemies) - 50) / 2, 100, Color.ORANGE, "enemy"));
		}

	}
	
	private static void gameOver() {

	}

	@Override
	public void start(Stage primaryStage) {
		
	     //////////////////////////
	    // Create the game scene//
	   //////////////////////////
		
	    gameScene = new Scene(createContent(), WIDTH, HEIGHT);

	    ///////////////////////////
	    // Create the menu scene//
	    /////////////////////////
	    
	    Label titleLabel = new Label("Space Invaders");
	    titleLabel.setFont(new javafx.scene.text.Font("Impact", 48));
	    titleLabel.setTextFill(Color.WHITE); // Set the text color to white
	    
	    Button newGameButton = new Button("New Game");
	    newGameButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;"); // Set the button color to gold and the text color to black
	    
	    newGameButton.setOnAction(event -> {
	    	
	    	//////////////////////////////////////
	        // Start a new game on button press//
	    	////////////////////////////////////
	    	
	        lives = 3;
	        kills = 0;
	        createEnemies();
	        player.dead = false;
	        root.getChildren().removeIf(node -> {
	            Object type = node.getProperties().get("type");
	            return type != null && (type.equals("playerbullet") || type.equals("enemybullet"));
	        });
	        primaryStage.setScene(gameScene);
	        primaryStage.setFullScreen(true);
	    });
	    
	    Button instructionsButton = new Button("Instructions");
	    instructionsButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;"); // Set the button color to gold and the text color to black
	    instructionsButton.setOnAction(event -> {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Instructions");
	        alert.setHeaderText("How to Play");
	        alert.setContentText("Use the arrow keys to move and spacebar to shoot.\n" +
	                "Defeat all the enemies to advance to the next stage.\n");
	        alert.showAndWait();
	    });
	    
	    Button exitButton = new Button("Exit");
	    exitButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black;"); // Set the button color to gold and the text color to black
	    exitButton.setOnAction(event -> {
	    	
	    	///////////////////////////////////
	        // Exit the game on button press//
	    	/////////////////////////////////
	    	
	        primaryStage.close();
	    });
	    
	    VBox menuBox = new VBox(titleLabel, newGameButton, instructionsButton, exitButton);
	    menuBox.setAlignment(Pos.CENTER);
	    menuBox.setSpacing(20);
	    menuBox.setStyle("-fx-background-color: black;"); // Set the background color to black
	    
	    menuScene = new Scene(menuBox, WIDTH, HEIGHT);
	    
	    menuScene.setFill(Color.BLACK); // Set the scene color to black
	    
	    ////////////////////////////////////////////
	    // Show the menu scene before game starts//
	    //////////////////////////////////////////
	    
	    primaryStage.setFullScreen(true);
	    primaryStage.setScene(menuScene);
	    primaryStage.show();
	    
	    //////////////////////
	    //keyboard listener//
	    ////////////////////
	    
	    ObservableSet<KeyCode> downKeys = FXCollections.observableSet();


	    gameScene.setOnKeyPressed(evt->{
            downKeys.add(evt.getCode());
        });

        gameScene.setOnKeyReleased(evt->{
            downKeys.remove(evt.getCode());
        });


        Timeline timer = new Timeline(new KeyFrame(
            javafx.util.Duration.millis(16), ae -> {
                downKeys.stream().parallel().forEach(kc -> {
                    Platform.runLater(() -> {//why?
                        switch(kc){
                            case LEFT: player.moveLeft();
                                break;
                            case RIGHT: player.moveRight();
                                break;
                            case SPACE: player.shoot();
                            	break;
                        }
                    });
                });
            }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

	    
	}
	
	/////////////////
	//sprite class//
	////////////////

	private static class Sprite extends Rectangle {

		private double x;
		private double y;
		private String type;
		private boolean dead = false;
		private long lastShotTime;

		public Sprite(double w, double h, double x, double y, Color clr, String type) {
			super(w, h, clr);
			this.x = x;
			this.y = y;
			this.type = type;

			setLayoutX(x);
			setLayoutY(y);
		}

		public void moveRight() {
			setLayoutX(getLayoutX() + 20);
		}

		public void moveLeft() {
			setLayoutX(getLayoutX() - 20);
		}

		public void moveUp() {
			setLayoutY(getLayoutY() - player_bullet_speed);
		}

		public void moveDown() {
			setLayoutY(getLayoutY() + enemy_bullet_speed);
		}

		public void shoot() {
			if(!player.dead) {
				long time = System.currentTimeMillis();
				if (time - lastShotTime > shoot_delay) {
					lastShotTime = time;
					root.getChildren().add(new Sprite(5, 20, getLayoutX() + 25, getLayoutY(), Color.GREENYELLOW, type + "bullet"));
				}
			}
		}
	}
	
	////////////////
	//main method//
	//////////////

	public static void main(String[] args) {
		launch(args);
	}
}
