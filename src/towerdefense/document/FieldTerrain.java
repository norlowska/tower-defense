package towerdefense.document;

public class FieldTerrain extends Field {
    private TowerFactoryInterface tower;

    public FieldTerrain(boolean start, boolean finish, Color color, Point point, TowerFactoryInterface tower) {
        super(start, finish, color, point);
        this.tower = tower;
    }

    public TowerFactoryInterface getTower() {
        return tower;
    }

    public void setTower(TowerFactoryInterface tower){this.tower = tower;}

    public boolean isRoad(){return false;}
}
