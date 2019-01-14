package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

public class ArcherTower extends Tower {

	public ArcherTower() {
		super(12, 100,7, 1.1, Color.WHITE);
	}

	public void setName() {
		super.setName(this.getClass().getName());
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/ArcherTower.txt");
	}

}
