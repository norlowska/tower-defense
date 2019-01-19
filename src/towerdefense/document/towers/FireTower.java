package towerdefense.document.towers;

import towerdefense.document.Tower;
import towerdefense.document.TowerFactoryInterface;
import towerdefense.document.TowerFactory;

import java.io.IOException;

public class FireTower extends Tower implements TowerFactoryInterface {

	public FireTower() {
		super(12, 100,7, 1.1);
		super.setName("Fire");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/FireTower.txt");
	}

	@Override
	public void setImage() {
		try {
			super.setImage("fire.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Factory extends TowerFactory {
		protected TowerFactoryInterface create(){
			return new FireTower();
		}

	}
	static {TowerFactory.addFactory("FireTower", new Factory());}
}
