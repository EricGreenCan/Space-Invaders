package application;

import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {

	private static final Pane root = new Pane();
	private static double t = 0;
	private static final int enemies = 5;
	private static int lives = 3;
	private static int kills = 0;
	private static Sprite player = new Sprite(50, 50, 375, 400, Color.BLACK, "player");

	private static Parent createContent() {
		root.getChildren().add(player);
		createEnemies();
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
					player.dead = true;
					lives--;
					gameOver();
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
		for (int i = 0; i < 6; i++) {
			root.getChildren().add(new Sprite(50, 50, 75 + i * 150, 100, Color.ORANGE, "enemy"));
		}

	}
	
	private static void gameOver() {
		if (lives == 0) {
			
		}
	}

	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(createContent(), 800, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.setOnKeyPressed(event -> {
			switch (event.getCode()) {
			case LEFT:
				player.moveLeft();
				break;
			case RIGHT:
				player.moveRight();
				break;
			case SPACE:
				player.shoot();
				break;
			}
		});
	}

	private static class Sprite extends Rectangle {

		private double x;
		private double y;
		private String type;
		private boolean dead = false;

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
			setLayoutY(getLayoutY() - 5);
		}

		public void moveDown() {
			setLayoutY(getLayoutY() + 5);
		}

		public void shoot() {
			root.getChildren()
					.add(new Sprite(5, 20, getLayoutX() + 25, getLayoutY(), Color.GREENYELLOW, type + "bullet"));
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
