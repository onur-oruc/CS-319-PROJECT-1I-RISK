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

public class SettingController {

    MediaPlayer music;
    @FXML
    CheckBox muteBox;
    @FXML
    Slider volumeLevel;

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
