package towerdefense;

public class Field {

	Enemy enemy;
	Terrain terrain;
	Tower tower;
	int start;
	int finish;
	
	public Field(Terrain terrain, int start, int finish) {
		this.terrain = terrain;
		this.start = start;
		this.finish = finish;
	}
	
}
