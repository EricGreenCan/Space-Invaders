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
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;

public class GamePane extends Pane {
	
    private ArrayList<PowerUp> powerups;
    private List<ImageView> backgroundImages;
    private double scrollPosition = 0;
    private double scrollSpeed = .75;
    private Timeline gameLoop;
    private PauseMenu pauseMenu;
    private GameOver gameOverMenu;
    private KeyListener keyListener;
    public boolean toggle;
    public static List<Bullet> bullets = new ArrayList<>();
    public static List<EnemyBullet> enemy_bullets = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Label stageLabel;
    public StageHandler stageHandler;
    private Screen screen;
    private Rectangle2D bounds;
    private double WIDTH; 
    private double HEIGHT;
    private double random_angle;
    private int max;
    private int min;
    Player player;
    
    public GamePane() {
    	
        this.screen = Screen.getPrimary();
        this.bounds = screen.getVisualBounds();
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
        powerups = new ArrayList<>();
        min = 0;
        max = 100;
        stageHandler = new StageHandler(HEIGHT);
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        pauseMenu = new PauseMenu(this);
        gameOverMenu = new GameOver(this);
        gameOverMenu.hide();
        pauseMenu.hide();
        toggle = false;
        stageLabel = new Label("Stage: " + stageHandler.getStage());
        stageLabel.setFont(new Font("Impact", 35));
        stageLabel.setTextFill(Color.WHITE);
        stageLabel.setPadding(new Insets(10));
        stageLabel.setLayoutX((WIDTH - stageLabel.getWidth()) / 2);

        backgroundImages = new ArrayList<>();
        Image image = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "\\Images\\space4.png");
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

        Image shipImage = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/ship.png");

        player = new Player(64, 64, (screenSize.getWidth() - 50) / 2, screenSize.getHeight() - 70, shipImage, "player");

        getChildren().addAll(stageLabel, player);
    }

    public void runGame() {
        keyListener = new KeyListener(getScene());
        keyListener.start(); // start the KeyListener
        Duration frameTime = Duration.seconds(1.0 / 60.0);    //60 FPS
        KeyFrame keyFrame = new KeyFrame(frameTime, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	if(player.lives > 0) {
	                if (keyListener.isPaused()) {
	                    if (!toggle) {
	                        keyListener.downKeys.clear();
	                        toggle = true;
	                        pauseMenu.show(keyListener);
	                    }
	                } else {
	                    toggle = false;
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
	                    update();
	                    render();
	                }
            	}else {
            		if(!toggle) {
            			keyListener.downKeys.clear();
            			toggle = true;
            			gameOverMenu.show(keyListener);   
            		}
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
        
        if (keyListener.isKeyPressed("SPACE") && player.canShoot) {
        	player.getXPos();
            player.getYPos();
            random_angle = Math.floor(Math.random() *(player.spread_max - player.spread_min + 1) + player.spread_min);       
            Bullet bullet = new Bullet(2, 75, player.getXPos()+32, player.getYPos()-16, Color.rgb(255, 255, 255, 0.5), random_angle, "player");
            getChildren().add(bullet);
            bullets.add(bullet);
            player.canShoot = false;
            Timeline delay = new Timeline(new KeyFrame(Duration.seconds(player.shootDelay), e -> player.canShoot = true));
            delay.play();
        }
        
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        List<PowerUp> powerupsToRemove = new ArrayList<>();
        List<EnemyBullet> enemy_bulletsToRemove = new ArrayList<>();
         
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.isOutOfScreen()) {
                bulletsToRemove.add(bullet);
            } else {
            	for (PowerUp powerup : powerups) {
            		if (bullet.getBoundsInParent().intersects(powerup.getBoundsInParent())) {
            			if (powerup.getType() == "move_speed") {
            				player.Speed += .1;
            				if(player.shootDelay > .15) {
            					player.shootDelay -= .05;           				
            				}        				
            			}
            			powerupsToRemove.add(powerup);
            		}
            	}
                for (Enemy enemy : enemies) {
                    if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    	if(Math.floor(Math.random() * (max - min + 1) + min) < 20) {
                    		Image speedup_img = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/fireup.png");                    		
                    		PowerUp powerup = new PowerUp(32, 32, enemy.getXPos(), enemy.getYPos(), Color.BLUE,  "move_speed", speedup_img);
                    		powerups.add(powerup);
                    		getChildren().addAll(powerup);
                    	}
                        bulletsToRemove.add(bullet);
                        enemiesToRemove.add(enemy);
                        stageHandler.destroyEnemy();
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
        powerups.removeAll(powerupsToRemove);
        getChildren().removeAll(bulletsToRemove);
        getChildren().removeAll(enemiesToRemove);
        getChildren().removeAll(powerupsToRemove);
        
        for(Enemy enemy : enemies) {
            enemy.update();
            if((Math.floor(Math.random() *(1500 - 0 + 1) + 0)) < 10) {
            	random_angle = Math.floor(Math.random() * (player.spread_max - player.spread_min + 1) + player.spread_min);
            	EnemyBullet enemy_bullet = new EnemyBullet(2, 32, enemy.getXPos()+32, enemy.getYPos()+16, Color.rgb(173, 247, 183, 0.5), random_angle, "enemy");
            	enemy_bullets.add(enemy_bullet);
            	getChildren().add(enemy_bullet);
            }
        }
        
        for(PowerUp powerup : powerups) {
            powerup.update();
        }
        
        for(EnemyBullet enemy_bullet : enemy_bullets) {
            enemy_bullet.update();
            if (enemy_bullet.isOutOfScreen()) {
                enemy_bulletsToRemove.add(enemy_bullet);
            }
            if (enemy_bullet.getBoundsInParent().intersects(player.getBoundsInParent())) {
            	enemy_bulletsToRemove.add(enemy_bullet);
            	player.lives -= 1;
            }
        }
        enemy_bullets.removeAll(enemy_bulletsToRemove);
        getChildren().removeAll(enemy_bulletsToRemove);
        
        
        if(stageHandler.isNewStage()) {
        	stageHandler.generateNewStage();
            enemies = stageHandler.getEnemies();
            getChildren().addAll(enemies);
        }
        stageLabel.setText("Stage: " + stageHandler.getStage());
        stageLabel.setLayoutX((WIDTH - stageLabel.getWidth()) / 2);
        
    }
    
    public void resetGame() {
    	getChildren().removeAll(enemies);
    	getChildren().removeAll(powerups);
    	getChildren().removeAll(bullets);
    	getChildren().removeAll(enemy_bullets);
    	enemies.clear();
    	powerups.clear();
    	bullets.clear();
    	enemy_bullets.clear();	
    	stageHandler.enemiesLeft = 0;
    	stageHandler.currentStage = 0;
    	toggle = false;
    	player.lives = 3;
    	player.xPos = (screenSize.getWidth() - 50) / 2;
    	player.yPos = screenSize.getHeight() - 70;
    }
    
    private void render() {
        player.render();
    }
}