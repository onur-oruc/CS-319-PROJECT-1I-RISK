package sample.Mission;

import sample.Entities.Challenger;
import sample.Entities.Player;

public interface Mission {
    boolean isCompleted(Challenger challenger);
    String getMissionName();
}
