package sample.Mission;

import sample.Entities.Challenger;
import sample.Entities.Player;

public class ConquerWorldMission implements Mission {
    private int totalNumRegions;
    private String missionName;

    public ConquerWorldMission( int totalNumRegions) {
        missionName = "Conquer the world";
        this.totalNumRegions = totalNumRegions;
    }

    @Override
    public boolean isCompleted(Challenger challenger) {
        return challenger.getRegionCount() == totalNumRegions;
    }
    public String getMissionName() {
        return missionName;
    }
}
