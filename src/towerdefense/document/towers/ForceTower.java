package towerdefense.document.towers;

import towerdefense.document.Tower;
import towerdefense.document.TowerFactoryInterface;
import towerdefense.document.TowerFactory;

import java.io.IOException;

public class ForceTower extends Tower implements TowerFactoryInterface {

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

	private static class Factory extends TowerFactory {
		protected TowerFactoryInterface create(){
			return new ForceTower();
		}

	}
	static {TowerFactory.addFactory("ForceTower", new Factory());}
}
