package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class PoisonTower extends Tower {

	public PoisonTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "POISON";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/PoisonTower.txt");
	}
	
}
