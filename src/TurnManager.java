import entities.Player;
import entities.Region;
import enums.MotivationLevel;
import enums.SeasonType;
import enums.StageType;

import java.util.Scanner;

public class TurnManager {

    // properties
    // needs continents array
    Player player;
    Region[] regions;
    int turnCount;
    SeasonType season;
    boolean plague, weather;
    boolean getsCard;

    StageType stage;
    int additionalTroops;

    final int PRICE_OF_MERCENARY = 10;
    final int PRICE_OF_ENTERTAINMENT = 5;

    // constructor
    public TurnManager( Player player, Region[] regions, boolean plague, boolean weather,
                        SeasonType season, int turnCount ) {
        this.player = player;
        this.regions = regions;
        this.plague = plague;
        this.weather = weather;
        this.season = season;
        this.turnCount = turnCount;

        additionalTroops = Math.max(player.getRegionCount() / 3, 3);

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
                    regions[scan.nextInt()].setTroopMotivation(/*true*/MotivationLevel.NORMALMOTIVATED);
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
            selectedRegion = regions[scan.nextInt()];

            troopsToDraft = Math.min(scan.nextInt(), additionalTroops);

            selectedRegion.setNumTroops( selectedRegion.getNumTroops() + troopsToDraft);

            additionalTroops = additionalTroops - troopsToDraft;
        }

        stage = StageType.ATTACK;

    }

    public void attack() {

    }


    public void oneTimeAttack() {

    }





}
