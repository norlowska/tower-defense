package towerdefense;

public class Mode {
	protected String name;
	protected double damageRate;
	protected double maxHealthRate;
	protected double pendingEnemiesRate;
	
	public Mode(String name, double damageRate, double maxHealthRate, double pendingEnemiesRate) {
		this.name = name;
		this.damageRate = damageRate;
		this.maxHealthRate = maxHealthRate;
		this.pendingEnemiesRate = pendingEnemiesRate;
	}
}
