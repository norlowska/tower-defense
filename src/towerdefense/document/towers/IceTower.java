package towerdefense.document.towers;

import towerdefense.document.Tower;
import towerdefense.document.TowerFactoryInterface;
import towerdefense.document.TowerFactory;

import java.io.IOException;

public class IceTower extends Tower implements TowerFactoryInterface {

	public IceTower() {
		super(12, 100,7, 1.1);
		super.setName("Ice");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/IceTower.txt");
	}

	@Override
	public void setImage() {
		try {
			super.setImage("ice.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Factory extends TowerFactory {
		protected TowerFactoryInterface create(){
			return new IceTower();
		}

	}
	static {TowerFactory.addFactory("IceTower", new Factory());}
}
