package towerdefense.document.towers;

import towerdefense.document.Tower;

import java.io.IOException;

public class NuclearTower extends Tower implements TowerF {

	public NuclearTower() {
		super(12, 100,7, 1.1);
		super.setName("Nuclear");
	}

	@Override
	public void setIcon() {
		super.setIcon("data/towers/NuclearTower.txt");
	}

	@Override
	public void setImage() {
		try {
			super.setImage("nuclear.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Factory extends TowerFactory{
		protected TowerF create(){
			return new NuclearTower();
		}

	}
	static {TowerFactory.addFactory("NuclearTower", new Factory());}
}
