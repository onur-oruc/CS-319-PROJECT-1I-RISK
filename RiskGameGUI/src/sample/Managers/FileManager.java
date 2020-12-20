package sample.Managers;

import java.io.*;

public class FileManager {

    public void writeGame( String fileName, GameManager gm)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream( "src/savedGames/" + fileName + ".txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject( gm);
            oos.close();
            fos.close();
        }
        catch( IOException e)
        {
            e.printStackTrace();
        }
    }


    public GameManager fetchSavedGame( String fileName)
    {
        FileInputStream fos;
        ObjectInputStream oos;
        Object a = new Object();
        try
        {
            fos = new FileInputStream( "src/savedGames/" + fileName + ".txt"  );
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
        return (GameManager) (a);

    }

}
