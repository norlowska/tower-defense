package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class FireTower extends Tower {

	public FireTower() {
		super("DRAGON", TextColor.ANSI.GREEN, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/FireTower.txt");
	}

}
