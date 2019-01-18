package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

import java.io.IOException;

public class ForceTower extends Tower {

	public ForceTower() {
		super(12, 100,7, 1.1);
		super.setName("Force");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/ForceTower.txt");
	}

	@Override
	public void setImage() {
		try {
			super.setImage("force.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
