import java.util.ArrayList;

public class Player {

    // properties
    private int playerID;
    private String name;
    private int money;  // gold
    private boolean turn;   // gereksiz gibi
    private int[] troopCards;
    private ArrayList<Integer> regionsByID;
    private int regionCount;    // skor gibi gösterilmeyecekse gereksiz gibi, array listin size da alınabilir
    private boolean[] allies;   // boolean on associated index will be true if other player is an ally
    private boolean isEliminated;   // becomes true if player is out of regions

    final int STARTING_MONEY = 0;

    // constructors
    public Player( int playerID, int numPlayers, String name) {

        this.playerID = playerID;
        this.name = name;
        money = STARTING_MONEY;
        turn = false;   // not yet his turn
        troopCards = new int[] {0,0,0,0};
        regionsByID = new ArrayList<>();
        regionCount = 0;
        isEliminated = false;

        allies = new boolean[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            allies[i] = false;
        }
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

    public boolean[] getAllies() { return allies; }

    public void setMoney(int money) {
        this.money = money;
    }

    // hmm
    public void takeTurn() {
        turn = true;
    }

    public boolean addRegion(int newRegionID) {

        if ( !regionsByID.contains(newRegionID)) {
            regionsByID.add(newRegionID);
            regionCount++;
            return true;
        }
        return false;
    }

}
