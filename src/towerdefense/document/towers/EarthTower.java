package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class EarthTower extends Tower {

	public EarthTower() {
		super("LASER", TextColor.ANSI.RED, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/EarthTower.txt");
	}

}
