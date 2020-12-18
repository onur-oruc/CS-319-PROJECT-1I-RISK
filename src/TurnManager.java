import entities.Player;
import entities.Region;
import enums.MotivationLevel;
import enums.SeasonType;
import enums.StageType;

import java.util.Scanner;

public class TurnManager {

    // properties
    Player player;
    Region[] regions;
    int turnCount;
    SeasonType season;
    boolean plague, weather;
    boolean getsCard;

    StageType stage;
    int additionalTroops;

    final int MIN_ADDITIONAL_TROOPS = 3;

    final int PRICE_OF_MERCENARY = 10;
    final int PRICE_OF_ENTERTAINMENT = 5;

    final int DROUGHT_LIMIT = 5;
    final double FROST_AVG_SURVIVING_PROPORTION = 0.5;
    // final int FROST_SURVIVORS_DEVIATION =

    final int MAX_NUM_ATTACKERS = 3;
    final int MAX_NUM_DEFENDERS = 2;

    final int PLAGUE_LIMIT = 10;
    final double PLAGUE_COEFFICIENT = 0.025;


    // constructor
    public TurnManager( Player player, Region[] regions, boolean plague, boolean weather,
                        SeasonType season, int turnCount ) {
        this.player = player;
        this.regions = regions;
        this.plague = plague;
        this.weather = weather;
        this.season = season;
        this.turnCount = turnCount;

        additionalTroops = Math.max(player.getRegionCount() / 3, MIN_ADDITIONAL_TROOPS);
        getsCard = false;

        stage = StageType.BUY;
    }

    // methods
    public void operate() {
        beginTurnOps();
        System.out.println();
        buy();
        System.out.println();
        draft();
        System.out.println();
        attack();
        System.out.println();
        fortify();
        System.out.println();
        endTurnOps();
    }

    public void beginTurnOps() {
        System.out.println("***BEGIN");
        System.out.println("BEGIN***");
    }

    public void buy() {

        System.out.println("***BUY");

        Scanner scan = new Scanner(System.in);
        int input;

        do {

            int money = player.getMoney();
            if (money < PRICE_OF_MERCENARY)
                System.out.println("*mercenary red*");

            if (money < PRICE_OF_ENTERTAINMENT)
                System.out.println("*entertainment red*");

            input = scan.nextInt();

            if (input == 1) // if clicked on mercenary
            {
                if (money < PRICE_OF_MERCENARY)
                    System.out.println("not enough money for mercenary");
                else
                {
                    additionalTroops++;
                    player.setMoney(money - PRICE_OF_MERCENARY);
                }

            }
            else if (input == 2) // if clicked on entertainment
            {
                if (money < PRICE_OF_ENTERTAINMENT)
                    System.out.println("not enough money for entertainment");
                else
                {
                    regions[scan.nextInt()].setMotivation(MotivationLevel.HIGH);    // todo
                    player.setMoney(money - PRICE_OF_ENTERTAINMENT);
                }
            }
        } while ( input != 0 );

        stage = StageType.DRAFT;
        System.out.println("BUY***");

    }

    public void draft() {
        System.out.println("***DRAFT");

        Scanner scan = new Scanner(System.in);
        Region selectedRegion;
        int troopsToDraft;

        while (additionalTroops > 0) {
            System.out.println("There are " + additionalTroops + " troops to draft");

            do {
                System.out.println("Enter a region ID");
                selectedRegion = regions[scan.nextInt()];
            } while ( selectedRegion.getOwnerID() != player.getId() );

            System.out.println("Enter no of troops to draft on the chosen region");
            troopsToDraft = Math.min(scan.nextInt(), additionalTroops);

            selectedRegion.setNumTroops( selectedRegion.getNumTroops() + troopsToDraft);

            additionalTroops = additionalTroops - troopsToDraft;
        }

        stage = StageType.ATTACK;
        System.out.println("DRAFT***");

    }

    public void attack() {

        System.out.println("***ATTACK");

        Scanner scan = new Scanner(System.in);
        Region attackerRegion;
        Region defenderRegion;
        int numAttackerDice;
        int numDefenderDice;

        System.out.println("Choose an owned region to attack with or -1 to end");
        int attackerRegionID = scan.nextInt();


        while ( attackerRegionID >= 0 && attackerRegionID < regions.length && regions[attackerRegionID].getOwnerID() == player.getId() ) {


            // do whiles are for exceptions
            do {
                System.out.println("Attacking with " + attackerRegionID);
                attackerRegion = regions[attackerRegionID];
            } while ( attackerRegion.getOwnerID() != player.getId() );

            do {
                System.out.println("Choose an enemy region by ID");
                defenderRegion = regions[scan.nextInt()];
            } while ( defenderRegion.getOwnerID() == player.getId() );


            do {
                System.out.println("Enter no of dice to use: 1 - " + Math.min(attackerRegion.getNumTroops() - 1, MAX_NUM_ATTACKERS));
                numAttackerDice = Math.min(attackerRegion.getNumTroops() - 1, scan.nextInt());
                numDefenderDice = Math.min(defenderRegion.getNumTroops(), MAX_NUM_DEFENDERS);
                System.out.println("Num troops\t: " + attackerRegion.getNumTroops() + "\t: " + defenderRegion.getNumTroops());
                System.out.println("Num dice\t: " + numAttackerDice + "\t: " + numDefenderDice);
            } while ( numAttackerDice > 0 && oneTimeAttack(attackerRegion, numAttackerDice, defenderRegion, numDefenderDice) );

            // conquering case
            if ( defenderRegion.getNumTroops() <= 0 ) {
                System.out.println("Enemy region with ID " + defenderRegion.getRegionID() + " is conquered!");
                System.out.println("How many troops go there? At least " + numAttackerDice + " troops should go." );

                defenderRegion.setOwnerID(player.getId());
                getsCard = true;

                int numTroopsToMove = Math.max(scan.nextInt(),numAttackerDice);    // the troops you attack with have to move to the conquered region
                moveTroops(attackerRegion,defenderRegion,numTroopsToMove);
            }

            System.out.println("Choose an owned region to attack with OR enter -1 to quit pattern");
            attackerRegionID = scan.nextInt();
        }

        stage = StageType.FORTIFY;
        System.out.println("ATTACK***");
    }

    public void fortify() {

        System.out.println("***FORTIFY");

        Scanner scan = new Scanner(System.in);
        Region origin, destination;
        int numTroopsToMove;

        System.out.println("Choose an owned region to move troops FROM");
        do {
            System.out.println("Enter a region ID");
            origin = regions[scan.nextInt()];
        } while ( origin.getOwnerID() != player.getId() );


        System.out.println("Choose an owned region to move troops TO");
        do {
            System.out.println("Enter a region ID");
            destination = regions[scan.nextInt()];
        } while ( destination.getOwnerID() != player.getId() );

        numTroopsToMove = scan.nextInt();

        moveTroops(origin,destination,numTroopsToMove);

        stage = StageType.END;
        System.out.println("FORTIFY***");

    }

    public void endTurnOps() {
        System.out.println("***END TURN");

        // possible season change
        // possible weather updates

        // possible plague updates
        int numTroops;
        double plagueRisk;

        for ( Integer regionID : player.getRegionIds() ) {
            numTroops = regions[regionID].getNumTroops();

            if ( numTroops <= PLAGUE_LIMIT )
                plagueRisk = 0;
            else
                plagueRisk = ((double) (numTroops)) * PLAGUE_COEFFICIENT;

            if ( Math.random() < plagueRisk ) {
                regions[regionID].setPlague(true);
                System.out.println("PLAGUE appeared on region " + regionID);
            }
        }

        // possible motivation updates
        // possible gold mine appearances

        System.out.println("END TURN***");
    }




    // returns false if attacking path is no longer available
    public boolean oneTimeAttack( Region attacker, int numAttackerDice, Region defender, int numDefenderDice ) {

        int[] loss = Dice.compareDice(  Dice.rollDice( numAttackerDice, attacker.getMotivation()),
                                        Dice.rollDice( numDefenderDice, defender.getMotivation()));

        attacker.setNumTroops( attacker.getNumTroops() - loss[0] );
        defender.setNumTroops( defender.getNumTroops() - loss[1] );

        System.out.println("Loss\t\t: -" + loss[0] + "\t: -" + loss[1]);


        if ( defender.getNumTroops() <= 0 || attacker.getNumTroops() <= 1 ) // if pattern is unavailable
            return false;

        return true;
    }


    public int moveTroops( Region origin, Region destination, int numTroops)
    {
        System.out.println("From " + origin.getRegionID() + " to " + destination.getRegionID() + ", " + numTroops + " troops are sent.");

        if ( numTroops > 0 )
        {
            numTroops = Math.min( numTroops, origin.getNumTroops() - 1 );   // (EXCEPTION) max no of troops that can be moved from any region

            if ( origin.hasDrought() )
                numTroops = Math.min( numTroops, DROUGHT_LIMIT );   // (EXCEPTION) max no of troops that can be moved from a region with drought

            origin.setNumTroops( origin.getNumTroops() - numTroops );   // troops depart from origin

            if ( origin.hasFrost() )
                numTroops = (int) (numTroops * FROST_AVG_SURVIVING_PROPORTION); // only a proportion survives the frost

            if ( numTroops > 0 ) {
                destination.setNumTroops( destination.getNumTroops() + numTroops ); // troops arrive at destination

                if ( origin.hasPlague() )
                    destination.setPlague(true);    // plague is spread

            }

        }

        System.out.println("From " + origin.getRegionID() + " to " + destination.getRegionID() + ", " + numTroops + " troops arrived alive");
        return numTroops; // troops that arrived at the destination
    }


    public boolean areRegionsConnected() {
        // who knows
        return true;
    }



}
