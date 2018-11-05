package towerdefense;

import java.util.List;

public class Tower {
	protected List<Bullet> bullets;
	protected int range;
	protected int power;
	protected int cost;
	protected String icon;
	
	public Tower(List<Bullet> bullets, int range, int power) {
		this.bullets = bullets;
		this.range = range;
		this.power = power;
	}
	
	public List<Bullet> getBullets() {
		return bullets;
	}
	public int getRange() {
		return range;
	}
	public int getPower() {
		return power;
	}
}
