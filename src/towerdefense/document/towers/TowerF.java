package towerdefense.document.towers;

import towerdefense.document.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * Created by user on 2019-01-19.
 */
public interface TowerF {
    String getIcon();
    towerdefense.document.Color getColor();
    int getPrice();
    int getRange();
    String getName();
    int getDamage();
    Image getImage();
}
