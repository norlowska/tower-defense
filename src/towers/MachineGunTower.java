package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.Tower;

public class MachineGunTower extends Tower {

	public MachineGunTower() {
		super("MACHINEGUN", TextColor.ANSI.BLACK, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/MachineGunTower.txt");
	}

}
