package towerdefense;

public class Field {

	Enemy enemy;
	Terrain terrain;
	Tower tower;
	int start;
	int finish;
	int displayEnemyCount = 0;
	
	public Field(Terrain terrain, int start, int finish) {
		this.terrain = terrain;
		this.start = start;
		this.finish = finish;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	public Tower getTower() {
		return tower;
	}

	public void setTower(Tower tower) {
		this.tower = tower;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getFinish() {
		return finish;
	}

	public void setFinish(int finish) {
		this.finish = finish;
	}
	
	public void deleteEnemy() {
		enemy = null;
	}
	
	public void deleteTower() {
		tower = null;
	}
	
	
	
	
}
