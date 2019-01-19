package towerdefense.document;

import java.util.HashMap;
import java.util.Map;

public abstract class TowerFactory{
    protected abstract TowerFactoryInterface create();
    private static Map<String, TowerFactory> factories = new HashMap<String, TowerFactory>();
    public static void addFactory(String id, TowerFactory f){
        factories.put(id, f);
    }
    public static final TowerFactoryInterface createTower(String id){
        if(!factories.containsKey(id)){
            try {
                Class.forName("towerdefense.document.towers." + id);
                //addFactory(id,);
            }catch(ClassNotFoundException e){
                throw new RuntimeException("Bad Tower: " + id);
            }
            if(!factories.containsKey(id)) throw new RuntimeException("Bad Tower: " + id);
        }
        return ((TowerFactory)factories.get(id)).create();
    }
}