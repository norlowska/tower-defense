package towerdefense.document;

import com.googlecode.lanterna.TextColor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Enemy implements IFlyweight {

    protected final int maxHealth;
    protected final double speed;
    protected String name;
    protected Color color;

    public Enemy(int maxHealth, double speed, Color color) {
        this.maxHealth = maxHealth;
        this.speed = speed;
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

    public Color getColor() {
        return color;
    }

    @Override
    public void checkColorUpdate(int currentHealth) {
        double result = (double)currentHealth/(double)maxHealth;
        if(result <= 0.1) {
            color = Color.RED;
        }
    }
}
