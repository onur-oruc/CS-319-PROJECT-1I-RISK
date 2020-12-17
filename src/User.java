public abstract class User {
    int money;
    TroopCard [] troopCard;
    boolean isTurn;
    String name;
    int id;
    int regionCount;
    boolean isEliminated;
    int [] regionIds;
    Mission mission;
    User( String name, int id, int [] regionIds){
        this.name = name;
        this.id = id;
        this.regionIds = new int [ regionIds.length];
        for( int i = 0; i < regionIds.length; i++){
            this.regionIds[i] = regionIds[i];
        }
    }

    public void updateRegionCount( int regionNum){
        regionCount = regionNum;
    }
    public void updateMoney( int money ){
        this.money = money;
    }
    public void updateRegions( int[] regionIds ) {
        this.regionIds = new int [regionIds.length];
        for( int i = 0; i < regionIds.length; i++){
            this.regionIds[i] = regionIds[i];
        }

    }

    public void combineCards(){

    }

    public int calculateBonusTroop( Continent[] continents ){

    }
}