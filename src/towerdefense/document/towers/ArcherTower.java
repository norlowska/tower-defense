package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

public class ArcherTower extends Tower {

	public ArcherTower() {
		super(12, 100,7, 1.1);
		super.setName("Archer");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/ArcherTower.txt");
	}

}
