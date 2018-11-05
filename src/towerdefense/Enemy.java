package towerdefense;

public class Enemy {
	protected final int maxHealth;
	protected int currentHealth;
	protected final int speed;
	
	public Enemy(int maxHealth, int speed) {
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.speed = speed;
	}
	
	public int getCurrentHealth() {
		return currentHealth;
	}
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getSpeed() {
		return speed;
	}
	
	
	
	
}
