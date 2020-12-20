package sample.Entities;


import java.io.Serializable;

/**
 * This class will be used to store name, id, # of regions that the continent has,
 * region ids, and bonus troops that will be given to the continent owner
 * Continent objects will be initialized according to the information in txt given
 * as parameter to this class' constructor
 *
 * @author Onur Oruç
 *
 */
public class Continent implements Serializable {
    // Asia, South America, Africa, North America, Australia, Europe
    // id:0,       1            2        3             4         5

    // properties
    private String continentName;
    private int continentId;
    private int regionCount;
    private int[] regionIds;
    private int bonusTroops;

    // Constructor
    public Continent() {
        continentName = "";
        continentId = -1;
        regionCount = -1;
        regionIds = null;
        bonusTroops = -1;
    }

    // set methodları gereksiz

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
