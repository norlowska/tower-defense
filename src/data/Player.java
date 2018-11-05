package data;

import java.util.ArrayList;
import java.util.List;
import towerdefense.Map;
import towerdefense.Tower;

public class Player {
	protected String nickname;
	protected List<Tower> towers;
	protected Map lastMap;
	protected int money;
	
	public Player(String nickname, Map lastMap) {
		this.nickname = nickname;
		this.money = 500;
		this.lastMap = lastMap;
		towers = new ArrayList<Tower>();
	}
}
