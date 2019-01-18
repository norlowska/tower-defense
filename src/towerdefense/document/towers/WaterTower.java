package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

import java.io.IOException;

public class WaterTower extends Tower {

	public WaterTower() {
		super(12, 100,7, 1.1);
		super.setName("Water");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/WaterTower.txt");
	}

	@Override
	public void setImage() {
		try {
			super.setImage("data/towersPNG/water.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
