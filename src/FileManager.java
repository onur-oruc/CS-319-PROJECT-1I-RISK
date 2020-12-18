import entities.Player;
import entities.Region;
import enums.SeasonType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class FileManager {
    // saving current game in to file
    GameManager fetchSavedGame( String fileName) {
        FileInputStream fos;
        ObjectInputStream oos;
        Object a = new Object();
        try
        {
            fos = new FileInputStream( "saved games\\" + fileName );
            oos = new ObjectInputStream(fos);
            a = oos.readObject();
            oos.close();
            fos.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return (GameManager)(a);
    }
    Region[]  fetchRegions( String fileName) {
        FileInputStream fos;
        ObjectInputStream oos;
        Object a = new Object();
        try
        {
            fos = new FileInputStream( "saved games\\" + fileName );
            oos = new ObjectInputStream(fos);
            a = oos.readObject();
            oos.close();
            fos.close();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        catch(Exception exp)
        {
            exp.printStackTrace();
        }
        return (Region[] )(a);
    }
    boolean writeGame( String fileName, GameManager gameToBeSaved) {
        try
        {
            FileOutputStream fos = new FileOutputStream( "saved games\\" + fileName+ ".txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject( gameToBeSaved );
            oos.close();
            fos.close();
        }
        catch( IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
