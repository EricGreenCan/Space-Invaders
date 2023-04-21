package application;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    public static List<Explosion> explosions = new ArrayList<>();
    public static List<Bullet> bulletsToRemove = new ArrayList<>();
    public static List<Enemy> enemiesToRemove = new ArrayList<>();
    public static List<PowerUp> powerupsToRemove = new ArrayList<>();
    public static List<EnemyBullet> enemy_bulletsToRemove = new ArrayList<>();
    public static List<Explosion> explosionsToRemove = new ArrayList<>();
    public EnemyBullet enemy_bullet;
    private Label stageLabel;
    private Label boot_label;
    private Label clock_label;
    private Label heart_label;
    public StageHandler stageHandler;
    private double WIDTH; 
    private double HEIGHT;
    private double random_angle;
    private int max;
    private int min;
    public Icon heart_icon;
    public Icon clock_icon;
    public Icon boot_icon;
    public Image speedup_img;
    public Image fireup_img;
    public Image heart_image;
    public Image boot_image;
    public DecimalFormat df;
    public Image clock_image;
    public SoundPlayer soundPlayer;
    Player player;
    
    public GamePane() {
    	soundPlayer = new SoundPlayer();
    	df = new DecimalFormat("#.##");
    	Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.WIDTH = size.getWidth();
        this.HEIGHT = size.getHeight();
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
        heart_label = new Label("null");
        heart_label.setFont(new Font("Impact", 13));
        heart_label.setTextFill(Color.WHITE);
        heart_label.setPadding(new Insets(10));
        heart_label.setLayoutX(60);
        heart_label.setLayoutY(HEIGHT-57);
        heart_image = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "//Images//heart.png.png");
        boot_label = new Label("null");
        boot_label.setFont(new Font("Impact", 13));
        boot_label.setTextFill(Color.WHITE);
        boot_label.setPadding(new Insets(10));
        boot_label.setLayoutX(60);
        boot_label.setLayoutY(HEIGHT-183);
        boot_image = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "//Images//boot.png.png");
        clock_label = new Label("null");
        clock_label.setFont(new Font("Impact", 13));
        clock_label.setTextFill(Color.WHITE);
        clock_label.setPadding(new Insets(10));
        clock_label.setLayoutX(60);
        clock_label.setLayoutY(HEIGHT-123);
        clock_image = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "//Images//clock.png.png");
        heart_icon = new Icon(40, 40, 20, HEIGHT-60, heart_image);
        clock_icon = new Icon(40, 40, 20, HEIGHT-120, clock_image);
        boot_icon = new Icon(40, 40, 20, HEIGHT-180, boot_image);

        backgroundImages = new ArrayList<>();
        fireup_img = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/fireup.png"); 
        speedup_img = new Image("file:" + Paths.get("").toAbsolutePath().toString() + "/Images/speedup.png"); 
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

        player = new Player(64, 64, (WIDTH - 50) / 2, HEIGHT - 70, shipImage, "player");

        getChildren().addAll(heart_icon, clock_icon, boot_icon, stageLabel, heart_label, boot_label, clock_label, player);
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
	                        double newY = imageView.getLayoutY() + (scrollSpeed +(stageHandler.getStage()/10));
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
        	try {
        		soundPlayer.play("\\Sounds\\laser.mp3");
        	}catch(Exception e){
        		
        	}
            
            getChildren().add(bullet);
            bullets.add(bullet);
            player.canShoot = false;
            Timeline delay = new Timeline(new KeyFrame(Duration.seconds(player.shootDelay), e -> player.canShoot = true));
            delay.play();
        }
         
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.isOutOfScreen()) {
                bulletsToRemove.add(bullet);
            } else {
            	for (PowerUp powerup : powerups) {
            		if (bullet.getBoundsInParent().intersects(powerup.getBoundsInParent())) {
            			if (powerup.getType() == "move_speed") {
            				player.Speed += .1;     				
            			}
            			if (powerup.getType() == "fire_speed") {
            				if(player.shootDelay > .15) {
            					player.shootDelay -= .07;           				
            				}   
            			}
            			powerupsToRemove.add(powerup);
            		}
            	}
                for (Enemy enemy : enemies) {
                    if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                    	if(Math.floor(Math.random() * (max - min + 1) + min) < 20) {
                    		    
                    		PowerUp powerup;
                    		if(Math.floor(Math.random() * (3 - 0 + 1) + 0) <= 1) {
                    			powerup = new PowerUp(36, 36, enemy.getXPos()+12, enemy.getYPos()+10, Color.BLUE,  "move_speed", speedup_img);
                    		} else {
                    			powerup = new PowerUp(33, 33, enemy.getXPos()+12, enemy.getYPos()+10, Color.BLUE,  "fire_speed", fireup_img);
                    		}
                    		
                    		powerups.add(powerup);
                    		getChildren().add(powerup);
                    	}
                    	Explosion explosion = new Explosion(enemy.getXPos(), enemy.getYPos());
                    	explosions.add(explosion);
                    	getChildren().add(explosion);
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
        bulletsToRemove.clear();
        enemiesToRemove.clear();
        powerupsToRemove.clear();
        
        for (Explosion explosion : explosions) {
        	if(explosion.animationDone()){
        		explosionsToRemove.add(explosion);
        		explosion.stopAnimation();
        	}
        }
        explosions.removeAll(explosionsToRemove);
        getChildren().removeAll(explosionsToRemove);
        explosionsToRemove.clear();
        
        for(Enemy enemy : enemies) {
            enemy.update();
            enemy.enemySpeed = enemy.enemySpeed + (stageHandler.getStage()/10);
            if((Math.floor(Math.random() *(1500 - 0 + 1) + 0)) < (enemy.fire_rate+(stageHandler.getStage()))) {
            	random_angle = Math.floor(Math.random() * (player.spread_max - player.spread_min + 1) + player.spread_min);
            	if (enemy.e_type == 0) {
            		enemy_bullet = new EnemyBullet(2, 32, enemy.getXPos()+32, enemy.getYPos()+16, Color.rgb(173, 247, 183, 0.5), random_angle, enemy.e_type);
            	}else if (enemy.e_type == 1) {
            		enemy_bullet = new EnemyBullet(2, 32, enemy.getXPos()+32, enemy.getYPos()+16, Color.rgb(81, 222, 232, 0.5), random_angle, enemy.e_type);
            	}else {
            		enemy_bullet = new EnemyBullet(2, 32, enemy.getXPos()+32, enemy.getYPos()+16, Color.rgb(237, 153, 69, 0.5), random_angle, enemy.e_type);
            	}
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
        enemy_bulletsToRemove.clear();
        
        heart_icon.update();
        
        
        if(stageHandler.isNewStage()) {
        	stageHandler.generateNewStage();
            enemies = stageHandler.getEnemies();
            getChildren().addAll(enemies);
        }
        stageLabel.setText("Stage: " + stageHandler.getStage());
        stageLabel.setLayoutX((WIDTH - stageLabel.getWidth()) / 2);
        heart_label.setText(player.lives+"/5");
        boot_label.setText(df.format(player.Speed)+"");
        clock_label.setText(df.format(player.shootDelay)+"");
        
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
    	player.lives = 5;
    	player.shootDelay = 1.1;
    	player.Speed = 10;
    	player.xPos = (WIDTH - 50) / 2;
    	player.yPos = HEIGHT - 70;
    }
    
    private void render() {
        player.render();
    }
}