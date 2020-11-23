import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Region {

    // properties
    private int regionID;
    private int ownerID;
    private int numTroops;
    private boolean hasCommander;
    private ClimateType climate;
    private int[] neighbors;
    private int locX;
    private int locY;
    private boolean hasGoldMine;
    private boolean troopMotivation;
    ArrayList<Integer> neighborsByID; // her region icin sabit bu



    // constructors
    public Region(int regionId){
        this.regionID = regionId;
        this.ownerID = -1;
        BufferedReader rdr;

        try {
            rdr = new BufferedReader(new FileReader("data/region.txt"));
            for(int i = 0; i < regionId; i++){
                rdr.readLine();
            }
            Scanner scan = new Scanner(rdr.readLine());
            locX = scan.nextInt();
            locY = scan.nextInt();

            int neighborCount = scan.nextInt();

            neighbors = new int[neighborCount];
            for(int i = 0; i < neighborCount; i++){
                neighbors[i] = scan.nextInt();
            }

            scan.close();
            rdr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // methods



    public void setNumTroops(int numTroops){
        this.numTroops = numTroops;
    }

    public int getNumTroops(){
        return this.numTroops;
    }

    public void increaseNumTroopsBy(int numNewTroops){
        numTroops = numTroops + numNewTroops;
    }

    public void setOwnerId(int ownerId){
        this.ownerID = ownerId;
    }

    public int getOwnerId(){
        return this.ownerID;
    }

    public void setHasCommander(boolean hasCommander){
        this.hasCommander = hasCommander;
    }

    public boolean hasCommander(){
        return this.hasCommander;
    }

    public void setClimate(ClimateType climate){
        this.climate = climate;
    }

    public ClimateType getClimate(){
        return this.climate;
    }

    public void setTroopMotivation(boolean troopMotivation){
        this.troopMotivation = troopMotivation;
    }

    public boolean getTroopMotivation(){
        return this.troopMotivation;
    }

    public void setHasGoldMine(boolean hasGoldMine){
        this.hasGoldMine = hasGoldMine;
    }

    public boolean hasGoldMine(){
        return this.hasGoldMine;
    }

    public int[] getNeighbors(){ return neighbors; }

    public int getLocX(){
        return this.locX;
    }

    public int getLocY(){
        return this.locY;
    }

}
