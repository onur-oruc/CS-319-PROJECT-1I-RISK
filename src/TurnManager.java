import entities.Continent;
import entities.Player;
import entities.Region;
import enums.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class TurnManager {
    // constants

    private final int DROUGHT_LIMIT = 5;
    private final double FROST_AVG_SURVIVING_PROPORTION = 0.7;

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
    private final double PLAGUE_AVG_SURVIVING_PROPORTION = 0.30;

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
        this.regions = regions;
        this.continents = continents;
        this.plague = plague;
        this.weather = weather;
        this.season = season;
        this.turnCount = turnCount;

        additionalTroops = Math.max(player.getRegionCount() / 3, MIN_ADDITIONAL_TROOPS);
        getsCard = false;

        stage = StageType.BEGIN;
    }

    // methods
    public void playTurn() {
        beginTurnOps();
        endTurnOps();
    }

    public void beginTurnOps() {
        System.out.println("***BEGIN");

        // plague
        blackDeath();           // todo
        recoverFromPlague();

        // reset weathers in all regions // done in GameManager ?

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

    // assumes that the selected region is owned by the player on turn
    public void draft( Region region, int numTroopsToDraft ) {

        if ( additionalTroops > numTroopsToDraft ) {
            region.setNumTroops( region.getNumTroops() + numTroopsToDraft );
            additionalTroops -= numTroopsToDraft;
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

    // use getConnectedOwnedRegions() method beforehand
    public void fortify( Region origin, int troopsToMove, Region destination) {
        moveTroops(origin,troopsToMove,destination);
    }


    public void endTurnOps() {
        System.out.println("***END TURN");

        // possible season change
        // possible weather updates
        // possible plague updates
        emergePlague();

        // possible motivation updates
        // possible gold mine appearances
        // possible gold mine appearances

        giveTroopCard();

        System.out.println("END TURN***\n");
    }

    /**
     * This method calculates the possibility of occurring
     * plague in the regions with troops more than plague limit
     */
    private void emergePlague() {
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
        int numTroops;
        Region region;
        for (Integer regionID : player.getRegionIds()) {
            region = regions[regionID];

            if ( region.hasPlague()) {
                numTroops = region.getNumTroops();
                region.setNumTroops( (int)( numTroops * PLAGUE_AVG_SURVIVING_PROPORTION) );
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
            if ( region.hasPlague()) {
                numTroops = region.getNumTroops();
                if (numTroops <= PLAGUE_RECOVER_LIMIT) {
                    region.setPlague(false);
                    region.demotivate();
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






    private int moveTroops( Region origin, int numTroops, Region destination)
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

    public int getAdditionalTroops() {
        return additionalTroops;
    }

    public void nextStage() {
        if ( stage == StageType.BEGIN )
            stage = StageType.BUY;
        else if ( stage == StageType.BUY )
            stage = StageType.DRAFT;
        else if ( stage == StageType.DRAFT )
            stage = StageType.ATTACK;
        else if ( stage == StageType.ATTACK )
            stage = StageType.FORTIFY;
        else if ( stage == StageType.FORTIFY )
            stage = StageType.END;
    }
}
