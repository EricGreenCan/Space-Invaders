package application;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GamePane extends Pane {

    private Timeline gameLoop;
    private PauseMenu pauseMenu;
    private KeyListener keyListener;
    private boolean toggle;
    public static List<Bullet> bullets = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Label stageLabel;
    private StageHandler stageHandler;
    Player player;
    
    public GamePane() {
    	stageHandler = new StageHandler();
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Animation.INDEFINITE);
        pauseMenu = new PauseMenu(this);
        pauseMenu.hide();
        toggle = false;
        
        stageLabel = new Label("Stage: " + stageHandler.getStage()); 
        stageLabel.setFont(new Font("Arial", 30));
        stageLabel.setPadding(new Insets(10));

        player = new Player(64, 64, (screenSize.getWidth() - 50) / 2, screenSize.getHeight() - 70, Color.RED, "player");
   
        //add game objects to game pane
        getChildren().addAll(stageLabel, player);
    }  

    public void runGame() {
        keyListener = new KeyListener(getScene());
        keyListener.start(); // start the KeyListener
        Duration frameTime = Duration.seconds(1.0 / 60.0);    //60 FPS  
        KeyFrame keyFrame = new KeyFrame(frameTime, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (keyListener.isPaused()) {
                    if(!toggle) {
                    	keyListener.downKeys.clear();
                    	toggle = true;
                    	pauseMenu.show(keyListener);
                    }
                } else {
                	toggle = false;
                    update();
                    render();
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
        
        if(keyListener.isKeyPressed("SPACE")) {
            player.getXPos();
            player.getYPos();
            Bullet bullet = new Bullet(3, 50, player.getXPos()+32, player.getYPos()-16, Color.BLUE, "bullet");
            getChildren().add(bullet);
            bullets.add(bullet);
        }
        
        // check for bullet-enemy collisions
        List<Bullet> bulletsToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();
        for (Bullet bullet : bullets) {
            bullet.update();
            if (bullet.isOutOfScreen()) {
                bulletsToRemove.add(bullet);
            } else {
                for (Enemy enemy : enemies) {
                    if (bullet.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        bulletsToRemove.add(bullet);
                        enemiesToRemove.add(enemy);
                        stageHandler.destroyEnemy();
                    }
                }
            }
        }
        bullets.removeAll(bulletsToRemove);
        enemies.removeAll(enemiesToRemove);
        getChildren().removeAll(bulletsToRemove);
        getChildren().removeAll(enemiesToRemove);
        
        for(Enemy enemy : enemies) {
            enemy.update();
        }
        
        //manage stage related logic and events
        //stageHandler.update();
        if(stageHandler.isNewStage()) {
        	stageHandler.generateNewStage();
            System.out.println("added enemies");
            enemies = stageHandler.getEnemies();
            getChildren().addAll(enemies);
        }
        stageLabel.setText("Stage: " + stageHandler.getStage());
    }
    
    private void render() {
        player.render();
    }
}