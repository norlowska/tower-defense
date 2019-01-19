package towerdefense.document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

class GameLogic implements Runnable {
    int interval;
    Map currentMap;
    Field startField;
    HashSet<Point> enemyPosition;
    Field moveField;
    Field nextField;
    ArrayList<Field> fieldWithEnemy = new ArrayList<>();
    ArrayList<Field> fieldMoveEnemy = new ArrayList<>();
    Document document;


    public GameLogic(int interval, Map currentMap, Field startField, HashSet<Point> enemyPosition,Document document) {
        this.interval = interval;
        this.startField = startField;
        this.currentMap = currentMap;
        this.enemyPosition = enemyPosition;
        this.document = document;
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
                //document.notifyView();
                System.out.println("Added Enemy");

            }
        }
        if(interval == 2 || interval == 3){

            while (checkAllFieldIterator.hasNext()){
                nextField = (Field) checkAllFieldIterator.nextMove();
                moveField = (Field) checkAllFieldIterator.next();
                if(moveField.isFinish()){
                    ((FieldRoad) moveField).removeEnemy();
                }
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
            //document.notifyView();
            fieldMoveEnemy.clear();
            fieldWithEnemy.clear();
            if(interval == 3){
                interval = 0;
            }


        }
        interval++;


    }
}