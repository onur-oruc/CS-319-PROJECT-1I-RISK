package mission;

import entities.Challenger;
import entities.Player;

public class ConquerWorldMission implements Mission {
    private int totalNumRegions;

    public ConquerWorldMission( int totalNumRegions) {
        this.totalNumRegions = totalNumRegions;
    }

    @Override
    public boolean isCompleted(Challenger challenger) {
        return challenger.getRegionCount() == totalNumRegions;
    }
}
