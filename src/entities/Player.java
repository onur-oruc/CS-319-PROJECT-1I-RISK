package entities;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;

public class Player extends Challenger{
    private boolean [] allies;
    final int STARTING_MONEY = 0;

    public Player( String name, int id, int [] regionIds){
        super( name, id);
        allies = new boolean [ 5];
        for( int i = 0; i < 5; i++)
            allies[i] = false;
    }
    public Player (int playerID, int numPlayers, String name) {
        super(name,playerID);

        money = STARTING_MONEY;
        isTurn = false;   // not yet his turn
        troopCards = null;
        regionCount = 0;
        isEliminated = false;

        allies = new boolean[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            allies[i] = false;
        }
    }

    public void updateAlly( boolean [] alies ){
        for( int i = 0; i < alies.length; i++){
            this.allies[i] = alies[i];
        }
    }

    public void printPlayer() {
        System.out.println();
        System.out.println("playerID: " + id);
        System.out.println("name: " + name);
        System.out.println("money: " + money);
        System.out.println("turn: " + isTurn);
        System.out.println("regionCount: " + regionCount);
        System.out.println("isEliminated: " + isEliminated);


        System.out.println();

        System.out.print("regionsByID: ");
        for (Integer integer : regionIds) {
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
