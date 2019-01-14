package towerdefense.document;


public class FieldRoad extends Field {
    private Enemy enemy;
    private int currentHealth;

    public FieldRoad(boolean start, boolean finish, Color color, Enemy enemy) {
        super(start, finish, color);
        this.enemy = enemy;
        currentHealth = enemy.getMaxHealth();
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
