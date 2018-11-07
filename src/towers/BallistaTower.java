package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class BallistaTower extends Tower {

	public BallistaTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "BALLISTA";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/BallistaTower.txt");
	}
}
