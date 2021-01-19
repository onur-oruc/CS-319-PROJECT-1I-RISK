package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.ControllerClasses.CreditsController;
import sample.ControllerClasses.MainMenuController;
import sample.Entities.Continent;
import sample.Entities.Region;
import sample.Managers.GameManager;

import java.io.File;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        String musicFile = "src/sample/Images/music.mp3";
        File file = new File(musicFile);
        Media sound = new Media(file.toURI().toString());
        MediaPlayer music = new MediaPlayer(sound);
        music.play();
        primaryStage.setTitle("RiskGame");
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ControllerClasses/MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController mmc = loader.getController();
        mmc.initialize( music);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.initStyle( StageStyle.UNDECORATED);
        primaryStage.show();
    }
    public static void main( String[] args)
    {
        launch(args);
    }

}
