import entities.Player;
import entities.Region;
import enums.SeasonType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameManager {

    // properties
    private static final int NUMBER_OF_REGIONS = 20;
    private static boolean gameContinues = false;

    // constructor
    public GameManager() {
        // GUI'den player infosu mod infosu çek
    }

    // methods
    public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        int numPlayers;
        Player[] players;
        Player player;
        Region[] regions;
        boolean plague;
        boolean weather;
        SeasonType season;
        int turnCount;

        // DATA TO GET FROM USER
        numPlayers = 4;
        plague = false;
        weather = false;
        season = SeasonType.SPRING;
        turnCount = 1;

        players = new Player[numPlayers];
        // setting player names etc
        for ( int i = 0; i < players.length; i++) {
            players[i] = new Player( i, numPlayers, ("joe" + i));
            //players[i].printPlayer();
        }

        System.out.println();

        //
        MapManager mapManager = MapManager.getInstance();
        regions = mapManager.initMap("regions.txt");
        mapManager.distributeRegions(regions,players);
        mapManager.distributeTroops(regions,players);

        for (Player value : players) {
            //value.printPlayer();
        }

        for (Region region : regions) {
            region.printRegion();
        }

        TurnManager turnManager = new TurnManager(players[0],regions,plague,weather,season,turnCount);

        turnManager.buy();
        turnManager.draft();
        turnManager.attack();

        /*
        // for terminal test
        System.out.println("enter number of players");
        numPlayers = scan.nextInt();
        players = new entities.Player[numPlayers];

        System.out.println("enter player names");
        for ( int i = 0; i < numPlayers; i++ ) {
            ArrayList<Integer> startingRegions = new ArrayList<Integer>();

            for (int j = 0; j < NUMBER_OF_REGIONS; j+=numPlayers) {
                startingRegions.add(j);
            }
            players[i] = new entities.Player( i, numPlayers, scan.nextLine(), startingRegions );
        }

        gameContinues = true;
        TurnManager turnManager;
        turnCount = 0;

        // customized
        plague = false;
        weather = false;

        while ( gameContinues ) {

            // season changes after every 2 full turns
            switch( ( turnCount / ( 2 * numPlayers)) % 4) {
                case 0:
                    season = enums.SeasonType.SPRING;
                    break;
                case 1:
                    season = enums.SeasonType.SUMMER;
                    break;
                case 2:
                    season = enums.SeasonType.FALL;
                    break;
                default:
                    season = enums.SeasonType.WINTER;
                    break;
            }

            turnManager = new TurnManager(players[turnCount%numPlayers],regions,plague,weather,season,turnCount);

            turnManager.buy();
            turnManager.draft();

            System.out.println("wow");
            turnCount++;
        }
        */
    }

    // farklı maplere gore grafları burada ayarlayabiliriz
    /*public static entities.Region[] createRegions() {

        entities.Region[] regions = new entities.Region[NUMBER_OF_REGIONS];

        for ( int i = 0; i < NUMBER_OF_REGIONS; i++) {
            regions[i] =  new entities.Region(i,"data/region.txt");
        }

        return regions;
    }*/

}
