package mission;

import entities.Challenger;

/**
 * This class will be used to check whether a challenger
 * eliminated with an enemy with a specific id.
 *
 * @author Onur Oruç
 */
public class EliminateColorMission implements Mission {
    // property(ies)
    private int playerToBeEliminated;
    private Challenger target;

    // constructor(s)

    /**
     * Constructor chooses a random player id that has to be
     * eliminated for the mission among 0-playerCount,
     *
     * @param challenger
     * @param playerCount
     */
    public EliminateColorMission(Challenger challenger, Challenger[] allChallengers, int playerCount) {

        // choose a random challenger id until it is different than the
        // challenger that will be assigned to this mission.
        do {
            playerToBeEliminated = (int)(Math.random() * playerCount);
        } while ( playerToBeEliminated == challenger.getId());

        Challenger target = allChallengers[playerToBeEliminated];
    }

    // method(s)

    /**
     * This method checks whether the given challenger
     * eliminated the enemy with id -playerToBeEliminated
     * @param challenger
     * @return
     */
    @Override
    public boolean isCompleted(Challenger challenger) {
        return target.isEliminated();
        //return challenger.hasEliminatedEnemy(playerToBeEliminated);
    }

    public int getPlayerToBeEliminated() {
        return playerToBeEliminated;
    }
}