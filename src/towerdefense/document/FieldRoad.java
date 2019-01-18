package towerdefense.document;


public class FieldRoad extends Field {
    private Enemy enemy;
    private int currentHealth;

    public FieldRoad(boolean start, boolean finish, Color color, Enemy enemy) {
        super(start, finish, color);
        this.enemy = enemy;
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
}
