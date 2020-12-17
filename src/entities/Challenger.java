package entities;

import enums.TroopCardType;
import mission.Mission;

import java.util.ArrayList;

public abstract class Challenger {
    int money;
    TroopCardType[] troopCards;
    boolean isTurn;
    String name;
    int id;
    int regionCount;
    boolean isEliminated;
    ArrayList<Integer>regionIds;
    Mission mission;



    Challenger(String name, int id){
        this.name = name;
        this.id = id;
        this.regionIds = new ArrayList<>();
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

        if ( !regionIds.contains(newRegionID)) {
            regionIds.add(newRegionID);
            regionCount++;
            return true;
        }
        return false;
    }

    public void combineCards(){

    }

    public int calculateBonusTroop( Continent[] continents ){

        return 0;
    }



    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void setEliminated(boolean eliminated) {
        isEliminated = eliminated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public TroopCardType[] getTroopCards() {
        return troopCards;
    }

    public void setTroopCards(TroopCardType[] troopCards) {
        this.troopCards = troopCards;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public ArrayList<Integer> getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(ArrayList<Integer> regionIds) {
        this.regionIds = regionIds;
    }
}