package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class ArcherTower extends Tower {

	public ArcherTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "ARCHER";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/ArcherTower.txt");
	}

}
