package mission;

import entities.Challenger;
import entities.Player;

import java.util.ArrayList;

/**
 * Occupy 18 territories with at least two armies in each territory
 *
 */
public class ConquerNumRegionMission implements Mission{
    @Override
    public boolean isCompleted(Challenger challenger) {
        int regionCount = challenger.getRegionCount();
        int regionWithAtLeast2Troops = 0;
        ArrayList<Integer> regionIds = challenger.getRegionIds();

        if (regionCount >= 18) {
            for ()
        }

        return false;
    }

    public ConquerNumRegionMission() {

    }
}
