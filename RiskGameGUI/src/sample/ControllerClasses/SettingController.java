package sample.ControllerClasses;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class will be used to control settings user interface
 * By using checkbox, music can be muted
 * By using slider, volume can be adjusted
 * @author Emin Adem Buran
 */
public class SettingController {

    MediaPlayer music;
    @FXML
    CheckBox muteBox;
    @FXML
    Slider volumeLevel;

    /**
     * This method listens for the actions on the back button
     * When the button is on action new scene is created with
     * MainMenu fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void backButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController mmc = loader.getController();
        mmc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.setMaximized(true);
        window.show();
    }

    /**
     * This method listens for the actions on the mute checkbox
     * When the box is checked music is muted, else not muted
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void musicMute( ActionEvent e)
    {
        if( muteBox.isSelected() )
        {
            music.setMute(true);
        }
        else {
            music.setMute(false);
        }
    }


    /**
     * This method initializes required adjustments for the settings controller class
     * It adjusts the check boxes and slider according to the media player
     * It constructs invalidation listener for the slider and adjust volume
     * inside this listener method
     * This method is public as it is used by other controller classes and fxml file
     * @param m
     */
    public void initialize(MediaPlayer m)
    {
        music = m;
        if( music.isMute() )
            muteBox.setSelected(true);
        else {
            muteBox.setSelected(false);
        }
        volumeLevel.setValue(music.getVolume() * 100);
        volumeLevel.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                music.setVolume(volumeLevel.getValue() / 100);
            }
        });
    }
}
