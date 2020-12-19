package sample.ControllerClasses;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PauseMenuController {
    @FXML
    Pane rulesPane;

    public void onClickedSaveGame(ActionEvent event) throws IOException {

    }
    public void onClickedSettings(ActionEvent event) throws IOException {

    }
    public void onClickedRules(ActionEvent event) throws IOException {
        rulesPane.setVisible(true);
    }
    public void onClickedResume(ActionEvent event) throws IOException {

    }
    public void onClickedCloseSettings(ActionEvent event) throws IOException {
        rulesPane.setVisible(false);
    }
}
