package towerdefense.document;

import towerdefense.document.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

/**
 * Created by user on 2019-01-19.
 */
public interface TowerFactoryInterface {
    String getIcon();
    towerdefense.document.Color getColor();
    int getPrice();
    int getRange();
    String getName();
    int getDamage();
    Image getImage();
}
