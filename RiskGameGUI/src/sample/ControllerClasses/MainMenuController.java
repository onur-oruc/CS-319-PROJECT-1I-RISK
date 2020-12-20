package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * This class will be used to control main menu user interface
 * There are six different buttons on the main menu
 * These are NewGame, Load Game, Rules, Options, Credits, Exit buttons
 * When these buttons are on clicked, a new scene is initialized
 * and displayed on the screen
 * @author Emin Adem Buran
 */
public class MainMenuController {
    MediaPlayer music;

    /**
     * This method listens for the actions on the new game button
     * When the button is on action new scene is created with
     * Loby fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedNewGame(ActionEvent event) throws IOException {

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Loby.fxml"));
        Parent root = loader.load();
        LobyController lb = loader.getController();
        lb.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }


    /**
     * This method listens for the actions on the options button
     * When the button is on action new scene is created with
     * Settings fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedOptions(ActionEvent event) throws IOException {

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Settings.fxml"));
        Parent root = loader.load();
        SettingController sc = loader.getController();
        sc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }


    /**
     * This method listens for the actions on the load game button
     * When the button is on action new scene is created with
     * LoadGame fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedLoadGame(ActionEvent event) throws IOException {

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoadGame.fxml"));
        Parent root = loader.load();
        LoadGameController cc = loader.getController();
        cc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }


    /**
     * This method listens for the actions on the rules button
     * When the button is on action new scene is created with
     * Rules fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedRules(ActionEvent event) throws IOException {

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Rules.fxml"));
        Parent root = loader.load();
        RulesController rc = loader.getController();
        rc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }


    /**
     * This method listens for the actions on the credits button
     * When the button is on action new scene is created with
     * Credits fxml file and displayed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedCredits(ActionEvent event) throws IOException {

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Credits.fxml"));
        Parent root = loader.load();
        CreditsController cc = loader.getController();
        cc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    /**
     * This method listens for the actions on the exit button
     * When the button is on action alert box is created
     * in order to confirm the exit decision
     * If the decision confirmed, window is closed
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedExit(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Exit Game");
        alert.setContentText("Do you want to exit game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            music.stop();
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }


    /**
     * This method initializes required adjustments for the main menu class
     * It assigns the media player to the passed parameter
     * This method is public as it is used by other controller classes and fxml file
     * @param m
     */
    public void initialize( MediaPlayer m )
    {
        music = m;
    }

}
