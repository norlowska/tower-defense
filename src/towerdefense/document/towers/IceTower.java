package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

public class IceTower extends Tower {

	public IceTower() {
		super(12, 100,7, 1.1);
		super.setName("Ice");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/IceTower.txt");
	}

}
