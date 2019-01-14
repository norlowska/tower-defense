package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class ForceTower extends Tower {

	public ForceTower() {
		super("BALLISTA", TextColor.ANSI.BLUE, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/ForceTower.txt");
	}
}
