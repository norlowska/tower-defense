package towerdefense.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

class MyRunnable implements Runnable {
    int interval;
    Map currentMap;
    Field startField;
    HashSet<Point> enemyPosition;


    public MyRunnable(int interval, Map currentMap,Field startField, HashSet<Point> enemyPosition) {
        this.interval = interval;
        this.startField = startField;
        this.currentMap = currentMap;
        this.enemyPosition = enemyPosition;
    }

    public void run() {
        System.out.println("Hello world");
        Iterator<Point> iterator = enemyPosition.iterator();
        if(((FieldTerrain)currentMap.getMap().get(0).get(0)).getTower()!=null)
            System.out.println("Mam wieze");
        if(interval == 1){
            Enemy enemy = ((FieldRoad) startField).getEnemy();
            if( enemy == null){
                ((FieldRoad) startField).setEnemy(new Enemy(40,1));
                System.out.println("Added Enemy");
                do{


                }while (iterator.hasNext());
            }
            interval = 0;
        }
        interval++;


    }
}