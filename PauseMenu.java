package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PauseMenu {

    private Stage stage;
    private Button resumeButton;
    private Button quitButton;
    
    public PauseMenu() {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Paused");
        
        resumeButton = new Button("Resume");
        resumeButton.setOnAction(e -> {
            stage.close();
        });
        
        quitButton = new Button("Quit");
        quitButton.setOnAction(e -> {
            System.exit(0);
        });
        
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(resumeButton, quitButton);
        
        Scene scene = new Scene(root, 200, 100);
        stage.setScene(scene);
    }
    
    public void show() {
        stage.show();
    }
    
    public void hide() {
        stage.hide();
    }
}