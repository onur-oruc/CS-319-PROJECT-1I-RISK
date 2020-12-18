package mission;

import entities.Challenger;
import entities.Player;

public class ConquerWorldMission implements Mission{
    @Override
    public boolean isCompleted(Challenger challenger) {
        return false;
    }
}
