package sample.Managers;

import sample.Enums.MotivationLevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Dice implements Serializable {

    // properties

    // constructors

    // methods
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
