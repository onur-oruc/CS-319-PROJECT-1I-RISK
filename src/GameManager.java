import entities.Continent;
import entities.Player;
import entities.Region;
import enums.SeasonType;
import mission.Mission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameManager {

    // properties
    private final int NUMBER_OF_REGIONS = 42;
    private boolean gameContinues = false;
    private int SEASON_PERIOD = 2;

    private int numPlayers;
    private String[] playerNames;
    private Player[] players;
    private Region[] regions;
    private Continent[] continents;
    private boolean plague;
    private boolean weather;
    private boolean secretMission;
    private SeasonType season;
    private int turnCount;
    private int winnerID;
    private int whoseTurn;

    // constructors

    // new game
    public GameManager( int numPlayers, String[] playerNames, boolean plague, boolean weather, boolean secretMission) {

        this.numPlayers = numPlayers;
        this.playerNames = playerNames;
        this.plague = plague;
        this.weather = weather;
        this.secretMission = secretMission;
        this.season = SeasonType.SPRING;
        this.turnCount = 1;

        // invalid winner id
        this.winnerID = -1;
        this.whoseTurn = -1;

    }

    // from saved game
    public GameManager( String filePath ) {

    }

    // methods
    /**
     * This method is used to initialize a game.
     */

    public void startGame() throws Exception {

        players = new Player[numPlayers];

        for ( int i = 0; i < players.length; i++) {
            players[i] = new Player( i, numPlayers, playerNames[i], null);
        }

        MapManager mapManager = MapManager.getInstance();
        ArrayList<Object> regionsNcontinents = mapManager.initializeMap("regions.txt","continents.txt",players,secretMission);

        regions = (Region[]) regionsNcontinents.get(0);
        continents = (Continent[]) regionsNcontinents.get(1);

    }

    public boolean isLast( Player player ){
        int idOfLastPlayer;
        for( int i = 0; i < numPlayers; i++)
            if( !players[i].isEliminated())
                idOfLastPlayer = i;
        return idOfLastPlayer == player.getId() ;
    }
    public void turns() {

        while ( winnerID < 0 ) {

            // reset weather effects
            for ( Region region: regions) {
                region.setDrought(false);
                region.setFrost(false);
            }

            for (Player player : players) {
                if ( !player.isEliminated() ) {
                    whoseTurn = player.getId();

                    TurnManager turnManager = new TurnManager(player, players, regions, continents, plague, weather, season, turnCount);
                    player.printPlayer();
                    turnManager.playTurn();

                    if (player.getMission().isCompleted(player)) {
                        winnerID = player.getId();
                        break;
                    }
                }
            }

            if ( turnCount % SEASON_PERIOD == 0 )
                nextSeason();

            turnCount++;
        }
    }
    /**
     * This method is used to update the season periodically.
     */
    public void nextSeason() {

        if ( season == SeasonType.SPRING )
            season = SeasonType.SUMMER;
        else if ( season == SeasonType.SUMMER )
            season = SeasonType.FALL;
        else if ( season == SeasonType.FALL )
            season = SeasonType.WINTER;
        else // if ( season == SeasonType.WINTER )
            season = SeasonType.SPRING;
    }
    /**
     * This method is used to write the money of each player to array.
     * @return int[] which denotes the money of players
     */
    public int[] getMoneyOfAll() {
        int[] moneys = new int[numPlayers];

        for ( int i = 0; i < numPlayers; i++ ) {
            moneys[i] = players[i].getMoney();
        }
        return moneys;
    }
    /**
     * This method is used to write the information for having goldmine for each region into an array.
     * @return boolean[] which stores the attribute hasGoldMine for each region.
     */
    public boolean[] getGoldMineOfAll() {
        boolean[] goldMines = new boolean[regions.length];

        for ( int i = 0; i < regions.length; i++ ) {
            goldMines[i] = regions[i].hasGoldMine();
        }
        return goldMines;
    }

    /*public static void main(String[] args) throws Exception {

        Scanner scan = new Scanner(System.in);
        int numPlayers;
        Player[] players;
        Player player;
        Region[] regions;
        Continent[] continents;
        boolean plague;
        boolean weather;
        SeasonType season;
        int turnCount;

        // DATA TO GET FROM USER
        numPlayers = 4;
        plague = true;
        weather = true;
        season = SeasonType.SPRING;
        turnCount = 1;

        players = new Player[numPlayers];
        // setting player names etc
        for ( int i = 0; i < players.length; i++) {

            players[i] = new Player( i, numPlayers, ("joe" + i), null);
            //players[i].printPlayer();
        }


        System.out.println();

        //
        MapManager mapManager = MapManager.getInstance();
        regions = mapManager.initMap("regions.txt");
        System.out.println(regions[0].getContinentID());

        mapManager.distributeRegions(regions,players);
        mapManager.distributeTroops(regions,players);
        continents = mapManager.initContinents("continents.txt");

        for (Player value : players) {
            //value.printPlayer();
        }

        for (Region region : regions) {
            region.printRegion();
        }

        System.out.println();


        TurnManager turnManager = new TurnManager(players[0],regions,continents,plague,weather,season,turnCount);

        players[0].printPlayer();
        turnManager.operate();
/*
        for ( Integer i : players[0].getRegionIds()) {
            System.out.print(i + ",");
        }*/

        /*
        while (false) {
            for ( tum oyuncular) {
                give turn
            }
            turncount++;
        }
        */

        /*while (true) {


            System.out.println("\nchoose one");

            for (Integer i : regions[scan.nextInt()].getConnectedOwnedRegions(regions, players[0].getRegionIds())) {
                System.out.print(i + ",");
            }
        }*/
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
