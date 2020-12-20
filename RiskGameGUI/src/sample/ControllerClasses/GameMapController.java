package sample.ControllerClasses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import sample.Entities.Player;
import sample.Entities.Region;
import sample.Enums.ClimateType;
import sample.Enums.MotivationLevel;
import sample.Enums.TroopCardType;
import sample.Managers.FileManager;
import sample.Managers.GameManager;
import sample.Managers.TurnManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class GameMapController {
    GameManager gm;
    MediaPlayer music;
    String s;
    Region regionToPlace, regionToAttackWith, regionToAttack, regionToGetTroop;
    int troopNumToFortify;
    @FXML
    AnchorPane gamerPanel, dicePanel, nextTurnPanel;
    @FXML
    AnchorPane buyPanel, pausePanel, savePanel, settingsPanel;
    @FXML
    Label instructionLabel, attackLabel;
    @FXML
    Label stageLabel;
    @FXML
    ImageView troopImage;
    @FXML
    Label troopLabel;
    @FXML
    ChoiceBox troopNo;
    @FXML
    Button moveCom;
    @FXML
    Button done;
    @FXML
    Button eventButton, attackButton1, attackButton2, attackButton3, nextStage, exitTurn, hire, combineButton, pauseButton;
    @FXML
    Button attackExit, buyExit, distribute, climateButton, motivationButton;
    @FXML
    ImageView playerImg1, playerImg2,playerImg3, playerImg4, imageTurn;
    @FXML
    Label infoLabel1, infoLabel2,infoLabel3,infoLabel4,playerMoney, troopCard1, troopCard2, troopCard3, troopCard4,missionLabel;
    @FXML
    Rectangle info1, info2, info3, info4;
    @FXML
    ImageView turn1, turn2, turn3, turn4, elim1, elim2, elim3, elim4;
    @FXML
    ComboBox chooseTroopNumber, troopCardNum1, troopCardNum2, troopCardNum3, troopCardNum4;
    @FXML
    TextField saveGameName;

    public void distributeClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene s = window.getScene();
        instructionLabel.setText(gm.getInstruction());
        stageLabel.setText(gm.getStageString());

        showMap(s);
        distribute.setVisible(false);
        Player p = gm.getPlayers()[gm.getWhoseTurn()];
        motivationButton.setVisible(true);
        climateButton.setVisible(true);
        pauseButton.setVisible(true);
        TurnManager tm = gm.getTm();
        playerImg1.setDisable(true);
        playerImg2.setDisable(true);
        playerImg3.setDisable(true);
        playerImg4.setDisable(true);
        Region[] allRegions = gm.getRegions();

        if(stageLabel.getText().equals("BUY STAGE"))
        {
            nextStage.setVisible(true);
            eventButton.setVisible(true);
            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            for( int i = 0; i < allRegions.length; i++)
            {
                SVGPath svg = (SVGPath) s.lookup("#svg" + i);
                svg.setDisable(true);
                if( p.hasRegion(i))
                    svg.setDisable(false);
            }

        }
        else if(stageLabel.getText().equals("DRAFT STAGE"))
        {
            nextStage.setVisible(true);
            if( tm.getAdditionalTroops() != 0)
                nextStage.setDisable(true);
            moveCom.setVisible(true);
            for( int i = 0; i < allRegions.length; i++)
            {
                SVGPath svg = (SVGPath) s.lookup("#svg" + i);
                svg.setDisable(true);
                if( p.hasRegion(i))
                    svg.setDisable(false);
            }
        }
        else if(stageLabel.getText().equals("ATTACK STAGE"))
        {
            nextStage.setVisible(true);
            for( int i = 0; i < allRegions.length; i++)
            {
                SVGPath svg = (SVGPath) s.lookup("#svg" + i);
                svg.setDisable(true);
                if( p.hasRegion(i))
                    svg.setDisable(false);
            }
        }
        else if(stageLabel.getText().equals("FORTIFY STAGE"))
        {
            nextStage.setVisible(true);
            for( int i = 0; i < allRegions.length; i++)
            {
                SVGPath svg = (SVGPath) s.lookup("#svg" + i);
                svg.setDisable(true);
                if( p.hasRegion(i))
                    svg.setDisable(false);
            }
        }

        int turn = gm.getWhoseTurn();
        if( turn == 0)
        {
            playerImg1.setDisable(false);
        }
        if( turn == 1)
        {
            playerImg2.setDisable(false);
        }
        if( turn == 2)
        {
            playerImg3.setDisable(false);
        }
        if( turn == 3)
        {
            playerImg4.setDisable(false);
        }
    }

    public void showMap( Scene sv)
    {
        Region[] allregion = gm.getRegions();
        Scene sc = sv;

        for( int i = 0 ; i <allregion.length; i++)
        {
            if( allregion[i].hasCommander())
            {
                ImageView comImage = (ImageView) sc.lookup("#com" + i);
                comImage.setVisible(true);
            }
            if( allregion[i].hasGoldMine())
            {
                ImageView comImage = (ImageView) sc.lookup("#gold" + i);
                comImage.setVisible(true);
            }
            Label lbl = (Label) sc.lookup("#lbl" + i);
            String s = "" +allregion[i].getNumTroops();
            lbl.setText(s);
            lbl.setVisible(true);

            SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
            Player p = gm.getPlayers()[allregion[i].getOwnerID()];
            svg.setFill(Paint.valueOf( p.getColor()));
        }
    }

    public void onRegionClicked( MouseEvent event) throws IOException {

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene sc = window.getScene();

        if( instructionLabel.getText().equals("Select a region to organize event"))
        {
            gm.setInstruction("Click on your avatar to buy or organize event");
            instructionLabel.setText("Click on your avatar to buy or organize event");
            nextStage.setVisible(true);
            Player p = gm.getPlayers()[gm.getWhoseTurn()];
            Region[] all = gm.getRegions();
            TurnManager tm = gm.getTm();
            String svgname = ((SVGPath)event.getSource()).getId();
            Region r = all[Integer.parseInt(svgname.substring(3))];
            System.out.println(r.getRegionID());
            tm.organizeEntertainment(r);
            for( int i = 0; i < all.length; i++)
            {
                SVGPath region = (SVGPath) sc.lookup("#svg" + i);
                region.setDisable(false);
            }
            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            eventButton.setVisible(true);
            pauseButton.setVisible(true);
            /*playerImg1.setDisable(false);
            playerImg2.setDisable(false);
            playerImg3.setDisable(false);
            playerImg4.setDisable(false);*/
        }
        else if( instructionLabel.getText().equals("Select a region to place commander"))
        {
            Region[] all = gm.getRegions();
            Player[] players = gm.getPlayers();

            Region r = all[Integer.parseInt(((SVGPath)event.getSource()).getId().substring(3))];
            Player p = players[gm.getWhoseTurn()];
            int previousLocation = p.getCommanderLocation();
            ImageView com = (ImageView) sc.lookup("#com" + previousLocation);
            com.setVisible(false);

            TurnManager tm = gm.getTm();
            tm.moveCommander(r);

            com = (ImageView) sc.lookup("#com" + r.getRegionID());
            com.setVisible(true);


            gm.setInstruction("Select a region you want to place troops");
            instructionLabel.setText("Select a region you want to place troops");
            nextStage.setVisible(true);
            moveCom.setVisible(true);
            pauseButton.setVisible(true);
        }

        else if( instructionLabel.getText().equals("Select a region you want to place troops"))
        {

            Region[] all = gm.getRegions();
            TurnManager tm = gm.getTm();
            int totalTroop = tm.getAdditionalTroops();
            troopLabel.setText(""+totalTroop);
            ObservableList<String> numbers = FXCollections.observableArrayList();
            numbers.add("0");
            String svgname = ((SVGPath)event.getSource()).getId();
            regionToPlace = all[Integer.parseInt(svgname.substring(3))];

            for( int i = 1; i <= totalTroop; i++ ){
                String numberr = "" + i;
                numbers.add(numberr);
            }

            troopNo.setItems(numbers);
            troopNo.setValue("0");

            instructionLabel.setText("Select the number of troops you want to place");
            gm.setInstruction("Select the number of troops you want to place");
            troopImage.setVisible(true);
            troopLabel.setVisible(true);
            troopNo.setVisible(true);
            done.setVisible(true);
            nextStage.setVisible(false);
            moveCom.setVisible(false);
            pauseButton.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to attack from"))
        {

            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            Region[] all = gm.getRegions();
            String svgname = ((SVGPath)event.getSource()).getId();
            regionToAttackWith = all[Integer.parseInt(svgname.substring(3))];
            ArrayList<Integer> redRegions = regionToAttackWith.getEnemyRegions(p.getRegionIds());

            for( int i = 0; i < all.length;i++)
            {
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                svg.setDisable(true);
            }
            for( int i = 0; i < redRegions.size(); i++)
            {
                SVGPath svg = (SVGPath) sc.lookup("#svg" + redRegions.get(i));
                svg.setFill(Paint.valueOf("#e00434"));
                svg.setDisable(false);
            }

            instructionLabel.setText("Select a region you want to attack");
            gm.setInstruction("Select a region you want to attack");
            nextStage.setVisible(false);
            pauseButton.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to attack"))
        {
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            Region[] all = gm.getRegions();
            String svgname = ((SVGPath)event.getSource()).getId();
            int troops = regionToAttackWith.getNumTroops();
            if( troops == 3){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(true);
            }
            else if( troops >= 4){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(false);
            }
            else if( troops == 2){
                attackButton1.setDisable(false);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);
            }
            regionToAttack = all[Integer.parseInt(svgname.substring(3))];
            instructionLabel.setText("Select the number of dice");
            gm.setInstruction("Select the number of dice");
            dicePanel.setVisible(true);
            motivationButton.setVisible(false);
            climateButton.setVisible(false);
            pauseButton.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to get troop"))
        {
            Region[] allRegion = gm.getRegions();
            int regionid = Integer.parseInt(((SVGPath)event.getSource()).getId().substring(3));
            regionToGetTroop = allRegion[regionid];

            ObservableList<String> numbers = FXCollections.observableArrayList();

            int troopNumMin = 1;
            int troopNumMax = regionToGetTroop.getNumTroops() - 1;

            for( int i = troopNumMin ; i <= troopNumMax; i++ ){
                String numberString = "" + i;
                numbers.add(numberString);
            }
            troopLabel.setText(""+(troopNumMax-troopNumMin + 1));
            troopNo.setItems(numbers);
            troopNo.setValue("1");

            for( int i = 0; i < allRegion.length ; i++){
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                svg.setDisable(true);
            }

            troopImage.setVisible(true);
            troopLabel.setVisible(true);
            troopNo.setVisible(true);
            done.setVisible(true);
            gm.setInstruction("Select the number of troops to get");
            instructionLabel.setText("Select the number of troops to get");
            nextStage.setVisible(false);
            pauseButton.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to add troop"))
        {

            Region[] allRegion = gm.getRegions();
            Region r = allRegion[Integer.parseInt((((SVGPath)event.getSource()).getId().substring(3)))];
            TurnManager tm = gm.getTm();
            tm.fortify(regionToGetTroop, troopNumToFortify, r);

            Label lbl = (Label) sc.lookup("#lbl" + regionToGetTroop.getRegionID());
            lbl.setText("" + regionToGetTroop.getNumTroops());
            lbl = (Label) sc.lookup("#lbl" + r.getRegionID());
            lbl.setText("" + r.getNumTroops());
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];

            for( int i = 0; i < allRegion.length ; i++){
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                svg.setDisable(true);
                Region region = allRegion[i];
                ArrayList<Integer> connectedOwnedRegions = region.getConnectedOwnedRegions(allRegion,p.getRegionIds());
                if( p.hasRegion(i) && allRegion[i].getNumTroops() > 1 && !(connectedOwnedRegions.size() == 1))
                    svg.setDisable(false);
            }
            gm.setInstruction("Select a region you want to get troop");
            instructionLabel.setText("Select a region you want to get troop");
            nextStage.setVisible(true);
            pauseButton.setVisible(true);
        }
    }
    public void attackClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if( e.getSource() == attackButton1 )
        {
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            TurnManager tm = gm.getTm();
            ArrayList<Object> returns = tm.oneTimeAttack( regionToAttackWith, 1,regionToAttack);
            ArrayList<Integer> attackersDice = (ArrayList<Integer>) returns.get(0);
            ArrayList<Integer> defenderDice = (ArrayList<Integer>) returns.get(1);
            int[] lostTroops =  (int[]) returns.get(2);

            Region[] all = gm.getRegions();
            for( int i = 0; i < all.length; i++){

                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                if( p.hasRegion(i))
                    svg.setDisable(false);
                else
                    svg.setDisable(true);
                svg.setFill(Paint.valueOf( players[all[i].getOwnerID()].getColor()));
                Label lbl = (Label) sc.lookup("#lbl" + i);
                lbl.setText(""+all[i].getNumTroops());
            }
            attackLabel.setText("Attacker lost: " + lostTroops[0] + " companion(s) Defender lost: " + lostTroops[1] + " companion(s)");

            for( int i = 1; i < 6; i++)
            {
                Stage g = (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene fc = g.getScene();
                ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            int defenderDiceNum = defenderDice.size();

            for( int i = 1; i < 6; i++)
            {
                if( i == 1 || i == 4 || ((i ==5) && defenderDiceNum==2) )
                {
                    int numberShowing = 0;
                    if( i == 1)
                        numberShowing = attackersDice.get(0);
                    if( i == 4)
                        numberShowing = defenderDice.get(0);
                    if( i == 5)
                        numberShowing = defenderDice.get(1);

                    File file = new File("src/sample/Images/dice" + numberShowing + ".jpeg");
                    Image image = new Image(file.toURI().toString());
                    Stage ss = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene fc = ss.getScene();
                    ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                    diceImage.setImage(image);
                    diceImage.setVisible(true);
                }
            }


            int troops = regionToAttackWith.getNumTroops();
            if( troops == 3){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(true);
            }
            else if( troops >= 4){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(false);
            }
            else if( troops == 2){
                attackButton1.setDisable(false);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);
            }
            else {
                attackButton1.setDisable(true);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);

            }
            if( regionToAttack.getOwnerID() == p.getId()){

                for( int i = 1; i < 6; i++)
                {
                    Stage g = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene fc = g.getScene();
                    ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                    diceImage.setVisible(false);
                }

                dicePanel.setVisible(false);
                instructionLabel.setText("Select the number of troops to invade");
                gm.setInstruction("Select the number of troops to invade");
                troopImage.setVisible(true);

                int troopNumMax;
                if( gm.isWeather() ) {
                    if (regionToAttackWith.hasDrought() && regionToAttackWith.getNumTroops()>tm.getDROUGHT_LIMIT())
                        troopNumMax = tm.getDROUGHT_LIMIT();
                    else
                        troopNumMax = regionToAttackWith.getNumTroops() - 1;
                }
                else
                    troopNumMax = regionToAttackWith.getNumTroops() - 1;

                int troopNumMin = 1;
                ObservableList<String> numbers = FXCollections.observableArrayList();

                for( int i = troopNumMin ; i <= troopNumMax; i++ ){
                    String numberString = "" + i;
                    numbers.add(numberString);
                }
                troopNo.setItems(numbers);
                troopNo.setValue(""+troopNumMin);
                troopNo.setVisible(true);
                troopLabel.setText("" + (troopNumMax- troopNumMin));
                troopLabel.setVisible(true);
                done.setVisible(true);
            }
        }
        else if( e.getSource() == attackButton2 )
        {
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            TurnManager tm = gm.getTm();
            ArrayList<Object> returns = tm.oneTimeAttack( regionToAttackWith, 2,regionToAttack);
            ArrayList<Integer> attackersDice = (ArrayList<Integer>) returns.get(0);
            ArrayList<Integer> defenderDice = (ArrayList<Integer>) returns.get(1);
            int[] lostTroops =  (int[]) returns.get(2);
            attackLabel.setText("Attacker lost: " + lostTroops[0] + " companion(s) Defender lost: " + lostTroops[1] + " companion(s)");

            Region[] all = gm.getRegions();
            for( int i = 0; i < all.length; i++){

                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                if( p.hasRegion(i))
                    svg.setDisable(false);
                else
                    svg.setDisable(true);
                svg.setFill(Paint.valueOf( players[all[i].getOwnerID()].getColor()));
                Label lbl = (Label) sc.lookup("#lbl" + i);
                lbl.setText(""+all[i].getNumTroops());
            }
            for( int i = 1; i < 6; i++)
            {
                Stage g = (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene fc = g.getScene();
                ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            int defenderDiceNum = defenderDice.size();

            for( int i = 1; i < 6; i++)
            {
                if( i == 1 || i == 2 || i == 4 || ((i ==5) && defenderDiceNum==2) )
                {
                    int numberShowing = 0;
                    if( i == 1)
                        numberShowing = attackersDice.get(0);
                    if( i == 2)
                        numberShowing = attackersDice.get(1);
                    if( i == 4)
                        numberShowing = defenderDice.get(0);
                    if( i == 5)
                        numberShowing = defenderDice.get(1);

                    File file = new File("src/sample/Images/dice" + numberShowing + ".jpeg");
                    Image image = new Image(file.toURI().toString());
                    Stage ss = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene fc = ss.getScene();
                    ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                    diceImage.setImage(image);
                    diceImage.setVisible(true);
                }
            }


            int troops = regionToAttackWith.getNumTroops();
            if( troops == 3){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(true);
            }
            else if( troops >= 4){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(false);
            }
            else if( troops == 2){
                attackButton1.setDisable(false);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);
            }
            else {
                attackButton1.setDisable(true);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);

            }
            if( regionToAttack.getOwnerID() == p.getId()){
                for( int i = 1; i < 6; i++)
                {
                    Stage g = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene fc = g.getScene();
                    ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                    diceImage.setVisible(false);
                }

                dicePanel.setVisible(false);
                instructionLabel.setText("Select the number of troops to invade");
                gm.setInstruction("Select the number of troops to invade");
                troopImage.setVisible(true);

                int troopNumMax;
                if( gm.isWeather() ) {
                    if (regionToAttackWith.hasDrought() && regionToAttackWith.getNumTroops()>tm.getDROUGHT_LIMIT())
                        troopNumMax = tm.getDROUGHT_LIMIT();
                    else
                        troopNumMax = regionToAttackWith.getNumTroops() - 1;
                }
                else
                    troopNumMax = regionToAttackWith.getNumTroops() - 1;
                int troopNumMin = 2;
                ObservableList<String> numbers = FXCollections.observableArrayList();

                for( int i = troopNumMin ; i <= troopNumMax; i++ ){
                    String numberString = "" + i;
                    numbers.add(numberString);
                }
                troopNo.setItems(numbers);
                troopNo.setValue(""+troopNumMin);
                troopNo.setVisible(true);
                troopLabel.setText("" + (troopNumMax- troopNumMin));
                troopLabel.setVisible(true);
                done.setVisible(true);
            }


        }

        else if( e.getSource() == attackButton3 ) {
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            TurnManager tm = gm.getTm();

            ArrayList<Object> returns = tm.oneTimeAttack(regionToAttackWith, 3, regionToAttack);
            ArrayList<Integer> attackersDice = (ArrayList<Integer>) returns.get(0);
            ArrayList<Integer> defenderDice = (ArrayList<Integer>) returns.get(1);
            int[] lostTroops = (int[]) returns.get(2);
            attackLabel.setText("Attacker lost: " + lostTroops[0] + " companion(s) Defender lost: " + lostTroops[1] + " companion(s)");

            Region[] all = gm.getRegions();
            for( int i = 0; i < all.length; i++){

                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                if( p.hasRegion(i))
                    svg.setDisable(false);
                else
                    svg.setDisable(true);
                svg.setFill(Paint.valueOf( players[all[i].getOwnerID()].getColor()));
                Label lbl = (Label) sc.lookup("#lbl" + i);
                lbl.setText(""+all[i].getNumTroops());
            }

            for( int i = 1; i < 6; i++)
            {
                Stage g = (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene fc = g.getScene();
                ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            int defenderDiceNum = defenderDice.size();

            for (int i = 1; i < 6; i++) {
                if (i == 1 || i == 2 || i == 3 || i == 4 || ((i == 5) && defenderDiceNum == 2)) {
                    int numberShowing = 0;
                    if (i == 1)
                        numberShowing = attackersDice.get(0);
                    if (i == 2)
                        numberShowing = attackersDice.get(1);
                    if (i == 3)
                        numberShowing = attackersDice.get(2);
                    if (i == 4)
                        numberShowing = defenderDice.get(0);
                    if (i == 5)
                        numberShowing = defenderDice.get(1);

                    File file = new File("src/sample/Images/dice" + numberShowing + ".jpeg");
                    Image image = new Image(file.toURI().toString());
                    Stage ss = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    Scene fc = ss.getScene();
                    ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                    diceImage.setImage(image);
                    diceImage.setVisible(true);
                }
            }

            int troops = regionToAttackWith.getNumTroops();
            if( troops == 3){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(true);
            }
            else if( troops >= 4){
                attackButton1.setDisable(false);
                attackButton2.setDisable(false);
                attackButton3.setDisable(false);
            }
            else if( troops == 2){
                attackButton1.setDisable(false);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);
            }
            else {
                attackButton1.setDisable(true);
                attackButton2.setDisable(true);
                attackButton3.setDisable(true);

            }

            if( regionToAttack.getOwnerID() == p.getId()){

                for( int i = 1; i < 6; i++)
                {
                    Stage g = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene fc = g.getScene();
                    ImageView diceImage = (ImageView) fc.lookup("#dice" + i);
                    diceImage.setVisible(false);
                }
                dicePanel.setVisible(false);
                instructionLabel.setText("Select the number of troops to invade");
                gm.setInstruction("Select the number of troops to invade");
                troopImage.setVisible(true);

                int troopNumMax;
                if( gm.isWeather() ) {
                    if (regionToAttackWith.hasDrought() && regionToAttackWith.getNumTroops()>tm.getDROUGHT_LIMIT())
                        troopNumMax = tm.getDROUGHT_LIMIT();
                    else
                        troopNumMax = regionToAttackWith.getNumTroops() - 1;
                }
                else
                    troopNumMax = regionToAttackWith.getNumTroops() - 1;
                int troopNumMin = 3;
                ObservableList<String> numbers = FXCollections.observableArrayList();

                for( int i = troopNumMin ; i <= troopNumMax; i++ ){
                    String numberString = "" + i;
                    numbers.add(numberString);
                }
                troopNo.setItems(numbers);
                troopNo.setValue(""+troopNumMin);
                troopNo.setVisible(true);
                troopLabel.setText("" + (troopNumMax- troopNumMin));
                troopLabel.setVisible(true);
                done.setVisible(true);
            }
        }


    }

    public void onRegionEntered( MouseEvent event) throws IOException {

    }

    public void onRegionExited( MouseEvent event) throws IOException {

    }

    public void doneClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if( instructionLabel.getText().equals("Select the number of troops to invade") )
        {
            Player[] players = gm.getPlayers();
            String troopNoString = (String)troopNo.getValue();
            int troopNumberToPlace = Integer.parseInt(troopNoString);

            TurnManager tm = gm.getTm();
            tm.moveTroops(regionToAttackWith, troopNumberToPlace, regionToAttack);
            SVGPath svg = (SVGPath) sc.lookup("#svg" + regionToAttack.getRegionID());
            svg.setFill(Paint.valueOf(players[regionToAttack.getOwnerID()].getColor()));
            Label lbl = (Label) sc.lookup("#lbl"+regionToAttack.getRegionID());
            lbl.setText(""+regionToAttack.getNumTroops());
            lbl = (Label) sc.lookup("#lbl"+regionToAttackWith.getRegionID());
            lbl.setText(""+regionToAttackWith.getNumTroops());
            ImageView com = (ImageView) sc.lookup("#com"+regionToAttack.getRegionID());
            com.setVisible(false);
            troopImage.setVisible(false);
            troopLabel.setVisible(false);
            troopNo.setVisible(false);
            done.setVisible(false);
            nextStage.setVisible(true);
            instructionLabel.setText("Select a region you want to attack from");
            gm.setInstruction("Select a region you want to attack from");
            pauseButton.setVisible(true);
        }
        else if( instructionLabel.getText().equals("Select the number of troops you want to place"))
        {
            String troopNoString = (String)troopNo.getValue();
            int troopNumberToPlace = Integer.parseInt(troopNoString);
            TurnManager tm = gm.getTm();
            tm.draft( regionToPlace, troopNumberToPlace);
            Label lbl = (Label) sc.lookup("#lbl" + regionToPlace.getRegionID());
            lbl.setText( ""+regionToPlace.getNumTroops());
            troopImage.setVisible(false);
            troopLabel.setVisible(false);
            troopNo.setVisible(false);
            done.setVisible(false);
            instructionLabel.setText("Select a region you want to place troops");
            gm.setInstruction("Select a region you want to place troops");
            if( tm.getAdditionalTroops() == 0)
                nextStage.setDisable(false);
            else
                nextStage.setDisable(true);
            nextStage.setVisible(true);
            moveCom.setVisible(true);
            pauseButton.setVisible(true);
        }
        else if( instructionLabel.getText().equals("Select the number of troops to get"))
        {
            Region[] allRegion = gm.getRegions();
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            ArrayList<Integer> connectedRegions = regionToGetTroop.getConnectedOwnedRegions(allRegion,p.getRegionIds());

            for( int i = 0; i < allRegion.length ; i++){
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                svg.setDisable(true);
                if( connectedRegions.contains(i))
                    svg.setDisable(false);
            }

            troopNumToFortify = Integer.parseInt((String)troopNo.getValue());
            troopImage.setVisible(false);
            troopLabel.setVisible(false);
            troopNo.setVisible(false);
            done.setVisible(false);
            gm.setInstruction("Select a region you want to add troop");
            instructionLabel.setText("Select a region you want to add troop");
        }
    }

    public void initialize( GameManager g, MediaPlayer m) throws IOException {
        gm = g;
        music = m;
        //System.out.println(g);
        Player[] pl = gm.getPlayers();

        for(int i = 0; i <pl.length; i++)
        {
            if( i == 0){
                if( 0 == gm.getWhoseTurn())
                {
                    turn1.setVisible(true);
                }
                if( pl[0].isEliminated())
                {
                    elim1.setVisible(true);
                }
                File f = new File(pl[0].getImageUrl().substring(6));
                Image a = new Image(f.toURI().toString());
                playerImg1.setImage(a);
                playerImg1.setDisable(true);
                playerImg1.setVisible(true);
            }
            else if( i == 1){
                if( 1 == gm.getWhoseTurn())
                {
                    turn2.setVisible(true);
                }
                if( pl[1].isEliminated())
                {
                    elim2.setVisible(true);
                }
                File f = new File(pl[1].getImageUrl().substring(6));
                Image a = new Image(f.toURI().toString());
                playerImg2.setImage(a);
                playerImg2.setDisable(true);
                playerImg2.setVisible(true);
            }
            else if( i == 2){
                if( 2 == gm.getWhoseTurn())
                {
                    turn3.setVisible(true);
                }
                if( pl[2].isEliminated())
                {
                    elim3.setVisible(true);
                }
                File f = new File(pl[2].getImageUrl().substring(6));
                Image a = new Image(f.toURI().toString());
                playerImg3.setImage(a);
                playerImg3.setDisable(true);
                playerImg3.setVisible(true);
            }
            else if( i == 3){
                if( 3 == gm.getWhoseTurn())
                {
                    turn3.setVisible(true);
                }
                if( pl[3].isEliminated())
                {
                    elim3.setVisible(true);
                }
                File f = new File(pl[3].getImageUrl().substring(6));
                Image a = new Image(f.toURI().toString());
                playerImg4.setImage(a);
                playerImg4.setDisable(true);
                playerImg4.setVisible(true);
            }
        }
        stageLabel.setText(gm.getStageString());
        instructionLabel.setText(gm.getInstruction());
        /*
        String[] im = null;

        File f = new File(im[0].substring(6));
        Image a = new Image(f.toURI().toString());
        playerImg1.setImage(a);
        playerImg1.setDisable(true);

        f = new File(im[1].substring(6));
        a = new Image(f.toURI().toString());
        playerImg2.setImage(a);
        playerImg2.setDisable(true);

        f = new File(im[2].substring(6));
        a = new Image(f.toURI().toString());
        playerImg3.setImage(a);
        playerImg3.setDisable(true);

        f = new File(im[3].substring(6));
        a = new Image(f.toURI().toString());
        playerImg4.setImage(a);
        playerImg4.setDisable(true);
        //Parent root = FXMLLoader.load(getClass().getResource("GameMapController.fxml"));
        /*stage = sv;
        System.out.println(sv);
        Scene sc = stage.getScene();
        SVGPath foo = (SVGPath) root.lookup("#svg1");
        // String d = (String)stg.getUserData();
        //.println(d);
        foo.setFill(Paint.valueOf("#c67b7b"));*/
    }

    public void onBackClicked(MouseEvent event) throws IOException {
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


    public void hireClicked(ActionEvent e)
    {
        TurnManager tm = gm.getTm();
        String troopNumberValue = (String)chooseTroopNumber.getValue();
        int troopNumber = Integer.parseInt(troopNumberValue);
        tm.buyMercenaries(troopNumber);
        buyPanel.setVisible(false);
        nextStage.setVisible(true);
        eventButton.setVisible(true);
        Player p = gm.getPlayers()[gm.getWhoseTurn()];
        if( p.getMoney() < 5)
            eventButton.setDisable(true);
        motivationButton.setVisible(true);
        climateButton.setVisible(true);
        pauseButton.setVisible(true);
    }
    public void playerProfileClicked( MouseEvent e)
    {
        final int  troopPrice = 3;
        Player p = gm.getPlayers()[gm.getWhoseTurn()];
        playerMoney.setText("$" + p.getMoney());
        int totalTroops = p.getMoney() / troopPrice;
        ObservableList<String> numbers = FXCollections.observableArrayList();
        numbers.add("0");

        for( int i = 1; i <= totalTroops; i++ ){
            String numberr = "" + i;
            numbers.add(numberr);
        }

        chooseTroopNumber.setItems(numbers);
        chooseTroopNumber.setValue("0");


        ArrayList<TroopCardType> troopCards = p.getTroopCards();
        int type1=0;
        int type2=0;
        int type3=0;
        int type4=0;
        for( int i =0 ; i < troopCards.size(); i++){
            if( troopCards.get(i) == TroopCardType.INFANTRY)
                type1++;
            else if( troopCards.get(i) == TroopCardType.ARTILLERY)
                type2++;
            else if( troopCards.get(i) == TroopCardType.MARINES)
                type3++;
            else if( troopCards.get(i) == TroopCardType.MERCENARY)
                type4++;
        }

        ObservableList<String> cardnumber1 = FXCollections.observableArrayList();
        cardnumber1.add("0");
        for( int i = 1; i <= type1; i++ ){
            String numberr = "" + i;
            cardnumber1.add(numberr);
        }
        troopCardNum1.setItems(cardnumber1);
        troopCardNum1.setValue("0");

        ObservableList<String> cardnumber2 = FXCollections.observableArrayList();
        cardnumber2.add("0");
        for( int i = 1; i <= type2; i++ ){
            String numberr = "" + i;
            cardnumber2.add(numberr);
        }
        troopCardNum2.setItems(cardnumber2);
        troopCardNum2.setValue("0");

        ObservableList<String> cardnumber3 = FXCollections.observableArrayList();
        cardnumber3.add("0");
        for( int i = 1; i <= type3; i++ ){
            String numberr = "" + i;
            cardnumber3.add(numberr);
        }
        troopCardNum3.setItems(cardnumber3);
        troopCardNum3.setValue("0");

        ObservableList<String> cardnumber4 = FXCollections.observableArrayList();
        cardnumber4.add("0");
        for( int i = 1; i <= type4; i++ ){
            String numberr = "" + i;
            cardnumber4.add(numberr);
        }
        troopCardNum4.setItems(cardnumber4);
        troopCardNum4.setValue("0");

        troopCard1.setText( ""+ type1);
        troopCard2.setText(""+ type2);
        troopCard3.setText(""+ type3);
        troopCard4.setText(""+ type4);
        missionLabel.setText(p.getMission().getMissionName());

        if( stageLabel.getText().equals("BUY STAGE"))
        {
            hire.setDisable(false);
            combineButton.setDisable(false);
        }
        else{
            hire.setDisable(true);
            combineButton.setDisable(true);
        }
        buyPanel.setVisible(true);
        nextStage.setVisible(false);
        eventButton.setVisible(false);
        motivationButton.setVisible(false);
        climateButton.setVisible(false);
        pauseButton.setVisible(false);
    }

    public void combineClicked( ActionEvent e)
    {
        TurnManager tm = gm.getTm();
        String troopCard1 = (String)troopCardNum1.getValue();
        String troopCard2 = (String)troopCardNum2.getValue();
        String troopCard3 = (String)troopCardNum3.getValue();
        String troopCard4 = (String)troopCardNum4.getValue();
        int troopCardNumber1 = Integer.parseInt(troopCard1);
        int troopCardNumber2 = Integer.parseInt(troopCard2);
        int troopCardNumber3 = Integer.parseInt(troopCard3);
        int troopCardNumber4 = Integer.parseInt(troopCard4);
        ArrayList<TroopCardType> cardsToCombin = new ArrayList<>();
        for( int i = 0; i < troopCardNumber1; i++)
        {
            cardsToCombin.add(TroopCardType.INFANTRY);
        }
        for( int i = 0; i < troopCardNumber2; i++)
        {
            cardsToCombin.add(TroopCardType.ARTILLERY);
        }
        for( int i = 0; i < troopCardNumber3; i++ )
        {
            cardsToCombin.add(TroopCardType.MARINES);
        }
        for( int i = 0; i < troopCardNumber4; i++)
        {
            cardsToCombin.add(TroopCardType.MERCENARY);
        }
        tm.combineCards(cardsToCombin);
        buyPanel.setVisible(false);
        nextStage.setVisible(true);
        eventButton.setVisible(true);
        motivationButton.setVisible(true);
        climateButton.setVisible(true);
        pauseButton.setVisible(true);
    }
    public void organizeEvent( ActionEvent e)
    {
        Player p = gm.getPlayers()[gm.getWhoseTurn()];
        Region[] r = gm.getRegions();
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        for( int i = 0; i < r.length; i++)
        {
            if( !p.hasRegion(i))
            {
                SVGPath region = (SVGPath) sc.lookup("#svg" + i);
                region.setDisable(true);
            }
        }
        gm.setInstruction("Select a region to organize event");
        instructionLabel.setText("Select a region to organize event");
        nextStage.setVisible(false);
        eventButton.setVisible(false);
        pauseButton.setVisible(false);
    }

    public void buyProfileExit( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        Button b = (Button)e.getSource();
        if( b == buyExit)
        {
            Player p = gm.getPlayers()[gm.getWhoseTurn()];
            eventButton.setVisible(true);
            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            buyPanel.setVisible(false);
            nextStage.setVisible(true);
            if( gm.getStageString().equals("BUY STAGE"))
                eventButton.setVisible(true);
            else
                eventButton.setVisible(false);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);
            pauseButton.setVisible(true);
        }

        else if( b == attackExit)
        {
            instructionLabel.setText("Select a region you want to attack from");
            gm.setInstruction("Select a region you want to attack from");
            nextStage.setVisible(true);
            dicePanel.setVisible(false);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);
            for( int i = 1 ; i < 6; i++ )
            {
                ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            Label lbl = (Label)sc.lookup("#lbl"+regionToAttackWith.getRegionID());
            lbl.setText(""+regionToAttackWith.getNumTroops());
            lbl = (Label)sc.lookup("#lbl"+regionToAttack.getRegionID());
            lbl.setText(""+regionToAttack.getNumTroops());
            pauseButton.setVisible(true);
        }
        else if( b == exitTurn)
        {
            nextStage.setVisible(true);
            eventButton.setVisible(true);
            Player p = gm.getPlayers()[gm.getWhoseTurn()];
            Region[] allRegion = gm.getRegions();
            for( int i= 0; i < allRegion.length; i++){
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                svg.setDisable(true);
                if( p.hasRegion(i))
                    svg.setDisable(false);
            }
            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            ImageView playerProfileImg = (ImageView) sc.lookup("#playerImg" + (p.getId()+1));
            playerProfileImg.setDisable(false);
            nextStage.setVisible(true);
            nextTurnPanel.setVisible(false);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);
            pauseButton.setVisible(true);
        }
    }

    public void pauseClicked( ActionEvent e)
    {
        pausePanel.setVisible(true);
    }

    public void saveGameOpened( ActionEvent e)
    {
        savePanel.setVisible(true);
        pausePanel.setVisible(false);
    }

    public void saveGameClicked( ActionEvent e)
    {
        FileManager fm = new FileManager();
        fm.writeGame( saveGameName.getText(), gm);
        savePanel.setVisible(false);
    }

    public void exitPause( ActionEvent e)
    {
        pausePanel.setVisible(false);
    }

    public void changeSettingOpened( ActionEvent e)
    {
        pausePanel.setVisible(false);
        settingsPanel.setVisible(true);
    }
    public void closeSetting( ActionEvent e)
    {
        settingsPanel.setVisible(false);
    }

    public void showClimate( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if(((Button)(e.getSource())).getText().equals("Show Climate"))
        {
            Region[] allregion = gm.getRegions();
            for( int i = 0 ; i < allregion.length; i++ )
            {
                ClimateType ct = allregion[i].getClimate();
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);

                if( ct == ClimateType.COLD)
                {
                    svg.setFill(Paint.valueOf("#175ba3"));
                }
                if( ct == ClimateType.WARM )
                {
                    svg.setFill(Paint.valueOf("#bf8b22"));
                }
                if( ct == ClimateType.HOT)
                {
                    svg.setFill(Paint.valueOf("#c41910"));
                }

                Label lbl = (Label) sc.lookup("#lbl" + i);
                String s = "" +allregion[i].getNumTroops();
                lbl.setText(s);
                lbl.setVisible(true);
            }
            motivationButton.setVisible(false);
            infoLabel1.setVisible(true);
            infoLabel1.setText("Hot");
            info1.setVisible(true);
            info1.setFill(Paint.valueOf("#c41910"));
            infoLabel2.setVisible(true);
            infoLabel2.setText("Warm");
            info2.setVisible(true);
            info2.setFill(Paint.valueOf("#bf8b22"));
            infoLabel3.setVisible(true);
            infoLabel3.setText("Cold");
            info3.setVisible(true);
            info3.setFill(Paint.valueOf("#175ba3"));
            ((Button)(e.getSource())).setText("Show Map");
        }
        else {
            showMap(sc);
            motivationButton.setVisible(true);
            info3.setVisible(false);
            infoLabel3.setVisible(false);
            info2.setVisible(false);
            infoLabel2.setVisible(false);
            info1.setVisible(false);
            infoLabel1.setVisible(false);
            ((Button)(e.getSource())).setText("Show Climate");
        }
    }

    public void nextStageClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if( stageLabel.getText().equals("BUY STAGE"))

        {   Player p = gm.getPlayers()[gm.getWhoseTurn()];
            Region[] all = gm.getRegions();
            for( int i = 0; i < all.length; i++)
            {
                SVGPath region = (SVGPath) sc.lookup("#svg" + i);
                region.setDisable(true);
                if( p.hasRegion(i))
                {
                    region.setDisable(false);
                }
            }
            TurnManager tm = gm.getTm();
            if( tm.getAdditionalTroops() > 0)
                nextStage.setDisable(true);
            eventButton.setVisible(false);
            stageLabel.setText("DRAFT STAGE");
            gm.setStageString("DRAFT STAGE");
            instructionLabel.setText("Select a region you want to place troops");
            gm.setInstruction("Select a region you want to place troops");
            moveCom.setVisible(true);
            eventButton.setVisible(false);
            pauseButton.setVisible(true);
        }
        else if( stageLabel.getText().equals("DRAFT STAGE") )
        {
            Player p = gm.getPlayers()[gm.getWhoseTurn()];
            Region[] all = gm.getRegions();
            for( int i = 0; i < all.length; i++)
            {
                if( !p.hasRegion(i))
                {
                    SVGPath region = (SVGPath) sc.lookup("#svg" + i);
                    region.setDisable(true);
                }
                else {
                    if( all[i].getNumTroops() < 2 || all[i].hasPlague() || all[i].getEnemyRegions(p.getRegionIds()).size() == 0)
                    {
                        SVGPath region = (SVGPath) sc.lookup("#svg" + i);
                        region.setDisable(true);
                    }
                }
            }
            moveCom.setVisible(false);
            stageLabel.setText("ATTACK STAGE");
            gm.setStageString("ATTACK STAGE");
            instructionLabel.setText("Select a region you want to attack from");
            gm.setInstruction("ATTACK STAGE");
            pauseButton.setVisible(true);
        }
        else if( stageLabel.getText().equals("ATTACK STAGE") )
        {
            Region[] allRegion = gm.getRegions();
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];

            for( int i = 0; i < allRegion.length ; i++){
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);
                svg.setDisable(true);
                Region r = allRegion[i];
                ArrayList<Integer> connectedOwnedRegions = r.getConnectedOwnedRegions(allRegion,p.getRegionIds());
                if( p.hasRegion(i) && allRegion[i].getNumTroops() > 1 && !(connectedOwnedRegions.size() == 1))
                    svg.setDisable(false);
            }

            gm.setStageString("FORTIFY STAGE");
            stageLabel.setText("FORTIFY STAGE");
            gm.setInstruction("Select a region you want to get troop");
            instructionLabel.setText("Select a region you want to get troop");
            pauseButton.setVisible(true);
        }
        else if( stageLabel.getText().equals("FORTIFY STAGE") )
        {
            Player[] players = gm.getPlayers();
            Player p = players[gm.getWhoseTurn()];
            Region[] allRegion = gm.getRegions();

            if( gm.isLast(p) ){
                gm.operationsAfterLastPlayer(allRegion, gm.getTurnCount());
                gm.setTurnCount(gm.getTurnCount() + 1);
            }

            gm.nextTurn();

            Player p2 = gm.getPlayers()[gm.getWhoseTurn()];


            File imageFile = new File(p2.getImageUrl().substring(6));
            Image image = new Image(imageFile.toURI().toString());
            imageTurn.setImage(image);
            ImageView playerTurnImage = (ImageView) sc.lookup("#turn" + (p.getId() + 1)  );
            playerTurnImage.setVisible(false);

            playerImg1.setDisable(true);
            playerImg2.setDisable(true);
            playerImg3.setDisable(true);
            playerImg4.setDisable(true);

            for(int i = 0; i <players.length; i++)
            {
                if( i == 0){
                    if( 0 == gm.getWhoseTurn())
                    {
                        turn1.setVisible(true);
                    }
                    if( players[0].isEliminated())
                    {
                        elim1.setVisible(true);
                    }
                }
                else if( i == 1){
                    if( 1 == gm.getWhoseTurn())
                    {
                        turn2.setVisible(true);
                    }
                    if( players[1].isEliminated())
                    {
                        elim2.setVisible(true);
                    }
                }
                else if( i == 2){
                    if( 2 == gm.getWhoseTurn())
                    {
                        turn3.setVisible(true);
                    }
                    if( players[2].isEliminated())
                    {
                        elim3.setVisible(true);
                    }
                }
                else if( i == 3){
                    if( 3 == gm.getWhoseTurn())
                    {
                        turn3.setVisible(true);
                    }
                    if( players[3].isEliminated())
                    {
                        elim3.setVisible(true);
                    }
                }
            }

            gm.setStageString("BUY STAGE");
            stageLabel.setText("BUY STAGE");
            gm.setInstruction("Click on your avatar to buy or organize event");
            instructionLabel.setText("Click on your avatar to buy or organize event");
            nextTurnPanel.setVisible(true);
            nextStage.setVisible(false);
            motivationButton.setVisible(false);
            climateButton.setVisible(false);
            pauseButton.setVisible(false);
        }
    }

    public void moveComClicked( ActionEvent e)
    {
        nextStage.setVisible(false);
        moveCom.setVisible(false);
        gm.setInstruction("Select a region to place commander");
        instructionLabel.setText("Select a region to place commander");
        pauseButton.setVisible(false);
    }

    public void motivationClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if(((Button)(e.getSource())).getText().equals("Show Motivation"))
        {
            Region[] allregion = gm.getRegions();
            for( int i = 0 ; i < allregion.length; i++ )
            {
                MotivationLevel ml= allregion[i].getMotivation();
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);

                if( ml == MotivationLevel.HIGH)
                {
                    svg.setFill(Paint.valueOf( "#21b09f"));
                }
                if( ml == MotivationLevel.NORMAL)
                {
                    svg.setFill(Paint.valueOf( "#a4cf23"));
                }
                if( ml == MotivationLevel.LOW)
                {
                    svg.setFill(Paint.valueOf( "#eba234"));
                }
                if( ml == MotivationLevel.NONE)
                {
                    svg.setFill(Paint.valueOf("#eb4c34"));
                }
                Label lbl = (Label) sc.lookup("#lbl" + i);
                String s = "" +allregion[i].getNumTroops();
                lbl.setText(s);
                lbl.setVisible(true);
            }
            info4.setVisible(true);
            info4.setFill(Paint.valueOf("#eb4c34"));
            infoLabel4.setVisible(true);
            infoLabel4.setText("Unmotivated");
            info3.setVisible(true);
            info3.setFill(Paint.valueOf("#eba234"));
            infoLabel3.setVisible(true);
            infoLabel3.setText("Low Motivated");
            info2.setVisible(true);
            info2.setFill(Paint.valueOf("#a4cf23"));
            infoLabel2.setVisible(true);
            infoLabel2.setText("Normal Motivated");
            info1.setVisible(true);
            info1.setFill(Paint.valueOf("#21b09f"));
            infoLabel1.setVisible(true);
            infoLabel1.setText("High Motivated");
            climateButton.setVisible(false);
            ((Button)(e.getSource())).setText("Show Map");
        }
        else {
            showMap(sc);
            info4.setVisible(false);
            infoLabel4.setVisible(false);
            info3.setVisible(false);
            infoLabel3.setVisible(false);
            info2.setVisible(false);
            infoLabel2.setVisible(false);
            info1.setVisible(false);
            infoLabel1.setVisible(false);
            climateButton.setVisible(true);
            ((Button)(e.getSource())).setText("Show Motivation");
        }
    }
}