package towerdefense.document;

import com.googlecode.lanterna.TextColor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class Tower {

    int damage;
    int price;
    int range;
    String icon;
    String name;
    Color color;
    double speed;

    public Tower(int damage, int price, int range, double speed, Color color) {
        this.damage = damage;
        this.price = price;
        this.range = range;
        setIcon();
        this.speed = speed;
        this.color = color;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getIcon() {
        return icon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setName(String name) {
        this.name = name;
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

    abstract public void setIcon();

}
