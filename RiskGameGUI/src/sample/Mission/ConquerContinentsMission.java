package sample.Mission;

import sample.Entities.Challenger;
import sample.Entities.Continent;
import sample.Entities.Player;

import java.io.Serializable;

/**
 * This class will be used to assign "conquer continents mission" to a player,
 * and to check whether the mission has been completed.
 *
 * There are 6 possibilities related to this mission
 *
 * 1. Asia and South America
 * 2. Asia and Africa
 * 3. North America and Africa
 * 4. North America and Australia
 * 5. Europe and South America and a 3rd continent of your choice
 * 6. Europe and Australia and a 3rd continent of your choice
 *
 * @author Onur Oruç
 * @author Ömer Yavuz Öztürk
 *
 */
public class ConquerContinentsMission implements Mission, Serializable {
    // constants
    final int[] ASIA_SOUTH_AMERICA = {0, 1};
    final int[] ASIA_AFRICA = {0, 2};
    final int[] NORTH_AMERICA_AFRICA = {3, 2};
    final int[] NORTH_AMERICA_AUSTRALIA = {3,4};
    final int[] EUROPE_SOUTH_AMERICA = {5,1}; // + one continent of Player's choice
    final int[] EUROPE_AUSTRALIA = {5,4}; // + one continent of Player's choice

    // properties
    private Continent[] continents;
    private int missionNum;
    private int[] continentIDs;
    private String missionName;

    public ConquerContinentsMission (Continent[] cont) {
        missionNum = (int)(Math.random() * 6) + 1;
        if (missionNum == 1)
            missionName = "Conquer Asia and South America";
        else if (missionNum == 2)
            missionName = "Conquer Asia and Africa";
        else if (missionNum == 3)
            missionName = "Conquer North America Africa";
        else if (missionNum == 4)
            missionName = "Conquer North America and Australia";
        else if (missionNum == 5)
            missionName = "Conquer Europe, South America, +1 continent";
        else
            missionName = "Conquer Europe, Australia, +1 continent";
        // initialize continents
        continents = cont;
    }

    @Override
    public boolean isCompleted(Challenger challenger) {
        int totalContinentsOfChallenger = 0;

        if (missionNum == 1) {
            return (iterateMissionArrays(challenger, ASIA_SOUTH_AMERICA));
        }
        else if (missionNum == 2) {
            return iterateMissionArrays(challenger, ASIA_AFRICA);
        }
        else if (missionNum == 3) {
            return iterateMissionArrays(challenger, NORTH_AMERICA_AFRICA);
        }
        else if (missionNum == 4) {
            return iterateMissionArrays(challenger, NORTH_AMERICA_AUSTRALIA);
        }
        else if (missionNum == 5) {
            if (iterateMissionArrays(challenger, EUROPE_SOUTH_AMERICA)) {
                return iterateFifthAndSixthMission(challenger, EUROPE_SOUTH_AMERICA);
            }
        }
        else {
            if (iterateMissionArrays(challenger, EUROPE_AUSTRALIA)) {
                return iterateFifthAndSixthMission(challenger, EUROPE_AUSTRALIA);
            }
        }

        return false;
    }

    /**
     * This method calculates the number of continents related to the mission that the challenger owns
     * This method is private as it is an auxiliary method that is needed only in
     * isCompleted() of this class.
     *
     * @param challenger
     * @param mission the mission that challenger has to complete to win the game.
     * @return true if challenger has the required continents.
     */
    private boolean iterateMissionArrays(Challenger challenger, int[] mission) {
        int totalContinentsOfChallenger = 0;

        for (int i = 0; i < mission.length; i++) {
            if (challenger.hasContinent(challenger, mission[i], continents))
                totalContinentsOfChallenger++;
        }

        return totalContinentsOfChallenger == mission.length;
    }

    /**
     * This method checks, when the challenger has to complete 5th or 6th mission,
     * whether the challenger has one extra continent of choice.
     * This method is private as it is an auxiliary method that is needed only in
     * isCompleted() of this class.
     *
     * @param challenger
     * @param mission   5th or 6th mission array with continent ids.
     * @return true if challenger has one extra continent other than the continents belonging to the corresponding mission.
     */
    private boolean iterateFifthAndSixthMission(Challenger challenger, int[] mission) {
        int firstContinentIdInMission = mission[0];
        int secondContinentIdInMission = mission[1];

        for (Continent continent : continents) {
            int continentID = continent.getContinentId();

            // check challenger has an extra continent other than the continents in the mission
            if (continentID != firstContinentIdInMission && continentID != secondContinentIdInMission) {
                if (challenger.hasContinent(challenger, continentID, continents))
                    return true;
            }
        }

        return false;
    }

    public String getMissionName() {
        return missionName;
    }
}
