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
    //Terrain terrain = new Terrain();
    ArrayList<ArrayList<Field>> map = new ArrayList<ArrayList<Field>>();

    public ArrayList<ArrayList<Field>> readMapLayout(String name) {
        String mapName = "data/" + name + ".txt";
        String line = null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(mapName));

            int numberOfArrays = 0;
            while ((line = bufferedReader.readLine()) != null) {
                map.add(new ArrayList<Field>());
                String[] splited = line.split(" ");

                for (int i = 0; i < splited.length; i++) {
                    Terrain terrain = new Terrain();
                    if (Character.getNumericValue(splited[i].charAt(0)) == 1) {
                        terrain.setColor(1);
                    } else {
                        terrain.setColor(0);
                    }
                    Field field = new Field(terrain, Character.getNumericValue(splited[i].charAt(1)),
                            Character.getNumericValue(splited[i].charAt(2)));
                    map.get(numberOfArrays).add(field);
                }
                numberOfArrays++;
            }
            bufferedReader.close();
            return map;
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + mapName + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + mapName + "'");
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
}
