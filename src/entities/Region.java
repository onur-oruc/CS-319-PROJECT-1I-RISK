package entities;

import enums.MotivationLevel;

import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;
        import java.lang.reflect.Array;
        import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Region {

    // properties
    private int continentID;
    private String regionName;
    private int regionID;
    private int ownerID;
    private int numTroops;
    private boolean hasCommander;
    private int[] neighbors;
    private int locX;
    private int locY;
    private boolean hasGoldMine;
    private MotivationLevel motivation;

    private boolean plague, drought, frost;

    // constructors
    public Region() {

    }

    // copy constructor
    public Region(Region toCopy) {
        this.continentID = toCopy.continentID;
        this.regionName = toCopy.regionName;
        this.regionID = toCopy.regionID;
        this.ownerID = toCopy.ownerID;
        this.numTroops = toCopy.numTroops;
        this.hasCommander = toCopy.hasCommander;
        this.hasGoldMine = toCopy.hasGoldMine;
        this.motivation = toCopy.motivation;

        this.neighbors = new int[toCopy.neighbors.length];
        for (int i = 0; i < neighbors.length; i++) {
            neighbors[i] = toCopy.neighbors[i];
        }
    }

    // methods

    public void setRegionID(int regionID) {this.regionID = regionID;}

    public int getRegionID() {return regionID;}

    public void setNumTroops(int numTroops){
        this.numTroops = numTroops;
    }

    public int getNumTroops(){
        return this.numTroops;
    }

    public void increaseNumTroopsBy(int numNewTroops){
        numTroops = numTroops + numNewTroops;
    }

    public void setOwnerID(int ownerID){
        this.ownerID = ownerID;
    }

    public int getOwnerID(){
        return this.ownerID;
    }

    public void setHasCommander(boolean hasCommander){
        this.hasCommander = hasCommander;
    }

    public boolean hasCommander(){
        return this.hasCommander;
    }

    public void setMotivation(MotivationLevel motivation){
        this.motivation = motivation;
    }

    public MotivationLevel getMotivation(){
        return this.motivation;
    }

    public void setHasGoldMine(boolean hasGoldMine){
        this.hasGoldMine = hasGoldMine;
    }

    public boolean hasGoldMine(){
        return this.hasGoldMine;
    }

    public void setNeighbors(int[] neighbors) {this.neighbors = neighbors;}

    public int[] getNeighbors(){ return neighbors; }

    public int getLocX(){
        return this.locX;
    }

    public int getLocY(){
        return this.locY;
    }

    public int getContinentID() {
        return continentID;
    }

    public void setContinentID(int continentID) {
        this.continentID = continentID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public boolean hasDrought() { return drought; }
    public void setDrought( boolean drought ) { this.drought = drought; }

    public boolean hasPlague() { return plague; }
    public void setPlague( boolean plague ) { this.plague = plague; }

    public boolean hasFrost() { return frost; }
    public void setFrost( boolean frost ) { this.frost = frost; }


    public ArrayList<Integer> getConnectedOwnedRegions( Region[] allRegions,  List<Integer> ownedRegionsByID ) {

        ArrayList<Integer> connectedOwnedRegions = new ArrayList <Integer> ();
        connectedOwnedRegions.add( this.regionID );
        boolean [] visitedRegions, isOwnedRegion;
        isOwnedRegion = new boolean [allRegions.length];
        visitedRegions = new boolean[allRegions.length];

        for( int i = 0; i < 42; i++ ){
            isOwnedRegion[i] = visitedRegions[i] = false;
        }

        for( int i = 0; i < ownedRegionsByID.size(); i++){
            isOwnedRegion[ ownedRegionsByID.get(i) ] = true;
        }

        int i = 0;

        while( i < connectedOwnedRegions.size() ){
            int currentRegion = connectedOwnedRegions.get( i );
            visitedRegions[ currentRegion] = true;
            int [] neighboursOfCurrentRegion = allRegions[ currentRegion ]. getNeighbors();
            for( int j = 0; j < neighboursOfCurrentRegion.length; j++){
                int regionIdToCheckConnectivity = neighboursOfCurrentRegion[j];
                if( !visitedRegions[regionIdToCheckConnectivity] && isOwnedRegion[regionIdToCheckConnectivity] ){
                    connectedOwnedRegions.add( regionIdToCheckConnectivity );
                    visitedRegions[regionIdToCheckConnectivity] = true;
                }

            }
            i++;
        }
        return connectedOwnedRegions;
    }


    public void printRegion() {

        System.out.println();
        System.out.println("regionID: " + regionID);
        System.out.println("regionName: " + regionName);
        System.out.println("continentID: " + continentID);
        System.out.println("ownerID: " + ownerID);
        System.out.println("numTroops: " + numTroops);
        System.out.println("hasCommander: " + hasCommander);
        System.out.println("hasGoldMine: " + hasGoldMine);
        System.out.println("troopMotivation: " + motivation);
        System.out.println("locX: " + locX);
        System.out.println("locY: " + locY);

        System.out.print("neighbors: ");
        for (int neighbor : neighbors) {
            System.out.print(neighbor + ", ");
        }
        System.out.println();
    }
}