package towerdefense.document;

import com.googlecode.lanterna.TextColor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Enemy implements IFlyweight {

    protected final int maxHealth;
    protected final double speed;
    protected String icon;
    protected String name;
    protected Color color;

    public Enemy(int maxHealth, double speed, Color color, String filename) {
        this.maxHealth = maxHealth;
        this.speed = speed;
        setIcon(filename);
        this.color = color;
    }

    public String getName() {
        return name;
    }
    public int getMaxHealth() {
        return maxHealth;
    }

    public double getSpeed() {
        return speed;
    }

    protected void setIcon(String filename) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        int nrLine = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

            while ((line = bufferedReader.readLine()) != null || nrLine < 4) {
                sb.append(line);
                nrLine++;
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" + filename + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '" + filename + "'");
        }
        this.icon = sb.toString();
    }

    @Override
    public void checkColorUpdate(int currentHealth) {

    }
}
