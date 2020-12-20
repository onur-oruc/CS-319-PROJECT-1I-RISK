package sample.Entities;

import jdk.swing.interop.SwingInterOpUtils;
import sample.Enums.TroopCardType;
import sample.Mission.Mission;

import java.util.ArrayList;

public class Player extends Challenger {
    private boolean [] allies;
    final int STARTING_MONEY = 100;

    public Player( String name, int id, String color, String url){
        super( name, id,color,url);
        this.addTroopCard(TroopCardType.ARTILLERY);
        this.addTroopCard(TroopCardType.ARTILLERY);
        this.addTroopCard(TroopCardType.MERCENARY);
        this.addTroopCard(TroopCardType.MARINES);
        this.addTroopCard(TroopCardType.INFANTRY);
        allies = new boolean [ 5];
        for( int i = 0; i < 5; i++)
            allies[i] = false;
        this.setMoney(STARTING_MONEY);
    } //

    public Player (int playerID, int numPlayers, String name, String color, String url) {
        super(name,playerID,color,url);

        this.setMoney(STARTING_MONEY);
        this.setTurn(false);   // not yet his turn
        this.setTroopCards(null);
        this.setRegionCount(0);
        this.setEliminated(false);

        allies = new boolean[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            allies[i] = false;
        }
    }

    public void updateAlly( boolean[] allies ) {
        for( int i = 0; i < allies.length; i++){
            this.allies[i] = allies[i];
        }
    }

    public void printPlayer() {
        System.out.println();
        System.out.println("playerID: " + getId());
        System.out.println("name: " + getName());
        System.out.println("money: " + getMoney());
        //System.out.println("turn: " + isTurn);
        System.out.println("regionCount: " + getRegionCount());
        // System.out.println("isEliminated: " + isEliminated);


        System.out.println();

        System.out.print("regionsByID: ");
        for (Integer integer : this.getRegionIds()) {
            System.out.print(integer + ", ");
        }
        System.out.println();

        System.out.print("allies: ");
        for (boolean ally : allies) {
            System.out.print(ally + ", ");
        }
        System.out.println();
    }
}