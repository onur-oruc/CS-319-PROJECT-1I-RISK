import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Continent {
    // properties
    private int continentId;
    private int regionCount;
    private int[] regionIdArr;
    private int bonusTroop;

    // constructor
    public Continent(int continentId){
        this.continentId = continentId;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("data/continent.txt"));
            for(int i=0;i<continentId;i++){
                br.readLine();
            }
            Scanner scan = new Scanner(br.readLine());
            this.bonusTroop = scan.nextInt();
            this.regionCount = scan.nextInt();


            regionIdArr = new int[regionCount];
            for(int i=0;i<regionCount;i++){
                regionIdArr[i] = scan.nextInt();
            }
            scan.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // methods
    public int getContinentId(){ return this.continentId; }
    public int getRegionCount(){ return this.regionCount; }
    public int[] getRegions(){ return this.regionIdArr; }
    public int getBonusTroop(){ return this.bonusTroop; }

}
