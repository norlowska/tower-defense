package towerdefense.document.towers;

import towerdefense.document.Tower;
import towerdefense.document.TowerFactoryInterface;
import towerdefense.document.TowerFactory;

import java.io.IOException;

public class WaterTower extends Tower implements TowerFactoryInterface {

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

	private static class Factory extends TowerFactory {
		protected TowerFactoryInterface create(){
			return new WaterTower();
		}

	}
	static {TowerFactory.addFactory("WaterTower", new Factory());}
}
