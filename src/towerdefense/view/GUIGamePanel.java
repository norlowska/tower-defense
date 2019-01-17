package towerdefense.view;

import javax.swing.*;
import java.awt.*;

public class GUIGamePanel extends JPanel implements Runnable{
    public Thread thread = new Thread(this);

    public GUIGamePanel() {
        thread.start();
//        setBackground(Color.pink);
    }

    public void paintComponent(Graphics g) {

    }

    public static int fpsFrame = 0, fps = 1000000;
    @Override
    public void run() {
        while (true){
            System.out.println("Hello");
            repaint();

            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
    }
}
