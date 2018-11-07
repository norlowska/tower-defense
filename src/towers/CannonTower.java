package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class CannonTower extends Tower {

	public CannonTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "CANNON";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/CannonTower.txt");
	}

}
