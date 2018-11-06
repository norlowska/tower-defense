package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class ArcherTower extends Tower {

	public ArcherTower() {
		super(new ArrayList<Bullet>(), 7, 12);
	}

	public void setIcon() {
		super.setIcon("data/ArcherTower.txt");
	}

}
