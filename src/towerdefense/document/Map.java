package towerdefense.document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Map{
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
                    if(colorScheme[0] == Color.YELLOW || colorScheme[0] == Color.MAGENTA || colorScheme[0] == Color.RED) {
                        colorScheme[0] = Color.BLACK;
                    } else if (colorScheme[1] == Color.YELLOW || colorScheme[1] == Color.MAGENTA || colorScheme[1] == Color.RED) {
                        colorScheme[1] = Color.WHITE;
                    }
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
                    int whereMove = Character.getNumericValue(splitted[i].charAt(3));
                    if (Character.getNumericValue(splitted[i].charAt(0)) == 1) {
                        f = new FieldTerrain(isStart, isFinish, colorScheme[0], null);
                    } else {
                        f = new FieldRoad(isStart, isFinish, colorScheme[1], null, whereMove);
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

    public class checkAllFieldIterator implements CheckAllFieldIterator {
            private int row = 0;
            private int column = 0;

            public boolean hasNext() {
                if(row == map.size())
                    return false;
                return true;
            }

            public Field next() {
                if (column == (map.get(row).size() - 1)) {
                    column = 0;
                    row++;
                    return map.get(row-1).get(map.get(row-1).size()-1);
                } else {
                    return map.get(row).get(column++);
                }

            }

            public Point fieldPoint(){
                return new Point(column,row);
            }
    }

    public class moveIterator implements MoveIterator<Field>{
        int row = 0, column = 0;

        public Field right(){

            if(column == map.get(row).size() - 1){
                column = 0;
            }else{
                column++;
            }
            while(map.get(row).get(column).isRoad()){
                column++;
                if(column == map.get(row).size() - 1 && map.get(row).get(column).isRoad()){
                    column = 0;
                }
            }
            return map.get(row).get(column);

        }

        public Field left(){

            if(column == 0){
                column = map.get(row).size() - 1;
            }else{
                column--;
            }
            while(map.get(row).get(column).isRoad()){
                column--;
                if(column == 0 && map.get(row).get(column).isRoad()){
                    column = map.get(row).size() - 1;
                }
            }
            return map.get(row).get(column);

        }

        public Field down(){

            if(row == map.size() - 1){
                row = 0;
            }else{
                row++;
            }
            while(map.get(row).get(column).isRoad()){
                row++;
                if(row == map.size() - 1 && map.get(row).get(column).isRoad()){
                    row = 0;
                }
            }
            return map.get(row).get(column);
        }

        public Field up(){

            if(row == 0){
                row = map.size() - 1;
            }else{
                row--;
            }
            while(map.get(row).get(column).isRoad()){
                row--;
                if(row == 0 && map.get(row).get(column).isRoad()){
                    row = map.size() - 1;
                }
            }
            return map.get(row).get(column);
        }

        public Point fieldPoint(){
            return new Point(column,row);
        }

        public boolean hasRight(){
            return true;
        }
        public boolean hasLeft(){
            return true;
        }
        public boolean hasUp(){
            return true;
        }
        public boolean hasDown(){
            return true;
        }
    }

    public MoveIterator<Field> iteratorMove(){
        return new moveIterator();
    }
    public CheckAllFieldIterator<Field> checkAllFieldIterator() { return new checkAllFieldIterator();}


    /**
     * Zwraca mapę.
     *
     * @return mapa
     */
    public ArrayList<ArrayList<Field>> getMap() {
        return map;
    }
}
