package towerdefense.document.towers;

import towerdefense.document.Tower;
import towerdefense.document.TowerFactoryInterface;
import towerdefense.document.TowerFactory;

import java.io.IOException;

public class ArcherTower extends Tower implements TowerFactoryInterface {

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
			super.setImage("archer.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Factory extends TowerFactory {
		protected TowerFactoryInterface create(){
			return new ArcherTower();
		}
	}
	static {TowerFactory.addFactory("ArcherTower", new Factory());}
}
