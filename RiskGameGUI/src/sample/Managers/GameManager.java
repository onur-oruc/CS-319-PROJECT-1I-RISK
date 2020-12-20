package sample.Managers;
import sample.Entities.Continent;
import sample.Entities.Player;
import sample.Entities.Region;
import sample.Enums.SeasonType;
import sample.Mission.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameManager implements Serializable {

    // properties
    private final int NUMBER_OF_REGIONS = 42;
    private boolean gameContinues = false;
    private int SEASON_PERIOD = 2;

    private int numPlayers;
    private String instruction;
    private Player[] players;
    private Region[] regions;
    private Continent[] continents;
    private boolean plague;
    private boolean weather;




    private boolean secretMission;
    private SeasonType season;

    private int turnCount;
    private int winnerID;
    private String stageString;
    private int whoseTurn;

    private TurnManager tm;

    // constructors

    // new game
    public GameManager( int numPlayers, Player[] players, boolean plague, boolean weather, boolean secretMission) {
        this.numPlayers = numPlayers;
        this.players = players;
        this.plague = plague;
        this.weather = weather;
        this.secretMission = secretMission;
        this.season = SeasonType.SPRING;
        this.turnCount = 1;
        this.instruction = "Click on distribute regions to start game";
        this.stageString = "BUY STAGE";
        // invalid winner id
        this.winnerID = -1;
        this.whoseTurn = 0;
        tm = null;
    }

    // from saved game
    public GameManager( String filePath ) {

    }

    // methods
    public boolean isSecretMission() {
        return secretMission;
    }

    public boolean isWeather() {
        return weather;
    }

    public boolean isPlague() {
        return plague;
    }

    public void startGame() throws Exception {

        MapManager mapManager = MapManager.getInstance();
        ArrayList<Object> regionsNcontinents = mapManager.initializeMap("../regions.txt","../continents.txt",players,secretMission);
        regions = (Region[]) regionsNcontinents.get(0);
        continents = (Continent[]) regionsNcontinents.get(1);
        tm = new TurnManager(players[0], players, regions, continents, plague,weather, season, turnCount );
    }

    public TurnManager getTm() {
        return tm;
    }

    /*public void turns() {

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
    }*/

    public String getStageString() {
        return stageString;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public void setStageString(String stageString) {
        this.stageString = stageString;
    }

    public int getWhoseTurn() {
        return whoseTurn;
    }

    public void setWhoseTurn(int whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Player[] getPlayers() {
        return players;
    }
    public Region[] getRegions() {
        return regions;
    }
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

    public int[] getMoneyOfAll() {
        int[] moneys = new int[numPlayers];

        for ( int i = 0; i < numPlayers; i++ ) {
            moneys[i] = players[i].getMoney();
        }
        return moneys;
    }

    public boolean[] getGoldMineOfAll() {
        boolean[] goldMines = new boolean[regions.length];

        for ( int i = 0; i < regions.length; i++ ) {
            goldMines[i] = regions[i].hasGoldMine();
        }
        return goldMines;
    }

    public void operationsAfterLastPlayer( Region[] regions, int turnCount) {
        for ( Region region : regions) {
            region.setDrought(false);
            region.setFrost(false);
        }

        if ( turnCount % SEASON_PERIOD == 0) {
            nextSeason();
        }
    }

    public void nextTurn() {
        for ( int i = whoseTurn +1; i < players.length; i++) {
            if (!players[i].isEliminated()) {
                whoseTurn =i;
                tm = new TurnManager(players[whoseTurn],players,regions,continents,plague,weather,season,turnCount);
                return;
            }
        }
        for ( int i = 0; i < whoseTurn; i++) {
            if(!players[i].isEliminated()) {
                whoseTurn = i;
                tm = new TurnManager(players[whoseTurn],players,regions,continents,plague,weather,season,turnCount);
                return;
            }
        }
    }

    public boolean isLast( Player player ){
        int idOfLastPlayer = 0;
        for( int i = 0; i < numPlayers; i++)
            if( !players[i].isEliminated())
                idOfLastPlayer = i;
        return idOfLastPlayer == player.getId() ;
    }
}
