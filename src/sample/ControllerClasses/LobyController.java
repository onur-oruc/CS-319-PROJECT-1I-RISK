package sample.ControllerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;

public class LobyController {

    ObservableList<String> playerNumber =
            FXCollections.observableArrayList(
                    "2",
                    "3",
                    "4"
            );
    @FXML
    private ComboBox playerCount;

    private ComboBox combo;
    private ComboBox combo2;
    private ComboBox combo3;
    private ComboBox combo4;

    @FXML
    private StackPane stackPane1;
    @FXML
    private StackPane stackPane2;
    @FXML
    private StackPane stackPane3;
    @FXML
    private StackPane stackPane4;
    @FXML
    private TextField playerName1;
    @FXML
    private TextField playerName2;
    @FXML
    private TextField playerName3;
    @FXML
    private TextField playerName4;

    @FXML
    AnchorPane anc;

    //Stage window;

    /*public void onAnchorPane(MouseEvent event) throws IOException
    {
        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
    }*/



    public void onClickedBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void startGameClicked(ActionEvent event) throws IOException {

        /*Parent root = FXMLLoader.load(getClass().getResource("GameMap.fxml"));*/

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameMap.fxml"));
        Parent root = loader.load();
        GameMapController gmc = loader.getController();
        gmc.initialize("adeeem");
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        //window.setUserData(new String("adeeem"));
        window.setScene(ng);
        window.show();

    }

    public void initialize() throws FileNotFoundException {


        playerCount.setItems(playerNumber);
        playerCount.setValue("2");


        ObservableList<Image> images = fetchImages();
        combo = createComboBox(images);
        stackPane1.getChildren().addAll(combo);

        combo2 = createComboBox(images);
        stackPane2.getChildren().addAll(combo2);

        combo3 = createComboBox(images);
        stackPane3.getChildren().addAll(combo3);

        combo4 = createComboBox(images);
        stackPane4.getChildren().addAll(combo4);

        /*acb2 = createComboBox(avatars);
        acb3 = createComboBox(avatars);
        acb4 = createComboBox(avatars);*/
    }

    public void comboAction(ActionEvent event) {


        if(playerCount.getValue().equals("2"))
        {
            stackPane1.setVisible(true);
            playerName1.setVisible(true);
            stackPane2.setVisible(true);
            playerName2.setVisible(true);
            stackPane3.setVisible(false);
            playerName3.setVisible(false);
            stackPane4.setVisible(false);
            playerName4.setVisible(false);
        }
        if(playerCount.getValue().equals("3"))
        {
            stackPane1.setVisible(true);
            playerName1.setVisible(true);
            stackPane2.setVisible(true);
            playerName2.setVisible(true);
            stackPane3.setVisible(true);
            playerName3.setVisible(true);
            stackPane4.setVisible(false);
            playerName4.setVisible(false);
        }
        if(playerCount.getValue().equals("4"))
        {
            stackPane1.setVisible(true);
            playerName1.setVisible(true);
            stackPane2.setVisible(true);
            playerName2.setVisible(true);
            stackPane3.setVisible(true);
            playerName3.setVisible(true);
            stackPane4.setVisible(true);
            playerName4.setVisible(true);
        }

    }

    private ObservableList<Image> fetchImages() throws FileNotFoundException {
        final ObservableList<Image> data = FXCollections.observableArrayList();
        // icon license: CC Attribution-Noncommercial-Share Alike 3.0
        // iconset homepage: http://vincentburton.deviantart.com/art/Iconos-Diaguitas-216196385
        String[] images = new String[]{"https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/andy-warhol-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/batman-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/robot-03-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/robot-02-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/traditional-african-man-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/donald-trump-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/traditiona-japanese-man-icon.png",
                "https://icons.iconarchive.com/icons/diversity-avatars/avatars/72/native-man-icon.png"};
        for (int i = 0; i < 8; i++) {

            data.add(
                    new Image( images[i]

                    )
            );
        }
        return data;
    }

    private ComboBox<Image> createComboBox(  ObservableList<Image> data) {
        ComboBox<Image> combo = new ComboBox<>();
        combo.getItems().addAll(data);
        combo.setButtonCell(new ImageListCell());
        combo.setCellFactory(listView -> new ImageListCell());
        combo.getSelectionModel().select(0);
        return combo;
    }


    class ImageListCell extends ListCell<Image> {
        private final ImageView view;

        ImageListCell() {
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            view = new ImageView();
        }

        @Override protected void updateItem(Image item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(null);
            } else {
                view.setImage(item);
                setGraphic(view);
            }
        }

    }
}
