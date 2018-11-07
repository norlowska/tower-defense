package towers;

import java.util.ArrayList;

import towerdefense.Bullet;
import towerdefense.Tower;

public class MachineGunTower extends Tower {

	public MachineGunTower() {
		super(new ArrayList<Bullet>(), 7, 12);
		name = "MACHINEGUN";
		setIcon();
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/MachineGunTower.txt");
	}

}
