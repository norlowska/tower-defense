package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class LaserTower extends Tower {

	public LaserTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "LASER";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/LaserTower.txt");
	}

}
