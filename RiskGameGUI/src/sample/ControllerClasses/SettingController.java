package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingController {

    public void backButtonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.setMaximized(true);
        window.show();
    }
}
