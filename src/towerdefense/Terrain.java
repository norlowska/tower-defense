package towerdefense;

import com.googlecode.lanterna.TextColor;

public class Terrain {
	
	static TextColor.ANSI color;
	
	public Terrain(boolean color) {
		if(color) {
			this.color = TextColor.ANSI.GREEN;
		}else this.color = TextColor.ANSI.BLACK;
	}
	

}
