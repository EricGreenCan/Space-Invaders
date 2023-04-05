package application;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

public class StageHandler {
	
	public int currentStage;
	private double HEIGHT;
	private double WIDTH;
	private int numStageEnemies; //number of enemies that will spawn in current stage
	public int enemiesLeft; //number of enemies left in stage
	private ArrayList<Enemy> enemies;
	public Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	
	public StageHandler(double height) {
		HEIGHT = height;
		WIDTH = size.getWidth();
		enemiesLeft = 0;
		enemies = new ArrayList<>();
		currentStage = 0;
		numStageEnemies = 12;
	}
	
	public void update() 
	{
		//NA
	}
	
	public void generateNewStage() {
	    currentStage += 1;
	    enemiesLeft = numStageEnemies;
	    enemies.clear();
	    
		double enemyWidth = 64;
	    double enemyHeight = 64;
	    double gap = WIDTH/(numStageEnemies*2.2);
	    double startX = gap;
	    double startY = -125;
	    
	    for (int i = 0; i < numStageEnemies; i++) {
	        double xPos = startX + i * (enemyWidth + gap);
	        double yPos = startY;
	        Enemy enemy = new Enemy(enemyWidth, enemyHeight, xPos, yPos, HEIGHT,  "Basic");
	        enemies.add(enemy);
	    }
	}
	
	public void destroyEnemy() {
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