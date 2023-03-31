package application;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class StageHandler {
	
	private int currentStage;
	private double HEIGHT;
	private int numStageEnemies; //number of enemies that will spawn in current stage
	private int enemiesLeft; //number of enemies left in stage
	private ArrayList<Enemy> enemies;
	
	public StageHandler(double height) {
		HEIGHT = height;
		enemiesLeft = 0;
		enemies = new ArrayList<>();
		currentStage = 0;
		numStageEnemies = 12;
	}
	
	public void update() 
	{
		//System.out.println("Updated stage");
		if (isNewStage()) {
			System.out.println("New stage");
			
			
			
			//System.out.println(enemiesLeft);
			
		}
	}
	
	public void generateNewStage() {
	    currentStage += 1;
	    enemiesLeft = numStageEnemies;
	    enemies.clear();
	    
		double enemyWidth = 64;
	    double enemyHeight = 64;
	    double gap = 80;
	    double startX = gap;
	    double startY = 50;
	    
	    for (int i = 0; i < numStageEnemies; i++) {
	        double xPos = startX + i * (enemyWidth + gap);
	        double yPos = startY;
	        Enemy enemy = new Enemy(enemyWidth, enemyHeight, xPos, yPos, HEIGHT,  "Basic");
	        enemies.add(enemy);
	    }
	}
	
	public void destroyEnemy() {
		System.out.println(enemiesLeft);
		enemiesLeft -= 1;
		
	}
	
	public boolean isNewStage() {
		if (enemiesLeft < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public int getStage() 
	{
		return currentStage;
	}
}