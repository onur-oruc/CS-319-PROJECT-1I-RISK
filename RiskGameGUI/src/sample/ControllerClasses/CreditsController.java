package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class will be used to control load game user interface
 * @author Emin Adem Buran
 */
public class CreditsController {

    MediaPlayer music;
    /**
     * This method listens for the actions on the back button
     * When the button is on action new scene is created with
     * MainMenu fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void backButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController mmc = loader.getController();
        mmc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    /**
     * This method initializes required adjustments for the main menu class
     * It assigns the media player to the passed parameter
     * This method is public as it is used by other controller classes and fxml file
     * @param m
     */
    public void initialize(MediaPlayer m)
    {
        music = m;
    }
}
