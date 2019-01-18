package towerdefense.document;

import com.googlecode.lanterna.TextColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class Enemy implements IFlyweight {

    protected int maxHealth;
    protected double speed;
    protected String name;
    protected Color color;
    protected Image image;

    public Enemy(int maxHealth, double speed) {
        this.maxHealth = maxHealth;
        this.speed = speed;
        this.color = Color.MAGENTA;
        try {
            this.image = ImageIO.read(new FileInputStream(new File("data/monster.png")));
            if(this.image != null) {
                System.out.println("Odczytano potwora");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Image getImage() {
        return image;
    }

    @Override
    public void checkColorUpdate(int currentHealth) {
        double result = (double)currentHealth/(double)maxHealth;
        if(result <= 0.1) {
            color = Color.RED;
        }
    }
}
