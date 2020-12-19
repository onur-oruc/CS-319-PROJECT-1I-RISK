package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainMenuController {


    public void onClickedNewGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Loby.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void onClickedOptions(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void onClickedRules(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Rules.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void onClickedCredits(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Credits.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void onClickedLoadGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoadGame.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void onClickedExit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Exit Game");
        alert.setContentText("Do you want to exit game?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }




}
