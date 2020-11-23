public class Dice {

    // properties



    // constructors



    // methods
    public static int roll( boolean motivated ) {

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
}
