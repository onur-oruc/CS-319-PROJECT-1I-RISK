package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class will be used to control rules user interface
 * @author Emin Adem Buran
 */
public class RulesController {
    MediaPlayer music;
    @FXML
    Button button;

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
     * This method initializes rules controller class
     * This method is public as it is used by other controller classes and fxml file
     * @param m
     */
    public void initialize(MediaPlayer m)
    {
        music = m;
    }





}
