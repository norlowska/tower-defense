package towerdefense;

import com.googlecode.lanterna.TextColor;

public class Terrain {
	
	int color;
	
	public Terrain() {
		
	}
	
	public void setColor(int color) {
		if(color == 1) {
			this.color = 1;
		}else  {
			this.color = 0;
		}
	}
	
	public int getColor() {
		return color;
	}
}
