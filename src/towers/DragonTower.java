package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.Tower;

public class DragonTower extends Tower {

	public DragonTower() {
		super("DRAGON", TextColor.ANSI.GREEN, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/DragonTower.txt");
	}

}
