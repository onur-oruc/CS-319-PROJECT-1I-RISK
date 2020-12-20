import enums.MotivationLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dice {

    // properties

    // constructors

    // methods
    /**
     * This method is used to roll dices and save the rolling results.
     * @param numDice This is the first paramter to rollDice method to denote how many times dice rolled
     * @param mLevel  This is the second parameter to denote motivation level of troops
     * @return ArrayList which denotes the results of rollings
     */
    public static ArrayList<Integer> rollDice(int numDice, MotivationLevel mLevel ) {

        int numMotivatedDice;

        // Determine the number of motivated dice according to motivation level
        if ( mLevel == MotivationLevel.NONE )
            numMotivatedDice = 0;
        else if ( mLevel == MotivationLevel.LOW )
            numMotivatedDice = 1;
        else if ( mLevel == MotivationLevel.NORMAL )
            numMotivatedDice = 2;
        else // if ( mLevel == MotivationLevel.HIGH )
            numMotivatedDice = 3;

        if ( numMotivatedDice > numDice )
            numMotivatedDice = numDice;

        // int[] output = new int[numDice];
        ArrayList<Integer> output = new ArrayList<>();


        while (numMotivatedDice > 0) {
            output.add( rollDie(true) );
            numMotivatedDice--;
            numDice--;
        }

        while (numDice > 0) {
            output.add( rollDie(false) );
            numDice--;
        }

        return output;
    }
    /**
     * This method is used to roll one dice and return the result
     * @param motivated This is the first paramter which denotes whether die is motivated or not.
     * @return int which denotes the results of rolling.
     */
    private static int rollDie( boolean motivated ) {
        double random = Math.random();

        if (motivated) {    // gives 2,3,4,5,6
            int value = (int) (random * 5);
            return value + 2;
        }
        else {              // gives 1,2,3,4,5,6
            int value = (int) (random * 6);
            return value + 1;
        }
    }
    /**
     * This method is used to compare dices of attacker side and defender side.
     * @param attacker This is the first paramter to compareDice method which denotes the results of rollings of attacker side
     * @param defender  This is the second parameter to compareDice method which denotes  the results of rollings of defender side
     * @return int[]  which denotes how many times attacker side succeed and how many times defender side succeed
     */
    public static int[] compareDice(ArrayList<Integer> attacker, ArrayList<Integer> defender) {

        int counter = 0;
        int atkLoss = 0;
        int defLoss = 0;
        Collections.sort(attacker,Collections.reverseOrder());
        Collections.sort(defender,Collections.reverseOrder());

        while ( ( counter < attacker.size() ) && ( counter < defender.size() ) ) {
            if ( attacker.get(counter) > defender.get(counter) ) {
                defLoss++;
            }
            else {
                atkLoss++;
            }

            counter++;
        }

        return new int[] { atkLoss, defLoss };
    }
}
