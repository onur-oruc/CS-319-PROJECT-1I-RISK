package sample.ControllerClasses;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sample.Managers.FileManager;
import sample.Managers.GameManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class will be used to control load game user interface
 * @author Melike Demirci
 */
public class LoadGameController {

    @FXML
    ListView<String> gamesListView;

    MediaPlayer mediaPlayer;

    ObservableList games = FXCollections.observableArrayList();

    /**
     * This method initializes required adjustments for the load game controller class
     * It assigns the media player to the passed parameter
     * It calls loadData() method
     * This method is public as it is used by other controller classes and fxml file
     * @param mediaPlayer
     */
    public void initialize(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
        int numberOfSavedFiles = 0;
        File savedFiles = new File("src/savedGames");
        String[] nameOfSavedFiles  = savedFiles.list();
        for (File file : savedFiles.listFiles())
        {
            if (file.isFile())
            {
                numberOfSavedFiles++;
            }
        }
        ObservableList names = FXCollections.observableArrayList();

        for( int i = 0 ; i < numberOfSavedFiles; i++){
            names.add(nameOfSavedFiles[i].substring(0,nameOfSavedFiles[i].length()-4));
        }
        gamesListView.setItems(names);

    }

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
        mmc.initialize( mediaPlayer);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    /**
     * This method listens for the actions on the start game button
     * When the button is on action new scene is created with GameMap fxml file
     * According to the selection in the list view new game manager is created
     * by using the saved game data
     * This method is public as it is connected to fxml file
     * @param event
     */
    public void onClickedStartGame(ActionEvent event) throws IOException {
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameMap.fxml"));
        Parent root = loader.load();
        GameMapController gmc = loader.getController();
        FileManager fm = new FileManager();
        String gameName = gamesListView.getSelectionModel().getSelectedItem();
        GameManager gm = fm.fetchSavedGame(gameName);
        gmc.initialize( gm, mediaPlayer);

        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }
}
