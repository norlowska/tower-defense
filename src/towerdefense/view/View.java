package towerdefense.view;

import towerdefense.document.Document;

import javax.swing.*;

public class View {
    protected Document document;
    protected JFrame window;

    public Document getDoc() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void render(){
    }

    public View() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window = new JFrame("Tower-Defense");
                window.setSize(900, 800);
                window.setResizable(false);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLocationRelativeTo(null);
            }
        });
    }
}
