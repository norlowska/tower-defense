package towerdefense;

import com.googlecode.lanterna.TextColor;

public class Terrain {
	
	static TextColor.ANSI color;
	
	public Terrain() {
		
	}
	
	public void setColor(int color) {
		if(color == 1) {
			this.color = TextColor.ANSI.GREEN;
		}else this.color = TextColor.ANSI.BLACK;
	}
}
