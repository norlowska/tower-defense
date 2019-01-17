package towerdefense.document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class Map implements Iterable<Field>{
    protected Color[] colorScheme = new Color[2];
    ArrayList<ArrayList<Field>> map = new ArrayList<ArrayList<Field>>();
    String mapName;

    public Map(String mapName) {
        this.mapName = mapName;
        readMapLayout();
    }

    public String getMapName() {
        return mapName;
    }



    public ArrayList<ArrayList<Field>> readMapLayout() {
        String mapFile = "data/" + mapName + ".txt";
        String line = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(mapFile));
            String[] splitted;
            int numberOfArrays = 0;
            if((line = bufferedReader.readLine()) != null) {
                splitted = line.split(" ");
                try{
                    colorScheme[0] = Color.valueOf(splitted[0]);
                    colorScheme[1] = Color.valueOf(splitted[1]);
                } catch(IllegalArgumentException e) {
                    System.out.println("Illegal color value. Default color scheme was set.");
                    colorScheme[0] = Color.BLACK;
                    colorScheme[1] = Color.WHITE;
                }

            }
            while ((line = bufferedReader.readLine()) != null) {
                map.add(new ArrayList<Field>());
                splitted = line.split(" ");

                for (int i = 0; i < splitted.length; i++) {
                    Field f;
                    boolean isStart = (splitted[i].charAt(1) == '1');
                    boolean isFinish = (splitted[i].charAt(2) == '1');
                    if (Character.getNumericValue(splitted[i].charAt(0)) == 1) {
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
            System.out.println("Unable to open map layout file '" + mapFile + "'");
        } catch (IOException ex) {
            System.out.println("Error reading map layout file file '" + mapFile + "'");
        }
        return map;
    }


    @Override
    public Iterator<Field> iterator() {
        return new Iterator<Field> () {
            private int row = 0;
            private int column = 0;

            @Override
            public boolean hasNext() {
                if(row == map.size())
                    return false;
                return true;
            }

            @Override
            public Field next() {
                if (column == (map.get(row).size() - 1)) {
                    column = 0;
                    row++;
                    return map.get(row-1).get(map.get(row-1).size()-1);
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
