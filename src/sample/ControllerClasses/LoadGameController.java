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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LoadGameController {

    @FXML
    Button button;
    @FXML
    private Pane gamePane;
    @FXML
    ListView<String> gamesListView;

    ObservableList games = FXCollections.observableArrayList();

    public void initialize()
    {
        loadData();
    }

    public void backButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void startGameClicked(ActionEvent event) throws IOException {
        String gameName;
        gameName = gamesListView.getSelectionModel().getSelectedItem();
        System.out.println(gameName);
        Parent root = FXMLLoader.load(getClass().getResource("GameMap.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    private void loadData(){
        games.removeAll(games);
        games.addAll("Game1", "Game2", "Game3", "Game4", "Game5", "Game6", "Game7");
        gamesListView.getItems().addAll(games);

    }
}
