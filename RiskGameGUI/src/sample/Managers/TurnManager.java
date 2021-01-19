package sample.Managers;
import sample.Entities.Challenger;
import sample.Entities.Continent;
import sample.Entities.Player;
import sample.Entities.Region;
import sample.Enums.*;


import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class TurnManager implements Serializable {
    // constants


    private final int DROUGHT_LIMIT = 5;
    private final double FROST_AVG_SURVIVING_PROPORTION = 0.5;
    private final int MIN_ADDITIONAL_TROOPS = 3;
    private final int PRICE_OF_MERCENARY = 3;
    private final int PRICE_OF_ENTERTAINMENT = 5;
    private final int GOLD_MINE_GAIN = 10;
    private final int MAX_NUM_ATTACKERS = 3;
    private final int MAX_NUM_DEFENDERS = 2;
    private final int PLAGUE_LIMIT = 10;
    private final int PLAGUE_RECOVER_LIMIT = 4;
    private final double PLAGUE_COEFFICIENT = 0.025;
    private final double PLAGUE_AVG_SURVIVING_PROPORTION = 0.30;

    private final int MAX_NUM_LINES = 7;

    // properties
    private Player player;
    private Player[] players;
    private Region[] regions;
    private Continent[] continents;
    private int turnCount;
    private SeasonType season;
    private boolean plague, weather;
    private boolean getsCard;
    private int additionalTroops;
    private String message;

    // constructor
    public TurnManager (Player player, Player[] players, Region[] regions, Continent[] continents,
                        boolean plague, boolean weather, SeasonType season, int turnCount ) {
        this.player = player;
        this.players = players;
        this.regions = regions;
        this.continents = continents;
        this.plague = plague;
        this.weather = weather;
        this.season = season;
        this.turnCount = turnCount;
        message = "";
        beginTurnOps();
    }

    // methods

    public String getMessage() {
        return message;
    }
    public int getAdditionalTroops() {
        return additionalTroops;
    }
    public int getDROUGHT_LIMIT() {
        return DROUGHT_LIMIT;
    }

    public int getPRICE_OF_MERCENARY() {
        return PRICE_OF_MERCENARY;
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

        if ( money >= PRICE_OF_ENTERTAINMENT && region.getMotivation() != MotivationLevel.HIGH ) {
            region.motivate();
            player.setMoney(money - PRICE_OF_ENTERTAINMENT);
        }
    }

    public void draft( Region region, int numTroopsToDraft ) {
        region.setNumTroops( region.getNumTroops() + numTroopsToDraft );
        additionalTroops -= numTroopsToDraft;
    }

    public void beginTurnOps() {

        // bonus troops
        int bonusFromReg = Math.max(player.getRegionCount() / 3, MIN_ADDITIONAL_TROOPS);
        int bonusFromCont = player.calculateBonusTroops(regions,continents);

        additionalTroops = bonusFromReg + bonusFromCont;
        msgAddition("" + additionalTroops + " bonus troops. " + bonusFromReg + " from " + player.getRegionCount() + " regions, " + bonusFromCont + " from continents.");

        // gold
        earnGold();

        // plague
        blackDeath();
        recoverFromPlague();

        getsCard = false;
    }


    public void endTurnOps() {

        // gold
        emergeGoldMine();

        // plague
        emergePlague();

        // troop card
        giveTroopCard();
    }

    /**
     * This method calculates the possibility of occurring
     * plague in the regions with troops more than plague limit
     */
    private void emergePlague() {
        int numTroops;
        double plagueRisk;
        Region region;

        for (Integer regionID : player.getRegionIds() ) {
            region = regions[regionID];
            numTroops = region.getNumTroops();

            if (numTroops <= PLAGUE_LIMIT)
                plagueRisk = 0;
            else
                plagueRisk = ((double) (numTroops)) * PLAGUE_COEFFICIENT;

            if ( Math.random() < plagueRisk ) {
                region.setPlague(true);
                msgAddition( "\nPLAGUE: Emerges in " + region.getRegionName() + "." );
            }
        }
    }

    private void blackDeath() {
        int numTroops;
        Region region;
        for (Integer regionID : player.getRegionIds()) {
            region = regions[regionID];

            if ( region.hasPlague()) {
                numTroops = region.getNumTroops();
                msgAddition( "\nPLAGUE: Of " + numTroops + " troops in " + region.getRegionName() + ", " );
                region.setNumTroops( (int)( numTroops * PLAGUE_AVG_SURVIVING_PROPORTION) );
                msgAddition( "only " + region.getNumTroops() + " are still alive." );
                region.demotivate();
            }
        }
    }

    /**
     * This method manages the recovery of a region with plague
     * If the troop number in the region with a plague becomes
     * less than plague recovery limit, they will recover
     */
    private void recoverFromPlague() {
        int numTroops;
        Region region;

        for (Integer regionID : player.getRegionIds()) {
            region = regions[regionID];
            numTroops = region.getNumTroops();
            if ( region.hasPlague()) {
                if (numTroops <= PLAGUE_RECOVER_LIMIT) {
                    region.setPlague(false);
                    msgAddition( "\nPLAGUE: " + region.getRegionName() + " recovers from the plague!" );
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

            if( loser.getRegionCount() == 0)
                loser.setEliminated(true);

            if ( loser.getCommanderLocation() == defenderRegion.getOwnerID() ) {
                //int destinationID = loser.getRegionIds().get((int) (Math.random() * loser.getRegionCount()));
                //moveCommander(regions[destinationID]);
                loser.setCommanderLocation(-1);
                defenderRegion.setHasCommander(false);
            }

            if ( loser.isEliminated() ) {
                for ( TroopCardType card : loser.getTroopCards()) {
                    player.addTroopCard(card);
                }
                loser.setTroopCards(null);
            }

            getsCard = true;
        }
        return arr;
    }

    public int moveTroops( Region origin, int numTroops, Region destination)
    {
        String additionalMsg = "";

        msgAddition( "Move " + numTroops + " troops from " + origin.getRegionName() + " to " + destination.getRegionName() + "." );

        if ( numTroops > 0 )
        {
            numTroops = Math.min( numTroops, origin.getNumTroops() - 1 );   // (EXCEPTION) max no of troops that can be moved from any region

            if ( origin.hasDrought() && numTroops > DROUGHT_LIMIT ) {
                numTroops = DROUGHT_LIMIT;   // (EXCEPTION) max no of troops that can be moved from a region with drought
                additionalMsg += "\nDROUGHT: Troops to depart are down to " + DROUGHT_LIMIT + ".";
            }

            origin.setNumTroops( origin.getNumTroops() - numTroops );   // troops depart from origin

            if ( origin.hasFrost() ) {
                additionalMsg += "\nFROST: Of " + numTroops + " troops which departed, ";
                numTroops = (int) (numTroops * FROST_AVG_SURVIVING_PROPORTION); // only a proportion survives the frost
                additionalMsg += "only " + numTroops + " arrived.";
            }

            if ( numTroops > 0 ) {
                destination.setNumTroops( destination.getNumTroops() + numTroops ); // troops arrive at destination

                if ( origin.hasPlague() ) {
                    destination.setPlague(true);    // plague is spread
                    additionalMsg += "\nPLAGUE: Emerges in " + destination.getRegionName() + ".";
                }
            }
        }

        msgAddition( additionalMsg );

        return numTroops; // troops that arrived at the destination
    }

    public void moveCommander(Region destination) {
        if (player.getRegionIds().contains(destination.getRegionID())) {

            int oldComLoc = player.getCommanderLocation();

            if (oldComLoc >= 0) {
                Region oldComRegion = regions[oldComLoc];
                oldComRegion.setHasCommander(false);
                oldComRegion.demotivate();
            }

            destination.setHasCommander(true);
            commanderEffect();
            player.setCommanderLocation(destination.getRegionID());
        }
    }

    public void commanderEffect() {
        Region region;
        for ( Integer regionID : player.getRegionIds() ) {
            region = regions[regionID];
            if ( region.hasCommander() ) {
                region.motivate();
                break;
            }
        }
    }

    private void emergeGoldMine() {
        int randomRegionID = ((int) (Math.random() * regions.length));
        Region randomRegion = regions[randomRegionID];

        if ( !randomRegion.hasGoldMine() ) {
            randomRegion.setHasGoldMine(true);
            msgAddition( "\nGold mine found in " + randomRegion.getRegionName() + "!" );
        }
    }

    private void earnGold() {
        Region region;

        for ( Integer regionID : player.getRegionIds() ) {
            region = regions[regionID];

            if ( region.hasGoldMine() ) {
                player.setMoney( player.getMoney() + GOLD_MINE_GAIN );
                region.setHasGoldMine(false);
                msgAddition( "\nGOLD: Earned " + GOLD_MINE_GAIN + " from " + region.getRegionName() + " gold mines." );
            }
        }
    }

    private void msgShortenExceeding() {
        String[] allLines = message.split("\n");
        int numLinesToDelete = allLines.length - MAX_NUM_LINES;

        if ( numLinesToDelete > 0)
            message = message.split("\n", numLinesToDelete + 1 )[numLinesToDelete];
    }

    private void msgAddition( String addition ) {
        message += addition;
        msgShortenExceeding();
    }

    public void killMessage() {
        //message = "";
    }
}