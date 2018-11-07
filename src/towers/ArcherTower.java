package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.Tower;

public class ArcherTower extends Tower {

	public ArcherTower() {
		super("ARCHER", TextColor.ANSI.WHITE, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/ArcherTower.txt");
	}

}
