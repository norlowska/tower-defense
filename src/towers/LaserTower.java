package towers;

import java.util.ArrayList;
import java.util.List;

import towerdefense.Bullet;
import towerdefense.Tower;

public class LaserTower extends Tower {

	public LaserTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		// TODO Auto-generated constructor stub
		setIcon();
	}

	public void setIcon() {
		super.setIcon("data/LaserTower.txt");
	}

}
