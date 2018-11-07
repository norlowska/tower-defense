package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class WizardTower extends Tower {

	public WizardTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "WIZARD";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/WizardTower.txt");
	}

}
