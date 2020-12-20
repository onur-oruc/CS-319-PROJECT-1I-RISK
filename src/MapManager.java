
import entities.Continent;
import entities.Player;
import entities.Region;
import mission.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class is designed according to Singleton design pattern.
 * It will be used in MapManager class to initialize regions,
 * distribute troops to each region, and
 * distribute these regions to players.
 *
 * @author Ömer Yavuz Öztürk
 * @author Onur Oruç
 * date: 16.12.2020
 *
 */
public class MapManager {

    // add non random distribution? todo

    // create an instance of MapManager
    private static MapManager instance;

    // make the constructor private so that this class cannot be instantiated
    private MapManager() {}

    // Get the single instance of MapManager
    public static MapManager getInstance(){
        if ( instance == null)
            instance = new MapManager();
        return instance;
    }

    /**
     * This is the method that will be used in MapManager class to initialize regions,
     * distribute regions to each player, and distribute troops to each region with at least
     * one troop per region.
     *
     * @param  regionsFilepath  the path from which region information will be fetched
     * @param  players   the player objects created in MapManager
     * @return regions   regions created according to the information in filepath
     * @throws Exception throw an exception if the file could not be found
     */
    public ArrayList<Object> initializeMap(String regionsFilepath, String continentsFilepath, Player[] players, boolean secretMission) throws Exception {

        ArrayList<Object> arr = new ArrayList<Object>();
        Region[] regions;
        regions = initMap(regionsFilepath);
        Continent[] continents;
        continents = initContinents(continentsFilepath);

        giveMissions(secretMission,players,regions,continents);

        distributeRegions(regions,players);
        distributeTroops(regions,players);

        arr.add(regions);
        arr.add(continents);

        return arr;
    }

    /**
     * Fetches information related to the regions in the map in the
     * text file located in the given file path
     * Utilizes BufferedReader and File classes from java.io
     *
     * @param filePath the location in which the text file is.
     * @return regions regions created according to the information in filepath
     * @throws Exception throw an exception if the file could not be found
     */
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
        } catch(FileNotFoundException e) {
            return null;
        }

        return regions;
    }
    /**
     * Distributes troops to each region
     * where each region has at least one troop
     *
     * @param regions the regions initialized with initMap()
     * @param players the player objects created in MapManager
     */
    public void distributeTroops( Region[] regions, Player[] players ) {
        int remainingNumTroops, numEmptyRegions, numTroopsToPlace;

        for (Player player : players) {
            ArrayList<Integer> list = player.getRegionIds();

            /*If there are two players, each player will have 40 troops
              If there are three players, each player will have 35 troops
              If there are four players, each player will have 30 troops*/
            remainingNumTroops = 50 - (players.length * 5);

            numEmptyRegions = list.size();

            for (Integer integer : list) {
                // calculate the number of troops in a way that each
                // region will have the same number of troops (if possible)
                numTroopsToPlace = remainingNumTroops / numEmptyRegions;
                regions[integer].setNumTroops(numTroopsToPlace);
                remainingNumTroops -= numTroopsToPlace;
                numEmptyRegions--;
            }
        }
    }

    /**
     * Distributes regions to each player
     *
     * @param regions the regions initialized with initMap()
     * @param players the player objects created in MapManager
     */
    public void distributeRegions( Region[] regions, Player[] players ) {

        ArrayList<Integer> unowned = new ArrayList<>();

        for ( int i = 0; i < regions.length; i++ ) {
            unowned.add(i);
        }

        int playerID, randomRegionID, randomIndex;

        for ( int i = 0; i < regions.length; i++ ) {
            playerID = (i % players.length);
            randomIndex = (int) (unowned.size() * Math.random());

            // remove the region from unowned array after distributing it to a player
            randomRegionID = unowned.remove(randomIndex);
            // System.out.println(randomRegionID); // test
            players[playerID].addRegion(randomRegionID);
            regions[randomRegionID].setOwnerID(playerID);
        }
    }



    /**
     * Fetches information related to the continents in the map in the
     * text file located in the given file path
     * Utilizes BufferedReader and File classes from java.io
     *
     * @param filePath the location in which the text file is.
     * @return continents created according to the information in filepath
     * @throws Exception throw an exception if the file could not be found
     */
    public Continent[] initContinents(String filePath) throws Exception {
        Continent[] continents;

        try {
            File file = new File(filePath);

            // first count the number of continents
            BufferedReader forNumLines = new BufferedReader(new FileReader(file));
            int numLines = -1;

            while( forNumLines.readLine() != null) {
                numLines++;
            }
            forNumLines.close();

            // initialize regions according to the number of continents calculated above.
            continents = new Continent[numLines];

            file = new File(filePath);
            Scanner inputStream = new Scanner(file);
            int counter = 0;
            while(inputStream.hasNext()) {
                String data = inputStream.nextLine();

                // skip the first line since it contains only titles
                if (counter != 0) {
                    continents[counter-1] = new Continent();
                    String[] line = data.split(",");


                    // continent ID of the continent
                    continents[counter - 1].setContinentId(Integer.parseInt(line[0]));

                    // create regions array, between the first element and last three elements in a row
                    int[] regionsIDs = new int[line.length-4];
                    for(int i = 1; i < line.length-3; i++) {
                        regionsIDs[i-1] = Integer.parseInt(line[i]);
                    }

                    continents[counter-1].setRegionIds(regionsIDs);

                    // set continent name, it is the third-last element in a row

                    continents[counter-1].setContinentName(line[line.length-3]);

                    // set number of regions that a continent has, it is the second-last element in a row.

                    continents[counter-1].setRegionCount(Integer.parseInt(line[line.length-2]));

                    // set extra bonus troops that will be given to a player
                    // when a player has the continent
                    // it is the last element in a row.

                    continents[counter-1].setBonusTroops(Integer.parseInt(line[line.length-1]));
                }
                counter++;
            }
        } catch(FileNotFoundException e) {
            return null;
        }

        return continents;
    }

    /**
     * This method is used to roll dices and save the rolling results.
     * @param secretMission This is the first parameter to giveMissions method that stores whether player has secretMission or not, for now.
     * @param players  This is the second parameter to giveMissions method stores info of players.
     * @param regions  This is the third parameter to giveMissions method stores info of regions.
     * @param continents  This is the fourth parameter to giveMissions method stores of info of continents.
     */
    private void giveMissions( boolean secretMission, Player[] players, Region[] regions, Continent[] continents) {

        if ( secretMission ) {

            for ( Player player : players ) {

                if ( Math.random() < 0.5 ) {
                    player.setMission( new ConquerContinentsMission(continents) );
                }
                else if ( Math.random() < 0.7 ) {
                    player.setMission( new EliminateColorMission(player,players,players.length)); //
                }
                else {
                    player.setMission( new ConquerNumRegionMission(regions));
                }
            }
        }
        else {
            for ( Player player : players ) {
                player.setMission( new ConquerWorldMission( regions.length ));
            }
        }

    }
}