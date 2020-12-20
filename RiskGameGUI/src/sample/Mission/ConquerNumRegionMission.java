package sample.Mission;

import sample.Entities.Challenger;
import sample.Entities.Region;


/**
 * This class will be used to check whether a challenger occupied
 * 18 territories with at least two armies in each territory
 * @author Onur Oruç
 * @author Ömer Yavuz Öztürk
 */
public class ConquerNumRegionMission implements Mission {
    // constants
    private final int NUM_REGION_TO_OCCUPY = 24;
    private final int MIN_NUM_TROOPS_ON_EACH = 1;

    // properties
    private final Region[] allRegions;
    private String missionName;

    // all region's information should be passed as a parameter.
    public ConquerNumRegionMission (Region[] regions) {
        missionName = "Conquer " + NUM_REGION_TO_OCCUPY + " with at least " + MIN_NUM_TROOPS_ON_EACH + " troops";
        allRegions = regions;
    }

    // methods
    @Override
    public boolean isCompleted (Challenger challenger) {
        int regionCount = challenger.getRegionCount();
        int regionWithAtLeast2Troops = 0;
        // ArrayList<Integer> regionIds = challenger.getRegionIds();

        int challengerID = challenger.getId();

        if (regionCount >= NUM_REGION_TO_OCCUPY) {
            for (int i = 0; i < allRegions.length; i++) {
                if ( allRegions[i].getOwnerID() == challengerID && allRegions[i].getNumTroops() >= MIN_NUM_TROOPS_ON_EACH)
                    regionWithAtLeast2Troops++;
            }
        }

        return regionWithAtLeast2Troops >= NUM_REGION_TO_OCCUPY;
    }

    public String getMissionName() {
        return missionName;
    }
}
