package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.Tower;

public class BallistaTower extends Tower {

	public BallistaTower() {
		super("BALLISTA", TextColor.ANSI.BLUE, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/BallistaTower.txt");
	}
}
