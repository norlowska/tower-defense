package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

public class ElectricTower extends Tower {

	public ElectricTower() {
		super(12, 100,7, 1.1);
		super.setName("Electric");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/ElectricTower.txt");
	}

}