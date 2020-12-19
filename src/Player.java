import java.util.ArrayList;

public class Player {

    // properties
    private int playerID;
    private String name;
    private int money;
    private boolean isTurn;
    private int regionCount;
    private int[] troopCards;
    private ArrayList<Integer> regionsByID;
    private boolean[] allies;
    private boolean isEliminated;
    private Continent[] continents;
    // constructors
    public Player(int playerID) {
        this.playerID = playerID;

    }

    // methods
    public ArrayList<Integer> getRegionsByID() {
        return regionsByID;
    }

    public int[] getTroopCards() {
        return troopCards;
    }

    public int getMoney() {
        return money;
    }

    public int getRegionCount() {
        return regionCount;
    }


    public void setMoney(int money) {
        this.money = money;
    }

}
