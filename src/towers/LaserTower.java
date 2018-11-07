package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.Tower;

public class LaserTower extends Tower {

	public LaserTower() {
		super("LASER", TextColor.ANSI.RED, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/LaserTower.txt");
	}

}
