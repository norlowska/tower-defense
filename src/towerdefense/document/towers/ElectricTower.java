package towerdefense.document.towers;

import towerdefense.document.Tower;

import java.io.IOException;

public class ElectricTower extends Tower implements TowerF {

	public ElectricTower() {
		super(12, 100,7, 1.1);
		super.setName("Electric");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/ElectricTower.txt");
	}

	@Override
	public void setImage() {
		try {
			super.setImage("electric.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Factory extends TowerFactory{
		protected TowerF create(){
			return new ElectricTower();
		}

	}
	static {TowerFactory.addFactory("ElectricTower", new Factory());}
}
