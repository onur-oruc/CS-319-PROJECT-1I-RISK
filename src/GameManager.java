import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        players = new Player[numPlayers];
        // setting player names etc
        for ( int i = 0; i < players.length; i++) {
            players[i] = new Player( i, numPlayers, ("joe" + i));

            players[i].printPlayer();
        }

        System.out.println();

        //
        MapManager mapManager = MapManager.getInstance();
        regions = mapManager.initMap("regions.txt");
        mapManager.distributeRegions(regions,players);
        mapManager.distributeTroops(regions,players);

        for (Player value : players) {
            value.printPlayer();
        }

        for (Region region : regions) {
            region.printRegion();
        }

        System.out.println("\ncompareDice testing");    // got correct results
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(4,1,6,4,4));
        ArrayList<Integer> b = new ArrayList<>(Arrays.asList(2,5,3,4,4));
        int[] loss = Dice.compareDice(a,b);
        System.out.println(loss[0]);
        System.out.println(loss[1]);

        /*
        // for terminal test
        System.out.println("enter number of players");
        numPlayers = scan.nextInt();
        players = new Player[numPlayers];

        System.out.println("enter player names");
        for ( int i = 0; i < numPlayers; i++ ) {
            ArrayList<Integer> startingRegions = new ArrayList<Integer>();

            for (int j = 0; j < NUMBER_OF_REGIONS; j+=numPlayers) {
                startingRegions.add(j);
            }
            players[i] = new Player( i, numPlayers, scan.nextLine(), startingRegions );
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
                    season = SeasonType.SPRING;
                    break;
                case 1:
                    season = SeasonType.SUMMER;
                    break;
                case 2:
                    season = SeasonType.FALL;
                    break;
                default:
                    season = SeasonType.WINTER;
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
    /*public static Region[] createRegions() {

        Region[] regions = new Region[NUMBER_OF_REGIONS];

        for ( int i = 0; i < NUMBER_OF_REGIONS; i++) {
            regions[i] =  new Region(i,"data/region.txt");
        }

        return regions;
    }*/

}
