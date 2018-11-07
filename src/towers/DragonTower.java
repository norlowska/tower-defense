package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class DragonTower extends Tower {

	public DragonTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "DRAGON";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/DragonTower.txt");
	}

}
