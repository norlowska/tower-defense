package towerdefense.document;

import towerdefense.document.towers.TowerF;

public class FieldTerrain extends Field {
    private TowerF tower;

    public FieldTerrain(boolean start, boolean finish, Color color, Point point, TowerF tower) {
        super(start, finish, color, point);
        this.tower = tower;
    }

    public TowerF getTower() {
        return tower;
    }

    public void setTower(TowerF tower){this.tower = tower;}

    public boolean isRoad(){return false;}
}
