package towerdefense.document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;


/**
 * Reprezentuje mapę w grze, która składa się z pól-ścieżek,
 * po których mogą się przemieszczać potwory oraz pól-terenów,
 * na których gracz może ustawić wieże. Pola te są wizualnie
 * odróżnialne dzięki kolorom zapisanym w atrybucie <i>colorScheme</i>
 */
public class Map implements Iterable<Field>{

    /**
     * Schemat kolorów mapy.
     * colorScheme[0]   kolor pól-terenów
     * colorScheme[1]   kolor pól-ścieżek
     */
    protected Color[] colorScheme = new Color[2];
    /**
     * Dwuwymiarowa tablica będąca mapą.
     */
    ArrayList<ArrayList<Field>> map = new ArrayList<ArrayList<Field>>();
    String mapName;

    public Map(String mapName) {
        this.mapName = mapName;
    }
    public Map(){

    }

    public String getMapName() {
        return mapName;
    }



    /**
     * Odczytywanie wyglądu mapy z pliku.
     *
     * @param name  nazwa mapy
     * @return array list   mapa
     */
    public ArrayList<ArrayList<Field>> readMapLayout() {
        String mapFile = "data/" + mapName + ".txt";
        String line = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(mapFile));

            int numberOfArrays = 0;
            while ((line = bufferedReader.readLine()) != null) {
                map.add(new ArrayList<Field>());
                String[] splited = line.split(" ");

                for (int i = 0; i < splited.length; i++) {
                    Field f;
                    boolean isStart = (splited[i].charAt(1) == '1');
                    boolean isFinish = (splited[i].charAt(2) == '1');
                    if (Character.getNumericValue(splited[i].charAt(0)) == 1) {
                        f = new FieldTerrain(isStart, isFinish, colorScheme[0], null);
                    } else {
                        f = new FieldRoad(isStart, isFinish, colorScheme[1], null);
                    }
                    map.get(numberOfArrays).add(f);
                }
                numberOfArrays++;
            }
            bufferedReader.close();
            return map;
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + mapFile + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + mapFile + "'");
        }
        return map;
    }


    @Override
    public Iterator<Field> iterator() {
        return new Iterator<Field> () {
            private int row = 0;
            private int column = 0;

            public Field first(){
                return map.get(0).get(0);
            }

            @Override
            public boolean hasNext() {
                if(row == map.get(0).size()-1 && column == map.size()-1 )
                    return false;
                return true;
            }

            @Override
            public Field next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (column == (map.size() - 1)) {
                    column = 0;
                    return map.get(row++).get(map.size()-1);
                } else {
                    return map.get(row).get(column++);
                }

            }

            @Override
            public void remove(){
                 throw new UnsupportedOperationException();
            }

            @Override
            public void forEachRemaining(Consumer<? super Field> action){
                throw new UnsupportedOperationException();
            }
        };
    }


    /**
     * Zwraca mapę.
     *
     * @return mapa
     */
    public ArrayList<ArrayList<Field>> getMap() {
        return map;
    }
}
