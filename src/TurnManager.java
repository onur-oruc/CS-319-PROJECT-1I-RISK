import entities.Continent;
import entities.Player;
import entities.Region;
import enums.*;

import java.util.Scanner;

public class TurnManager {
    // constants
    private final int PRICE_OF_MERCENARY = 10;
    private final int PRICE_OF_ENTERTAINMENT = 5;
    private final int DROUGHT_LIMIT = 5;
    private final double FROST_AVG_SURVIVING_PROPORTION = 0.5;
    // final int FROST_SURVIVORS_DEVIATION =

    private final int GOLD_MINE_GAIN = 10;
    private final int MAX_NUM_ATTACKERS = 3;
    private final int MAX_NUM_DEFENDERS = 2;
    private final int PLAGUE_LIMIT = 10;
    private final int PLAGUE_RECOVER_LIMIT = 4;
    private final double PLAGUE_COEFFICIENT = 0.025;
    private final double WEATHER_COEFFICIENT = 0.025;
    private final double HIGH_PROBABILITY = 0.2;
    private final double MEDIUM_PROBABILITY = 0.08;
    private final double LOW_PROBABILITY = 0.03;

    // properties
    private Player player;
    private Region[] regions;
    private Continent[] continents;
    private int turnCount;
    private SeasonType season;
    private boolean plague, weather;
    private boolean getsCard;
    private StageType stage;
    private int additionalTroops;
    private ClimateType climate;


    // constructor
    public TurnManager (Player player, Region[] regions, Continent[] continents, boolean plague, boolean weather,
                        SeasonType season, int turnCount, ClimateType climate) {
        this.player = player;
        this.regions = regions;
        this.continents = continents;
        this.plague = plague;
        this.weather = weather;
        this.season = season;
        this.turnCount = turnCount;
        this.climate = climate;

        additionalTroops = Math.max(player.getRegionCount() / 3, 3); // ?

        stage = StageType.BUY;
    }

    // methods
    public void buy() {

        Scanner scan = new Scanner(System.in);
        int input = 3;

        while ( input != 0 ) {

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
        }

        stage = StageType.DRAFT;

    }

    public void draft() {
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
    }

    public void attack() {
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

                int numTroopsToMove = Math.max(scan.nextInt(),numAttackerDice);    // the troops you attack with have to move to the conquered region
                moveTroops(attackerRegion,defenderRegion,numTroopsToMove);
            }

            System.out.println("Choose an owned region to attack with OR enter -1 to quit pattern");
            attackerRegionID = scan.nextInt();
        }

        stage = StageType.FORTIFY;
    }

    public void fortify() {
        Scanner scan = new Scanner(System.in);
        Region origin, destination;
        int numTroopsToMove;

        System.out.println("Choose an owned region to move troops from OR -1 to end");
        int attackerRegionID = scan.nextInt();
    }

    public void beginTurnOps() {
        // reset weathers in all regions
        for ( Region region: regions) {
            region.setDrought(false);
            region.setFrost(false);
        }

        // update player's money according to the the gold mined owned
        for ( Integer regionID : player.getRegionIds() ) {
            if (regions[regionID].hasGoldMine()) {
                player.setMoney(player.getMoney() + GOLD_MINE_GAIN);
            }
        }

        // calculate extra regions in case the challenger has continent(s)
        for (int i = 0; i < continents.length; i++) {
            if (player.hasContinent(player, continents[i].getContinentId(),continents)) {
                additionalTroops += continents[i].getBonusTroops();
            }
        }

    }

    public void endTurnOps() {
        // possible season change
        // possible weather updates


        // possible plague updates
        spreadPlague();

        // possible motivation updates
        // possible gold mine appearances
    }

    /**
     * This method will be used to change region weathers
     *
     */
    public void emergeWeather() {
        if (season == SeasonType.WINTER) {
            for (Region region : regions) {
                region.setDrought(false);

                // calculate frost possibility
                if (climate == ClimateType.COLD) { // % 20
                    if (Math.random() <= HIGH_PROBABILITY ) {
                        region.setFrost(true);
                    }
                }
                else if (climate == ClimateType.HOT) { // % 3
                    if (Math.random() <= LOW_PROBABILITY) {
                        region.setFrost(true);
                    }
                }
                else if (climate == ClimateType.WARM) { // % 8
                    if (Math.random() <= MEDIUM_PROBABILITY) {
                        region.setFrost(true);
                    }
                }
            }
        }
        else if ( season == SeasonType.SPRING) {
            for (Region region: regions) {
                if (climate == ClimateType.COLD) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(3/4)) {
                        region.setFrost(true);
                    }
                    else if (Math.random() <= MEDIUM_PROBABILITY*(1/4)) {
                        region.setDrought(true);
                    }
                }
                else if ( climate == ClimateType.HOT) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(1/4)) {
                        region.setFrost(true);
                    }
                    else if (Math.random() <= MEDIUM_PROBABILITY*(3/4)) {
                        region.setDrought(true);
                    }
                }
                else {
                    if (Math.random() <= MEDIUM_PROBABILITY*(1/2)) {
                        region.setFrost(true);
                    }
                    else if (Math.random() <= MEDIUM_PROBABILITY*(1/2)) {
                        region.setDrought(true);
                    }
                }

            }
        }
        else if (season == SeasonType.SUMMER) {
            for (Region region : regions) {
                region.setFrost(false);

                // calculate drought possibility
                if (climate == ClimateType.COLD) { // % 8
                    if (Math.random() <= LOW_PROBABILITY ) {
                        region.setDrought(true);
                    }
                }
                else if (climate == ClimateType.HOT) { // % 3
                    if (Math.random() <= HIGH_PROBABILITY) {
                        region.setDrought(true);
                    }
                }
                else if (climate == ClimateType.WARM) { // % 20
                    if (Math.random() <= MEDIUM_PROBABILITY) {
                        region.setDrought(true);
                    }
                }
            }
        }
        else { // fall
            for (Region region: regions) {
                if ( climate == ClimateType.COLD) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(1/4)) {
                        region.setDrought(true);
                    } else if (Math.random() <= MEDIUM_PROBABILITY*(3/4)) {
                        region.setFrost(true);
                    }
                }
                else if ( climate == ClimateType.HOT) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(3/4)) {
                        region.setDrought(true);
                    } else if (Math.random() <= MEDIUM_PROBABILITY*(1/4)) {
                        region.setFrost(true);
                    }
                }
                else {
                    if (Math.random() <= MEDIUM_PROBABILITY*(1/2)) {
                        region.setDrought(true);
                    } else if (Math.random() <= MEDIUM_PROBABILITY*(1/2)) {
                        region.setFrost(true);
                    }
                }
            }
        }
    }


    /**
     * This method calculates the possibility of occurring
     * plague in the regions with troops more than plague limit
     */
    public void spreadPlague() {
        int numTroops;
        double plagueRisk;

        for (Integer regionID : player.getRegionIds() ) {
            numTroops = regions[regionID].getNumTroops();

            if (numTroops <= PLAGUE_LIMIT)
                plagueRisk = 0;
            else
                plagueRisk = ((double) (numTroops)) * PLAGUE_COEFFICIENT;

            if (Math.random() < plagueRisk ) {
                regions[regionID].setPlague(true); // when will it be set to false?
                System.out.println("PLAGUE appeared on region " + regionID);
            }
        }
    }

    /**
     * This method manages the recovery of a region with plague
     * If the troop number in the region with a plague becomes
     * less than plague recovery limit, they will recover
     */
    public void recoverFromPlague() {
        int numTroops;

        for (Integer regionID : player.getRegionIds()) {
            numTroops = regions[regionID].getNumTroops();
            if ( regions[regionID].hasPlague()) {
                if (numTroops <= PLAGUE_RECOVER_LIMIT) {
                    regions[regionID].setPlague(true);
                }
            }
        }
    }

    /**
     * This method gives a troop card to the challenger,
     * if challenger conquer at least one region during
     * the attack stage
     *
     */
    public void giveTroopCard() {
        if (getsCard) {
            // choose a random troop card
            int random = (int)(Math.random()*4);

            if (random == 0)
                player.addTroopCard(TroopCardType.ARTILLERY);
            else if (random == 1)
                player.addTroopCard(TroopCardType.MERCENARY);
            else if (random == 2)
                player.addTroopCard(TroopCardType.MARINES);
            else
                player.addTroopCard(TroopCardType.INFANTRY);
        }
    }



    // returns false if attacking path is no longer available
    public boolean oneTimeAttack( Region attacker, int numAttackerDice, Region defender, int numDefenderDice ) {

        int[] loss = Dice.compareDice(  Dice.rollDice( numAttackerDice, attacker.getMotivation()),
                                        Dice.rollDice( numDefenderDice, defender.getMotivation()));

        attacker.setNumTroops( attacker.getNumTroops() - loss[0]);
        defender.setNumTroops( defender.getNumTroops() - loss[1]);

        System.out.println("Loss\t\t: -" + loss[0] + "\t: -" + loss[1]);


        if ( defender.getNumTroops() <= 0 || attacker.getNumTroops() <= 1 ) // if pattern is unavailable
            return false;

        return true;
    }


    public int moveTroops (Region origin, Region destination, int numTroops)
        /*throws NotEnoughTroops
        {
            if ( numTroops > origin.getNumTroops())
                throw new NotEnoughTroops(numTroops);
        }*/
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
