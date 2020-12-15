package entities;


/**
 * This class will be used to store name, id, # of regions that the continent has,
 * region ids, and bonus troops that will be given to the continent owner
 * Continent objects will be initialized according to the information in txt given
 * as parameter to this class' constructor
 *
 * @author Onur Oruç
 * date: 16.12.2020
 *
 */
public class Continent {
    // properties
    private String continentName;
    private int continentId;
    private int regionCount;
    private int[] regionIds;
    private int bonusTroops;

    // Constructor
    public Continent() {

    }



    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public int[] getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(int[] regionIds) {
        this.regionIds = regionIds;
    }

    public int getBonusTroops() {
        return bonusTroops;
    }

    public void setBonusTroops(int bonusTroops) {
        this.bonusTroops = bonusTroops;
    }
}
