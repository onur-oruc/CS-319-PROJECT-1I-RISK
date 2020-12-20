package sample.Managers;
import sample.Entities.Challenger;
import sample.Entities.Continent;
import sample.Entities.Player;
import sample.Entities.Region;
import sample.Enums.*;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class TurnManager {
    // constants


    private final int DROUGHT_LIMIT = 5;
    private final double FROST_AVG_SURVIVING_PROPORTION = 0.5;

    private final int MIN_ADDITIONAL_TROOPS = 3;

    private final int PRICE_OF_MERCENARY = 3;
    private final int PRICE_OF_ENTERTAINMENT = 5;

    // final int FROST_SURVIVORS_DEVIATION =

    private final int GOLD_MINE_GAIN = 10;
    private final int MAX_NUM_ATTACKERS = 3;
    private final int MAX_NUM_DEFENDERS = 2;
    private final int PLAGUE_LIMIT = 10;
    private final int PLAGUE_RECOVER_LIMIT = 4;
    private final double PLAGUE_COEFFICIENT = 0.025;
    private final double HIGH_PROBABILITY = 0.2;
    private final double MEDIUM_PROBABILITY = 0.08;
    private final double LOW_PROBABILITY = 0.03;

    // properties
    private Player player;
    private Player[] players;
    private Region[] regions;
    private Continent[] continents;
    private int turnCount;
    private SeasonType season;
    private boolean plague, weather;
    private boolean getsCard;
    private StageType stage;


    private int additionalTroops;

    // constructor
    public TurnManager (Player player, Player[] players, Region[] regions, Continent[] continents, boolean plague, boolean weather,
                        SeasonType season, int turnCount) {
        this.player = player;
        this.players = players;
        this.regions = regions;
        this.continents = continents;
        this.plague = plague;
        this.weather = weather;
        this.season = season;
        this.turnCount = turnCount;

        //additionalTroops = Math.max(player.getRegionCount() / 3, MIN_ADDITIONAL_TROOPS);
        additionalTroops = 3;
        getsCard = false;

        stage = StageType.BEGIN;
    }

    // methods
    /*public void playTurn(){
        beginTurnOps();
        buy();
        draft();
        attack();
        fortify();
        endTurnOps();
    }*/

    public void beginTurnOps() {
        System.out.println("***BEGIN");

        // plague
        blackDeath();
        recoverFromPlague();

        // reset weathers in all regions


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
        stage = StageType.BUY;
        System.out.println("BEGIN***\n");
    }

    public int getAdditionalTroops() {
        return additionalTroops;
    }

    public int getDROUGHT_LIMIT() {
        return DROUGHT_LIMIT;
    }

    public void combineCards(ArrayList<TroopCardType> cardsToCombine) {
        ArrayList<TroopCardType> possibleCombination = new ArrayList<>();

        for (TroopCardType card : cardsToCombine) {
            if (!possibleCombination.contains(card))
                possibleCombination.add(card);
        }
        if (possibleCombination.size() == 3) {
            additionalTroops += 8;
            for (TroopCardType cardToRemove : possibleCombination) {
                player.removeCard(cardToRemove);
            }
            return;
        }

        possibleCombination = new ArrayList<>();
        for (TroopCardType troopCard: cardsToCombine) {
            int numSameCard = 0;
            for (TroopCardType card : cardsToCombine) {
                if (troopCard == card) {
                    possibleCombination.add(card);
                    numSameCard++;
                }
                if (numSameCard == 3) {
                    additionalTroops += 8;
                }
            }
        }
        if (possibleCombination.size() == 3) {
            for (TroopCardType card: possibleCombination) {
                player.removeCard(card);
            }
        }
    }

    public void buyMercenaries( int numMercenaries ) {
        int money = player.getMoney();
        int cost = numMercenaries * PRICE_OF_MERCENARY;

        if (money < cost)
            System.out.println("not enough money for mercenary");
        else
        {
            additionalTroops += numMercenaries;
            player.setMoney(money - cost);
        }
    }

    public void organizeEntertainment( Region region ) {
        int money = player.getMoney();

        if ( money >= PRICE_OF_ENTERTAINMENT ) {
            region.motivate();
            player.setMoney(money - PRICE_OF_ENTERTAINMENT);
        }
    }

    public void draft( Region region, int numTroopsToDraft ) {

            region.setNumTroops( region.getNumTroops() + numTroopsToDraft );
            additionalTroops -= numTroopsToDraft;
    }

    /*public void attack() {

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
            System.out.println("Attacking with " + attackerRegionID);
            attackerRegion = regions[attackerRegionID];

            ArrayList<Integer> closeEnemies = attackerRegion.getEnemyRegions( player.getRegionIds() );

            for ( Integer i : closeEnemies ) {
                System.out.print(i + ",");
            }
            System.out.println();

            do {
                System.out.println("Choose an enemy region by ID");
                defenderRegion = regions[scan.nextInt()];
            } while ( !closeEnemies.contains(defenderRegion.getRegionID()) );

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

                Player loser = players[defenderRegion.getOwnerID()];

                defenderRegion.setOwnerID(player.getId());
                player.addRegion(defenderRegion.getRegionID());
                loser.removeRegion(defenderRegion.getRegionID());

                if ( loser.isEliminated() )
                {
                    for ( TroopCardType card : loser.getTroopCards()) {
                        player.addTroopCard(card);
                    }
                    loser.setTroopCards(null);
                }

                getsCard = true;

                int numTroopsToMove = Math.max(scan.nextInt(),numAttackerDice);    // the troops you attack with have to move to the conquered region
                moveTroops(attackerRegion,defenderRegion,numTroopsToMove);

                // move commander on this stage todo
            }

            System.out.println("Choose an owned region to attack with OR enter -1 to quit pattern");
            attackerRegionID = scan.nextInt();
        }

        stage = StageType.FORTIFY;
        System.out.println("ATTACK***\n");
    }*/

    /*public void fortify() {

        System.out.println("***FORTIFY");

        Scanner scan = new Scanner(System.in);
        Region origin, destination;
        int numTroopsToMove;

        System.out.println("Choose an owned region to move troops FROM");
        do {
            System.out.println("Enter a region ID");
            origin = regions[scan.nextInt()];
        } while ( origin.getOwnerID() != player.getId() && (origin.getNumTroops() == 1) );

        System.out.println("Choose an owned connected region to move troops TO");
        ArrayList<Integer> connectedOwnedRegions;

        do {
            connectedOwnedRegions = origin.getConnectedOwnedRegions(regions, player.getRegionIds());

            for ( Integer i : connectedOwnedRegions) {
                System.out.print( i + ",");
            }
            System.out.println("\nEnter a region ID from above");
            destination = regions[scan.nextInt()];

        } while ( !connectedOwnedRegions.contains( destination.getRegionID() ));

        System.out.println("How many troops should be moved? From 1 to " + (origin.getNumTroops() - 1));
        numTroopsToMove = scan.nextInt();

        stage = StageType.END;
        System.out.println("FORTIFY***\n");
    }*/

    public void endTurnOps() {
        System.out.println("***END TURN");

        // possible season change
        // possible weather updates
        // possible plague updates
        spreadPlague();

        // possible motivation updates
        // possible gold mine appearances
        // possible gold mine appearances
        giveTroopCard();

        System.out.println("END TURN***\n");
    }

    /**
     * This method will be used to change region weathers
     *
     */
    public void emergeWeather() {
        if (season == SeasonType.WINTER) {
            for (Region region : regions) {
                region.setDrought(false);
                ClimateType climate = region.getClimate();

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
                ClimateType climate = region.getClimate();

                if (climate == ClimateType.COLD) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(0.75)) {
                        region.setFrost(true);
                    }
                    else if (Math.random() <= MEDIUM_PROBABILITY*(0.25)) {
                        region.setDrought(true);
                    }
                }
                else if ( climate == ClimateType.HOT) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(0.25)) {
                        region.setFrost(true);
                    }
                    else if (Math.random() <= MEDIUM_PROBABILITY*(0.75)) {
                        region.setDrought(true);
                    }
                }
                else {
                    if (Math.random() <= MEDIUM_PROBABILITY*(0.50)) {
                        region.setFrost(true);
                    }
                    else if (Math.random() <= MEDIUM_PROBABILITY*(0.50)) {
                        region.setDrought(true);
                    }
                }

            }
        }
        else if (season == SeasonType.SUMMER) {
            for (Region region : regions) {
                region.setFrost(false);
                ClimateType climate = region.getClimate();

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
                ClimateType climate = region.getClimate();

                if ( climate == ClimateType.COLD) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(0.25)) {
                        region.setDrought(true);
                    } else if (Math.random() <= MEDIUM_PROBABILITY*(0.75)) {
                        region.setFrost(true);
                    }
                }
                else if ( climate == ClimateType.HOT) {
                    if (Math.random() <= MEDIUM_PROBABILITY*(0.75)) {
                        region.setDrought(true);
                    } else if (Math.random() <= MEDIUM_PROBABILITY*(0.25)) {
                        region.setFrost(true);
                    }
                }
                else {
                    if (Math.random() <= MEDIUM_PROBABILITY*(0.50)) {
                        region.setDrought(true);
                    } else if (Math.random() <= MEDIUM_PROBABILITY*(0.50)) {
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
    private void spreadPlague() {
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

    private void blackDeath() {

    }

    /**
     * This method manages the recovery of a region with plague
     * If the troop number in the region with a plague becomes
     * less than plague recovery limit, they will recover
     */
    private void recoverFromPlague() {
        int numTroops;

        for (Integer regionID : player.getRegionIds()) {
            numTroops = regions[regionID].getNumTroops();
            if ( regions[regionID].hasPlague()) {
                if (numTroops <= PLAGUE_RECOVER_LIMIT) {
                    regions[regionID].setPlague(false);
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

    public void fortify( Region origin, int troopsToMove, Region destination) {
        moveTroops(origin,troopsToMove,destination);
    }



    // use getEnemyRegions() method beforehand
    // returns an ArrayList<Object> of three which contains attacker's dice, defender's dice, loss of both sides ([0]: attacker's loss, [1]: defender's loss [1])
    public ArrayList<Object> oneTimeAttack( Region attackerRegion, int numAttackerDice, Region defenderRegion) {

        int numDefenderDice = Math.min(defenderRegion.getNumTroops(), MAX_NUM_DEFENDERS);

        ArrayList<Object> arr = new ArrayList<Object>();
        arr.add( Dice.rollDice( numAttackerDice, attackerRegion.getMotivation()));
        arr.add( Dice.rollDice( numDefenderDice, defenderRegion.getMotivation()));

        int[] loss = Dice.compareDice( (ArrayList<Integer>) arr.get(0), (ArrayList<Integer>) arr.get(1) );
        attackerRegion.setNumTroops(attackerRegion.getNumTroops() - loss[0]);
        defenderRegion.setNumTroops(defenderRegion.getNumTroops() - loss[1]);

        arr.add(loss);

        // conquering case
        if ( defenderRegion.getNumTroops() <= 0 ) {
            Player loser = players[defenderRegion.getOwnerID()];
            defenderRegion.setOwnerID(player.getId());
            player.addRegion(defenderRegion.getRegionID());
            loser.removeRegion(defenderRegion.getRegionID());

            // This segment might become invalid if the rule about commander repeatedly respawning is changed
            if ( loser.getCommanderLocation() == defenderRegion.getOwnerID() ) {

                if ( loser.isEliminated() )
                {
                    for ( TroopCardType card : loser.getTroopCards()) {
                        player.addTroopCard(card);
                    }
                    loser.setTroopCards(null);

                    defenderRegion.setHasCommander(false);
                }
                else {
                    int destinationID = loser.getRegionIds().get((int) (Math.random() * loser.getRegionCount()));
                    moveCommander(regions[destinationID]);
                }
            }
            getsCard = true;
        }
        return arr;
    }


    public int moveTroops( Region origin, int numTroops, Region destination)
    {
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

                if ( origin.hasPlague() ) {
                    destination.setPlague(true);    // plague is spread
                }
            }
        }
        return numTroops; // troops that arrived at the destination
    }


    public void moveCommander( Region destination ) {
        regions[player.getCommanderLocation()].setHasCommander(false);
        destination.setHasCommander(true);
        player.setCommanderLocation(destination.getRegionID());
    }
}
