package towerdefense.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

class MyRunnable implements Runnable {
    int interval;
    Map currentMap;
    Field startField;
    HashSet<Point> enemyPosition;
    Field moveField;
    Field nextField;
    ArrayList<Field> fieldWithEnemy = new ArrayList<>();
    ArrayList<Field> fieldMoveEnemy = new ArrayList<>();


    public MyRunnable(int interval, Map currentMap,Field startField, HashSet<Point> enemyPosition) {
        this.interval = interval;
        this.startField = startField;
        this.currentMap = currentMap;
        this.enemyPosition = enemyPosition;
    }

    public void run() {
        System.out.println("Hello world");
        Iterator<Point> iterator = enemyPosition.iterator();
        CheckAllFieldIterator checkAllFieldIterator = currentMap.checkAllFieldIterator();
        if(((FieldTerrain)currentMap.getMap().get(0).get(0)).getTower()!=null)
            System.out.println("Mam wieze");
        if(interval == 1){
            Enemy enemy = ((FieldRoad) startField).getEnemy();
            if( enemy == null){
                ((FieldRoad) startField).setEnemy(new Enemy(40,1));
                System.out.println("Added Enemy");

            }
        }
        if(interval == 2){

            while (checkAllFieldIterator.hasNext()){
                nextField = (Field) checkAllFieldIterator.nextMove();
                moveField = (Field) checkAllFieldIterator.next();
                if(moveField instanceof FieldRoad){
                    if(((FieldRoad) moveField).hasEnemy()){
                        fieldWithEnemy.add(moveField);
                        fieldMoveEnemy.add(nextField);
                    }
                }

            }
            for(int i = 0;i < fieldMoveEnemy.size(); i++){
                ((FieldRoad)fieldMoveEnemy.get(i)).setEnemy(((FieldRoad)fieldWithEnemy.get(i)).getEnemy());
                ((FieldRoad)fieldWithEnemy.get(i)).removeEnemy();
            }
            interval = 0;

        }
        interval++;


    }
}