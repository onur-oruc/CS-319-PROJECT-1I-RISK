package sample.Mission;

import sample.Entities.Challenger;
import sample.Entities.Player;

import java.io.Serializable;

public interface Mission extends Serializable {
    boolean isCompleted(Challenger challenger);
    String getMissionName();
}
