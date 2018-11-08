package towerdefense;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Map {
	protected String colorScheme;
	//Terrain terrain = new Terrain();
	ArrayList<ArrayList<Field>> map = new ArrayList<ArrayList<Field>>();
	
	
	public ArrayList<ArrayList<Field>> readMapLayout(String name) {
		String mapName = "data/"+ name +".txt";
		String line = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(mapName));
			
			int numberOfArrays = 0;
			while ((line = bufferedReader.readLine()) != null) {
				map.add(new ArrayList<Field>());
				String[] splited = line.split(" ");
				
				for(int i = 0;i<splited.length; i++) {
					Terrain terrain = new Terrain();
					if(Character.getNumericValue(splited[i].charAt(0)) == 1) {
						terrain.setColor(1);
					}else {
						terrain.setColor(0);
					}
					Field field = new Field(terrain, Character.getNumericValue(splited[i].charAt(1)),
							Character.getNumericValue(splited[i].charAt(2)));
					map.get(numberOfArrays).add(field);
				}
				numberOfArrays++;
			}
			bufferedReader.close();
			return map;
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + mapName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + mapName + "'");
		}
		return map;
	}
	
	public void addTower(Tower tower, int x, int y) {
		map.get(y).get(x).setTower(tower);
		
	}
	
	public void addEnemy(Enemy enemy, int x, int y) {
		map.get(y).get(x).setEnemy(enemy);
		
	}
	
	public void deleteTower(int x, int y) {
		map.get(y).get(x).deleteTower();
		
	}
	
	public void deleteEnemy(int x, int y) {
		map.get(y).get(x).deleteEnemy();
		
	}
	
}
