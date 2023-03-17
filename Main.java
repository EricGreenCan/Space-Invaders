package application;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    
    private Stage primaryStage;
    private Screen screen;
    private Rectangle2D bounds;
    private double WIDTH; 
    private double HEIGHT;
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.screen = Screen.getPrimary();
        this.bounds  = screen.getVisualBounds();
        this.WIDTH = bounds.getWidth();
        this.HEIGHT = bounds.getHeight();
        showMainMenu();
    }
    
    public void showMainMenu() {

        MenuPane menuPane = new MenuPane();
        menuPane.setOnPlayClicked(event -> showGame());
        Scene scene = new Scene(menuPane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    
    public void showGame() {
        GamePane gamePane = new GamePane();
        Scene scene = new Scene(gamePane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.show();
        gamePane.runGame();
    }
    
    public void showGameOverMenu() {
        // TODO
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}