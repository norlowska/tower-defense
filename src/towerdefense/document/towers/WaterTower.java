package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class WaterTower extends Tower {

	public WaterTower() {
		super("MACHINEGUN", TextColor.ANSI.BLACK, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/WaterTower.txt");
	}

}
