package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

public class NuclearTower extends Tower {

	public NuclearTower() {
		super(12, 100,7, 1.1);
		super.setName("Nuclear");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/NuclearTower.txt");
	}

}
