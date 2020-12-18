package entities;

import enums.TroopCardType;
import mission.Mission;

import java.lang.reflect.Array;
import java.util.*;

public abstract class Challenger {
    // make properties private
    private int money;
    private TroopCardType[] troopCards;
    private boolean isTurn;
    private String name;
    private int id;
    private int regionCount;
    private boolean isEliminated;

    private ArrayList<Integer> eliminatedEnemyIds; // will be used for eliminate color mission

    private ArrayList<Integer> regionIds;
    private Mission mission;

    Challenger(String name, int id) {
        this.name = name;
        this.id = id;
        this.regionIds = new ArrayList<>();
        this.eliminatedEnemyIds = new ArrayList<>();
    }

    public void updateRegionCount( int regionNum){
        regionCount = regionNum;
    }
    public void updateMoney( int money ){
        this.money = money;
    }
    public void updateRegions( int[] regionIds ) {
//        this.regionIds = new int [regionIds.length];
//        for( int i = 0; i < regionIds.length; i++){
//            this.regionIds[i] = regionIds[i];
//        }
    }

    public boolean addRegion(int newRegionID) {
        if (!regionIds.contains(newRegionID)) {
            regionIds.add(newRegionID);
            regionCount++;
            return true;
        }
        return false;
    }

    /**
     * This methods checks for the existence of the given continentID in given challenger
     * All continents are also given as a parameter to receive the regions of the continent
     * with continentID. Then, regions of the continent are compared to the regions of
     * challenger
     *
     * @param challenger whose continent will be checked.
     * @param continentID searched in challenger
     * @param continents the array containing all continents with their regions in the game
     * @return true if challenger owns the continent with continentID.
     */
    public boolean hasContinent(Challenger challenger, int continentID, Continent[] continents) {
        int[] regionsInContinent = null;

        // search for the continent with the given continentID (parameter)
        for (int i = 0; i < continents.length; i++) {
            // find the continent with the given continentID (parameter)
            if ( continents[i].getContinentId() == continentID) {
//                int numRegions = continents[i].getRegionCount();
//                regionsInContinent = new int[numRegions];

                // get continent's region array
                regionsInContinent = continents[i].getRegionIds();
            }
        }

        int challengerNumReg = 0;
        // check if the challenger has all regions of the continent with continentID
        for (int i = 0; i < regionsInContinent.length; i++ ) {
            if (challenger.getRegionIds().contains(regionsInContinent[i])) {
                challengerNumReg++;
            }
        }

        // if challenger has all the regions in the continent, return true
        if (challengerNumReg == regionsInContinent.length)
            return true;

        return false;
    }

    /**
     * This methods checks whether the enemy with enemyID
     * was eliminated by the challenger
     *
     * @param enemyID
     * @return
     */
    public boolean hasEliminatedEnemy(int enemyID) {
        return eliminatedEnemyIds.contains(enemyID);
    }

    /**
     * This methods add the eliminated enemy with eliminatedEnemyID
     * to the eliminated enemy list of the challenger
     *
     * @param eliminatedEnemyID eliminated enemy by the challenger
     */
    public void updateEliminatedEnemies(int eliminatedEnemyID) {
        eliminatedEnemyIds.add(eliminatedEnemyID);
    }

    public void combineCards() {

    }

    public int calculateBonusTroop (Continent[] continents) {

        return 0;
    }


    public Mission getMission() {
        return mission;
    }

    public void setMission (Mission mission) {
        this.mission = mission;
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void setEliminated (boolean eliminated) {
        isEliminated = eliminated;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn (boolean turn) {
        isTurn = turn;
    }

    public TroopCardType[] getTroopCards() {
        return troopCards;
    }

    public void setTroopCards (TroopCardType[] troopCards) {
        this.troopCards = troopCards;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney (int money) {
        this.money = money;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount (int regionCount) {
        this.regionCount = regionCount;
    }

    public ArrayList<Integer> getRegionIds() {
        return regionIds;
    }

    public void setRegionIds (ArrayList<Integer> regionIds) {
        this.regionIds = regionIds;
    }
}
