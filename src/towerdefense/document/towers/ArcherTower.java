package towerdefense.document.towers;

import towerdefense.document.Tower;

import java.io.IOException;

public class ArcherTower extends Tower implements TowerF {

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

	private static class Factory extends TowerFactory{
		protected TowerF create(){
			return new ArcherTower();
		}
	}
	static {TowerFactory.addFactory("ArcherTower", new Factory());}
}
