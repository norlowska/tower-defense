package towerdefense.document;


public class FieldRoad extends Field {
    private Enemy enemy;
    private int whereMove;
    private int currentHealth;

    public FieldRoad(boolean start, boolean finish, Color color, Enemy enemy, int whereMove) {
        super(start, finish, color);
        this.enemy = enemy;
        this.whereMove = whereMove;
        if(enemy != null) {
            currentHealth = enemy.getMaxHealth();
        } else {
            currentHealth = 0;
        }
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public boolean isRoad(){return true;}

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public Point move(Point point){

        switch(whereMove){
            case 1:
                point.setY(point.getY() - 1);
                break;
            case 2:
                point.setX(point.getX() + 1);
                break;
            case 3:
                point.setY(point.getY() + 1);
                break;
            case 4:
                point.setX(point.getX() - 1);
                break;
            default:
                System.out.println("Zly whereMove");
                break;
        }
        enemy = null;
        return point;
    }
}
