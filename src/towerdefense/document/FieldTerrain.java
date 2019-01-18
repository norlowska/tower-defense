package towerdefense.document;

public class FieldTerrain extends Field {
    private Tower tower;

    public FieldTerrain(boolean start, boolean finish, Color color, Tower tower) {
        super(start, finish, color);
        this.tower = tower;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower){this.tower = tower;}

    public boolean isRoad(){return false;}
}
