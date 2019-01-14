package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class IceTower extends Tower {

	public IceTower() {
		super("POISON", TextColor.ANSI.MAGENTA, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towerdefense.document.towers/IceTower.txt");
	}

}
