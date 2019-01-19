package towerdefense.document.towers;

import towerdefense.document.Tower;
import towerdefense.document.TowerFactoryInterface;
import towerdefense.document.TowerFactory;

import java.io.IOException;

public class EarthTower extends Tower implements TowerFactoryInterface {

    public EarthTower() {
        super(12, 100, 7, 1.1);
        super.setName("Earth");
    }

    @Override
    public void setIcon() {
        super.setIcon("data/towers/EarthTower.txt");
    }

    @Override
    public void setImage() {
        try {
            super.setImage("earth.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Factory extends TowerFactory {
        protected TowerFactoryInterface create(){
            return new EarthTower();
        }

    }
    static {TowerFactory.addFactory("EarthTower", new Factory());}
}
