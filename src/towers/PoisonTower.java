package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Tower;

public class PoisonTower extends Tower {

	public PoisonTower() {
		super("POISON", TextColor.ANSI.MAGENTA, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/PoisonTower.txt");
	}

}
