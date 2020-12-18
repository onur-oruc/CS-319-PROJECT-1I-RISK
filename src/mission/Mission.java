package mission;

import entities.Challenger;
import entities.Player;

public interface Mission {
    boolean isCompleted(Challenger challenger);
}
