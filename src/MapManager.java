public class MapManager {
    // properties
    private final int regionCount = 42;
    private final int continentCount = 6;
    private Continent[] continentArr;
    private Region[] regionArr;
    private Player[] playerArr;
    private int playerCount;

    // constructor
    public MapManager(Player[] playerArr){
        this.playerCount = playerCount;
        this.playerArr = playerArr;

        regionArr = new Region[regionCount]; //init region array
        for(int i = 0; i < regionCount; i++){ //create regions and add to region array
            Region r = new Region(i);
            regionArr[i] = r;
        }

        continentArr = new Continent[continentCount];
        for(int i = 0; i < continentCount; i++){ //create regions and add to region array
            Continent c = new Continent(i);
            continentArr[i] = c;
        }
    }
    // methods
    public void initMap(){
        int regionPerPlayer = regionCount/playerCount;
        for(int i = 0; i < regionPerPlayer; i++){

        }

    }
}
