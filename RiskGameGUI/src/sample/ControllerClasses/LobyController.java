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
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import sample.Entities.*;
import sample.Managers.*;
import java.io.*;


public class LobyController {

    MediaPlayer music;
    ObservableList<String> playerNumber =
            FXCollections.observableArrayList(
                    "2",
                    "3",
                    "4"
            );
    ObservableList<String> missions =
            FXCollections.observableArrayList(
                    "Global Domination",
                    "Secret Mission"
            );
    @FXML
    ComboBox playerCount, missionChoice;

    private ComboBox combo;
    private ComboBox combo2;
    private ComboBox combo3;
    private ComboBox combo4;

    @FXML
    CheckBox plagueCheck, weatherCheck;

    @FXML
    Rectangle color1, color2, color3, color4;

    @FXML
    private StackPane stackPane1,stackPane2,stackPane3,stackPane4;
    @FXML
    private TextField playerName1, playerName2, playerName3, playerName4;

    public void onClickedBack(ActionEvent event) throws IOException {
        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenu.fxml"));
        Parent root = loader.load();
        MainMenuController mmc = loader.getController();
        mmc.initialize( music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void startGameClicked(ActionEvent event) throws Exception {

        /*Parent root = FXMLLoader.load(getClass().getResource("GameMap.fxml"));*/

        FXMLLoader  loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameMap.fxml"));
        Parent root = loader.load();
        GameMapController gmc = loader.getController();
        String[] images = new String[4];

        //get the properties of the game
        GameManager gm = null;
        String numPlayerString = (String)playerCount.getValue();
        int numPlayer = Integer.parseInt(numPlayerString);
        boolean plague = false;
        boolean weather = false;
        boolean secretMission = false;

        if( plagueCheck.isSelected())
            plague = true;
        if( weatherCheck.isSelected())
            weather = true;
        if( missionChoice.getValue().equals("Secret Mission"))
            secretMission = true;


        if( numPlayer == 2)
        {
            //System.out.println("girdi1");
            Player p0 = new Player( playerName1.getText(), 0, "#34abeb", ((Image)combo.getValue()).getUrl());
            Player p1 = new Player( playerName2.getText(), 1, "#ee940c", ((Image)combo2.getValue()).getUrl());
            Player[] players = new Player[2];
            players[0]=p0;
            players[1]=p1;
            gm = new GameManager( 2, players, plague,weather,secretMission);
        }
        if( numPlayer == 3)
        {
            //System.out.println("girdi2");
            Player p0 = new Player( playerName1.getText(), 0, "#34abeb", ((Image)combo.getValue()).getUrl());
            Player p1 = new Player( playerName2.getText(), 1, "#ee940c", ((Image)combo2.getValue()).getUrl());
            Player p2 = new Player( playerName3.getText(), 2, "#cf1fff", ((Image)combo3.getValue()).getUrl());
            Player[] players = new Player[3];
            players[0]=p0;
            players[1]=p1;
            players[2]=p2;
            gm = new GameManager( 3, players, plague,weather,secretMission);
        }
        else if( numPlayer == 4)
        {
            //System.out.println("girdi3");
            Player p0 = new Player( playerName1.getText(), 0, "#34abeb", ((Image)combo.getValue()).getUrl());
            Player p1 = new Player( playerName2.getText(), 1, "#ee940c", ((Image)combo2.getValue()).getUrl());
            Player p2 = new Player( playerName3.getText(), 2, "#cf1fff", ((Image)combo3.getValue()).getUrl());
            Player p3 = new Player( playerName4.getText(), 3, "#1bc64c", ((Image)combo4.getValue()).getUrl());
            Player[] players = new Player[4];
            players[0]=p0;
            players[1]=p1;
            players[2]=p2;
            players[3]=p3;
            gm = new GameManager( 4, players, plague,weather,secretMission);
        }

        gm.startGame();


        //System.out.println(images[0]);
        gmc.initialize(gm,music);
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void initialize( MediaPlayer m) throws FileNotFoundException {

        music = m;

        playerCount.setItems(playerNumber);
        playerCount.setValue("2");

        missionChoice.setItems(missions);
        missionChoice.setValue("Global Domination");


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
            color1.setVisible(true);
            color2.setVisible(true);
            color3.setVisible(false);
            color4.setVisible(false);
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
            color1.setVisible(true);
            color2.setVisible(true);
            color3.setVisible(true);
            color4.setVisible(false);
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
            color1.setVisible(true);
            color2.setVisible(true);
            color3.setVisible(true);
            color4.setVisible(true);
        }

    }

    private ObservableList<Image> fetchImages() throws FileNotFoundException {
        final ObservableList<Image> data = FXCollections.observableArrayList();
        // icon license: CC Attribution-Noncommercial-Share Alike 3.0
        // iconset homepage: http://vincentburton.deviantart.com/art/Iconos-Diaguitas-216196385
        String[] images = new String[]{"andy-warhol-icon.png",
                "batman-icon.png",
                "robot-03-icon.png",
                "robot-02-icon.png",
                "traditional-african-man-icon.png",
                "donald-trump-icon.png",
                "traditiona-japanese-man-icon.png",
                "native-man-icon.png" };

        for (int i = 0; i < 8; i++)
        {
            File file = new File("src/sample/Images/" + images[i]);
            data.add(new Image( file.toURI().toString()));
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
