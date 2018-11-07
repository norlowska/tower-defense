package towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.Tower;

public class WizardTower extends Tower {

	public WizardTower() {
		super("WIZARD", TextColor.ANSI.CYAN, 7, 12);
	}

	@Override
	public void setIcon() {
		super.setIcon("assets/towers/WizardTower.txt");
	}

}
