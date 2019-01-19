package towerdefense.document.towers;

import towerdefense.document.Tower;

import java.io.IOException;

public class WaterTower extends Tower implements TowerF {

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
			super.setImage("water.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Factory extends TowerFactory{
		protected TowerF create(){
			return new WaterTower();
		}

	}
	static {TowerFactory.addFactory("WaterTower", new Factory());}
}
