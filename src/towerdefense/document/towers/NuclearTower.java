package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class NuclearTower extends Tower {

	public NuclearTower() {
		super("WIZARD", TextColor.ANSI.CYAN, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/NuclearTower.txt");
	}

}
