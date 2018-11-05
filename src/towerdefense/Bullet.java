package towerdefense;

public class Bullet {
	protected Enemy target;
	protected int speed;
	protected String icon;
	
	public Enemy getTarget() {
		return target;
	}
	public void setTarget(Enemy target) {
		this.target = target;
	}
	public int getSpeed() {
		return speed;
	}
	public String getIcon() {
		return icon;
	}
}
