package towerdefense.document;

import java.util.ArrayList;
import java.util.HashMap;

public class EnemyFlyweightFactory {
    private HashMap<String, IFlyweight> enemiesList;

    public IFlyweight getFlyweight(String name) {
        if(enemiesList.get(name) == null) {
         //   Enemy enemy = new Enemy(name);
        }
        return enemiesList.get(name);
    }
}
