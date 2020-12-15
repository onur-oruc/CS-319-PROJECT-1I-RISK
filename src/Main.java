import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int regionId = 3;
        int locX = 0;
        int locY = 0;
        int neighborCount = 0;
        int[] neighbors = {};
        BufferedReader rdr;

        try {
            rdr = new BufferedReader(new FileReader("data/region.txt"));
            for(int i = 0; i < regionId; i++){
                rdr.readLine();
            }
            Scanner scan = new Scanner(rdr.readLine());
            locX = scan.nextInt();
            locY = scan.nextInt();

            neighborCount = scan.nextInt();

            neighbors = new int[neighborCount];
            for(int i = 0; i < neighborCount; i++){
                neighbors[i] = scan.nextInt();
            }

            scan.close();
            rdr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(locX);
        System.out.println(locY);
        for(int i = 0; i < neighborCount; i++){
            System.out.println(neighbors[i]);
        }
    }

}