package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class CannonTower extends Tower {

	public CannonTower() {
		super("CANNON", TextColor.ANSI.YELLOW, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/CannonTower.txt");
	}

}
