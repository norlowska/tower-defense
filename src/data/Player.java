package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import towerdefense.document.Map;
import towerdefense.document.Tower;
import towers.ArcherTower;

public class Player {
	protected String nickname;
	protected ArrayList<Tower> towers;
	protected Map lastMap;
	protected int money;
	
	public Player(String nickname, Map lastMap) {
		this.nickname = nickname;
		this.money = 500;
		this.lastMap = lastMap;
		towers = new ArrayList<Tower>();
		towers.add(new ArcherTower());
		//savePlayer();
	}
	
	public void savePlayer() {
		try {
		File file = new File(nickname + ".txt");
		FileWriter write = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(write);
		bw.write(nickname);
		bw.newLine();
		String result = new String();
		for(Tower x: towers) {
			result = result + x.getName();
		}
		bw.write(result);
		
		}catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + nickname + ".txt" + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + nickname + ".txt "+ "'");
		}
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public List<Tower> getTowers() {
		return towers;
	}
	
	public Tower getTower(int index) {
		return towers.get(index);
	}

	public void setTowers(List<Tower> towers) {
		this.towers = towers;
	}

	public Map getLastMap() {
		return lastMap;
	}

	public void setLastMap(Map lastMap) {
		this.lastMap = lastMap;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	
}
