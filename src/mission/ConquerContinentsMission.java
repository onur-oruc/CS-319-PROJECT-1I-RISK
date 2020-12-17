package mission;

import entities.Continent;
import entities.Player;
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
 *
 */
public class ConquerContinentsMission implements Mission {
    // constants
    final int[] ASIA_SOUTH_AMERICA = {0, 1};
    final int[] ASIA_AFRICA = {0, 2};
    final int[] NORTH_AMERICA_AFRICA = {3, 2};
    final int[] NORTH_AMERICA_AUSTRALIA = {3,4};
    final int[] EUROPE_SOUTH_AMERICA = {5,1};
    final int[] EUROPE_AUSTRALIA = {5,4};

    // properties
    Continent[] continents;
    int missionNum;
    int[] continentIDs;

    public ConquerContinentsMission( Continent[] continents) {
        int randomMission = (int)(Math.random() * 6);
        missionNum = randomMission + 1;

        // TO DO initialize continents
    }

    @Override
    public boolean isCompleted(Player player) {


        if (missionNum == 1) {

        }
        else if (missionNum == 2) {

        }
        else if (missionNum == 3) {

        }
        else if (missionNum == 4) {

        }
        else if (missionNum == 5) {

        }
        else {

        }

        return true;
    }
}
