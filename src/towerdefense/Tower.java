package towerdefense;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.googlecode.lanterna.TextColor;

import data.Player;

public abstract class Tower {
	protected String name;
	protected List<Bullet> bullets;
	protected int range;
	protected int power;
	protected int cost;
	protected String icon;
	protected TextColor color;
	
	public Tower(List<Bullet> bullets, int range, int power) {
		this.bullets = bullets;
		this.range = range;
		this.power = power;
	}
	
	public List<Bullet> getBullets() {
		return bullets;
	}
	public int getRange() {
		return range;
	}
	public int getPower() {
		return power;
	}
	public String getIcon() {
		return icon;
	}
	public TextColor getColor() {
		return color;
	}
	
	public String getName() {
		return name;
	}
	
	protected void setIcon(String filename) {
		StringBuilder sb = new StringBuilder();
		String line = null;
		int nrLine = 0;
		 try {
	            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

	            while((line = bufferedReader.readLine()) != null || nrLine <4) {
	                sb.append(line);
	                nrLine++;
	            }
	            bufferedReader.close();         
	        }
	        catch(FileNotFoundException ex) {
	            System.out.println(
	                "Unable to open file '" + filename + "'");                
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" + filename + "'");                  
	        }
		 this.icon = sb.toString();
	}
	
	abstract public void setIcon();

}
