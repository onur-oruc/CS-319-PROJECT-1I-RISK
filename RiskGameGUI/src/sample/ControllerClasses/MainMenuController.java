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

public class MainMenuController {
    MediaPlayer music;

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

    public void initialize( MediaPlayer m )
    {
        music = m;
    }




}
