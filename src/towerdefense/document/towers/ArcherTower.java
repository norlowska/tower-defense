package towerdefense.document.towers;

import com.googlecode.lanterna.TextColor;

import towerdefense.document.Color;
import towerdefense.document.Tower;

import java.io.IOException;

public class ArcherTower extends Tower {

	public ArcherTower() {
		super(12, 100,7, 1.1);
		super.setName("Archer");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/ArcherTower.txt");
	}

	@Override
	public void setImage(){
		try {
			super.setImage("data/towersPNG/archer.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
