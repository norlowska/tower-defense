package towerdefense;

public class GameMap {

	Map map = new Map();
	
	public GameMap(String name) {
		map.readMapLayout(name);
	}
	
	
}
