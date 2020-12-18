package mission;

import entities.Challenger;
import entities.Region;


/**
 * This class will be used to whether a challenger occupied
 * 18 territories with at least two armies in each territory
 * @author Onur Oruç
 */
public class ConquerNumRegionMission implements Mission {
    // constants
    private final int NUM_REGION_TO_OCCUPY = 18;

    // properties
    private Region[] allRegions;

    // all region's information should be passed as a parameter.
    public ConquerNumRegionMission (Region[] regions) {
        allRegions = new Region[regions.length];
        for ( int i = 0; i < regions.length; i++) {
            allRegions[i] = regions[i];
        }
    }

    // methods
    public void updateRegions (Region[] regions) {
        allRegions = new Region[regions.length];
        for (int i = 0; i < regions.length; i++) {
            allRegions[i] = new Region(regions[i]);
        }
    }

    @Override
    public boolean isCompleted (Challenger challenger) {
        int regionCount = challenger.getRegionCount();
        int regionWithAtLeast2Troops = 0;
        // ArrayList<Integer> regionIds = challenger.getRegionIds();

        int challengerID = challenger.getId();

        if (regionCount >= NUM_REGION_TO_OCCUPY) {
            for (int i = 0; i < allRegions.length; i++) {
                if ( allRegions[i].getOwnerId() == challengerID && allRegions[i].getNumTroops() >= 2)
                    regionWithAtLeast2Troops++;
            }
        }
        if (regionWithAtLeast2Troops >= NUM_REGION_TO_OCCUPY)
            return true;

        return false;
    }
}
