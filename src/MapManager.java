import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class MapManager {

    // props
    /*Region[] regions;
    Player[] players;
    Continent[] continents;
    int totalNumTroops;*/

    //create an object of SingleObject
    private static MapManager instance = new MapManager();

    //make the constructor private so that this class cannot be
    //instantiated
    private MapManager() {}

    //Get the only object available
    public static MapManager getInstance(){
        return instance;
    }

    public void showMessage() {
        System.out.println("selamun aleykum");
    }

    public Region[] initMap(String filePath) throws Exception {
        Region[] regions;

        try {
            File file = new File(filePath);

            // first count the number of regions
            BufferedReader forNumLines = new BufferedReader(new FileReader(file));
            int numLines = -1;

            while( forNumLines.readLine() != null) {
                numLines++;
            }
            forNumLines.close();

            // initialize regions according to the number of regions calculated above.
            regions = new Region[numLines];

            file = new File(filePath);
            Scanner inputStream = new Scanner(file);
            int counter = 0;
            while(inputStream.hasNext()) {
                String data = inputStream.nextLine();

                // skip the first line since it contains only titles
                if (counter != 0) {
                    regions[counter-1] = new Region();
                    String[] line = data.split(",");

                    // continent ID to which the region belongs is the first number in a row
                    regions[counter - 1].setContinentID(Integer.parseInt(line[0]));

                    // create neighbor array, between the first element and last two elements in a row
                    int[] neighbors = new int[line.length-3];
                    for(int i = 1; i < line.length-2; i++) {
                        neighbors[i-1] = Integer.parseInt(line[i]);
                    }
                    regions[counter-1].setNeighbors(neighbors);

                    // set regionName, it is just before the last element in a row.
                    regions[counter-1].setRegionName(line[line.length-2]);

                    // set regionID, it is the last element in a row.
                    regions[counter-1].setRegionID(Integer.parseInt(line[line.length-1]));
                }

                counter++;
            }
        }
        catch(FileNotFoundException e) {
            return null;
        }

        return regions;
    }


    public void distributeRegions( Region[] regions, Player[] players ) {
        // random
        ArrayList<Integer> unowned = new ArrayList<>();

        for ( int i = 0; i < regions.length; i++ ) {
            unowned.add(i);
        }

        int playerID, randomRegionID, randomIndex;

        for ( int i = 0; i < regions.length; i++ ) {
            playerID = (i % players.length);
            randomIndex = (int) (unowned.size() * Math.random());
            randomRegionID = unowned.remove(randomIndex);
            players[playerID].addRegion(randomRegionID);
        }

    }

    public void distributeTroops( Region[] regions, Player[] players ) {

        //players;

        //regions[index].setNumTroops() = num;

    }

}