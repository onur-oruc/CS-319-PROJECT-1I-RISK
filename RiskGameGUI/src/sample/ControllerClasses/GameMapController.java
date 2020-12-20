package sample.ControllerClasses;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
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
import sample.Entities.*;
import sample.Enums.*;
import sample.Managers.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * This class will be used to connect game map user interface and game logic
 * This class holds the game manager as an attribute and calls its functions
 * according to the user actions on the interface
 * @author Emin Adem Buran
 */

public class GameMapController {

    GameManager gm;
    MediaPlayer music;
    Region regionToPlace, regionToAttackWith, regionToAttack, regionToGetTroop;
    int troopNumToFortify;
    @FXML
    AnchorPane gamerPanel, dicePanel, nextTurnPanel, buyPanel, pausePanel, savePanel, settingsPanel;
    @FXML
    Label instructionLabel, attackLabel, stageLabel, troopLabel,troopCard2, troopCard3,
            troopCard4,missionLabel, infoLabel1, infoLabel2,infoLabel3,infoLabel4,playerMoney, troopCard1;
    @FXML
    ImageView troopImage, playerImg1, playerImg2,playerImg3, playerImg4, imageTurn,
            turn1, turn2, turn3, turn4, elim1, elim2, elim3, elim4, dice1, dice2, dice3, dice4, dice5;
    @FXML
    ChoiceBox troopNo;
    @FXML
    Button moveCom, done, eventButton, attackButton1, attackButton2, attackButton3,
            nextStage, exitTurn, hire, combineButton, pauseButton
            , attackExit, buyExit, distribute, climateButton, motivationButton;
    @FXML
    Rectangle info1, info2, info3, info4;
    @FXML
    ComboBox chooseTroopNumber, troopCardNum1, troopCardNum2, troopCardNum3, troopCardNum4;
    @FXML
    TextField saveGameName;

    /**
     * This method update the disability of player profile images
     * This method is public as it is connected to fxml file
     * @param set1, set2, set3, set4
     */
    public void playerImageDisable(boolean set1, boolean set2,boolean set3,boolean set4)
    {
        playerImg1.setDisable(set1);
        playerImg2.setDisable(set2);
        playerImg3.setDisable(set3);
        playerImg4.setDisable(set4);
    }

    /**
     * This method makes the regions of a player clickable
     * and makes other regions disabled
     * This method is public as it is connected to fxml file
     * @param p, regions, s
     */
    public void enableRegions( Player p, Region[] regions, Scene s)
    {
        for( int i = 0; i < regions.length; i++)
        {
            SVGPath svg = (SVGPath) s.lookup("#svg" + i);
            svg.setDisable(true);
            if( p.hasRegion(i))
                svg.setDisable(false);
        }
    }

    /**
     * This method listens for the actions on the distribute regions button
     * When the button is clicked instructon labels are setted for the current stage of the game
     * Visibility of other elements are arranged according to this stage
     * This method is public as it is connected to fxml file
     * @param e
     */
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
        playerImageDisable(true,true,true,true);

        Region[] allRegions = gm.getRegions();

        if(stageLabel.getText().equals("BUY STAGE"))
        {
            nextStage.setVisible(true);
            eventButton.setVisible(true);
            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            enableRegions(p,allRegions,s);
        }
        else if(stageLabel.getText().equals("DRAFT STAGE"))
        {
            nextStage.setVisible(true);
            if( tm.getAdditionalTroops() != 0)
                nextStage.setDisable(true);
            moveCom.setVisible(true);
            enableRegions(p,allRegions,s);
        }
        else if(stageLabel.getText().equals("ATTACK STAGE"))
        {
            nextStage.setVisible(true);
            enableRegions(p,allRegions,s);
        }
        else if(stageLabel.getText().equals("FORTIFY STAGE"))
        {
            nextStage.setVisible(true);
            enableRegions(p,allRegions,s);
        }

        int turn = gm.getWhoseTurn();
        if( turn == 0)
            playerImg1.setDisable(false);
        if( turn == 1)
            playerImg2.setDisable(false);
        if( turn == 2)
            playerImg3.setDisable(false);
        if( turn == 3)
            playerImg4.setDisable(false);
    }

    /**
     * This method is used in order to adjust all the regions on the game map
     * according to the players ownership and existence of commander/goldmine
     * If a region has a commander or gold mine, small icons are added
     * Color of the region is adjusted according to the owner player's color
     * This method is private as it is an auxiliary method that is needed in
     * methods of GameMapController class
     * @param sv
     */
    private void showMap( Scene sv)
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

    /**
     * This method make all regions clickable
     * This method is public as it is connected to fxml file
     * @param all, s
     */
    public void setEnabledAllRegions ( Region[] all, Scene s)
    {
        for( int i = 0; i < all.length; i++)
        {
            SVGPath region = (SVGPath) s.lookup("#svg" + i);
            region.setDisable(false);
        }
    }

    /**
     * This method listens for the actions on the regions
     * When the region is clicked, different operations are
     * executed according to the stage that play in
     * In order to get the stage of the play, instruction labels are checked
     * This method is public as it is connected to fxml file
     * @param event
     */
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
            tm.organizeEntertainment(r);
            setEnabledAllRegions(all,sc);

            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            eventButton.setVisible(true);
            pauseButton.setVisible(true);
        }
        else if( instructionLabel.getText().equals("Select a region to place commander"))
        {
            Region[] all = gm.getRegions();
            Player[] players = gm.getPlayers();

            //get selected region
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

    /**
     * This method updates the disability of attack buttons
     * This method is public as it is connected to fxml file
     * @param set1, set2, set3
     */
    public void setAttackButtonsAbility( boolean set1, boolean set2, boolean set3)
    {
        attackButton1.setDisable(set1);
        attackButton2.setDisable(set2);
        attackButton3.setDisable(set3);
    }

    /**
     * This method makes the dice images unvisible
     * This method is public as it is connected to fxml file
     */
    public void setDıceImageUnvisible( )
    {
        dice1.setVisible(false);
        dice2.setVisible(false);
        dice3.setVisible(false);
        dice4.setVisible(false);
        dice5.setVisible(false);
    }

    /**
     * This method update the dice views of the attack stage
     *
     * This method is public as it is connected to fxml file
     * @param all, sc, p, players
     */
    public void updateDiceView( Region[] all, Scene sc, Player p, Player[] players)
    {
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
    }

    /**
     * This method listens for the actions on the attack buttons
     * There are three different attack buttons in the game which are
     * Attack with 1 dice
     * Attack with 2 dice
     * Attack with 3 dice
     *
     * According to the dice number different attack operations are executed
     * by using game manager, turn manger, player, dice
     * At the end of the attack, regions are updated according
     * to the attack result; if the owner changed color of the region
     * is changed and label showing the number of troops is updated
     *
     * This method is public as it is connected to fxml file
     * @param e
     */
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

            updateDiceView( all, sc, p, players);

            attackLabel.setText("Attacker lost: " + lostTroops[0] + " companion(s) Defender lost: " + lostTroops[1] + " companion(s)");

            setDıceImageUnvisible();

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
                setAttackButtonsAbility(false,false,true);
            }
            else if( troops >= 4){
                setAttackButtonsAbility(false,false,false);
            }
            else if( troops == 2){
                setAttackButtonsAbility(false,true,true);
            }
            else {
                setAttackButtonsAbility(true,true,true);
            }

            if( regionToAttack.getOwnerID() == p.getId()){

                setDıceImageUnvisible();

                dicePanel.setVisible(false);

                instructionLabel.setText("Select the number of troops to invade");
                gm.setInstruction("Select the number of troops to invade");

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
                troopLabel.setText("" + (troopNumMax- troopNumMin));

                setVisibilityTroopNumber(true);
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

            updateDiceView( all, sc,  p,  players);

            setDıceImageUnvisible();

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
                setAttackButtonsAbility(false,false,true);
            }
            else if( troops >= 4){
                setAttackButtonsAbility(false,false,false);
            }
            else if( troops == 2){
                setAttackButtonsAbility(false,true,true);
            }
            else {
                setAttackButtonsAbility(true,true,true);
            }
            if( regionToAttack.getOwnerID() == p.getId()){

                setDıceImageUnvisible();

                dicePanel.setVisible(false);
                instructionLabel.setText("Select the number of troops to invade");

                gm.setInstruction("Select the number of troops to invade");

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
                troopLabel.setText("" + (troopNumMax- troopNumMin));

                setVisibilityTroopNumber(true);
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

            updateDiceView(  all, sc,  p, players);

            setDıceImageUnvisible();

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
                setAttackButtonsAbility(false,false,true);
            }
            else if( troops >= 4){
                setAttackButtonsAbility(false,false,false);
            }
            else if( troops == 2){
                setAttackButtonsAbility(false,true,true);
            }
            else {
                setAttackButtonsAbility(true,true,true);
            }

            if( regionToAttack.getOwnerID() == p.getId()){
                setDıceImageUnvisible();

                dicePanel.setVisible(false);

                instructionLabel.setText("Select the number of troops to invade");
                gm.setInstruction("Select the number of troops to invade");



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
                troopLabel.setText("" + (troopNumMax- troopNumMin));

                setVisibilityTroopNumber(true);
            }
        }


    }

    public void onRegionEntered( MouseEvent event) throws IOException {
        // todo for future developments
    }

    public void onRegionExited( MouseEvent event) throws IOException {
        // todo for future developments
    }

    /**
     * This method make the selection of troop number possible
     * This method is public as it is connected to fxml file
     * @param set1
     */
    public void setVisibilityTroopNumber( boolean set1)
    {
        troopImage.setVisible(set1);
        troopLabel.setVisible(set1);
        troopNo.setVisible(set1);
        done.setVisible(set1);
    }

    /**
     * This method listens for the actions on ok button
     * Ok button is used to after troop numbers is selected for 3 different situations;
     * troops to invade
     * troops to place
     * troops to get
     *
     * By using turn manager, player and region classes operations will be done
     * This method is public as it is connected to fxml file
     * @param e
     */
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

            setVisibilityTroopNumber(false);

            nextStage.setVisible(true);
            pauseButton.setVisible(true);

            instructionLabel.setText("Select a region you want to attack from");
            gm.setInstruction("Select a region you want to attack from");

        }
        else if( instructionLabel.getText().equals("Select the number of troops you want to place"))
        {
            String troopNoString = (String)troopNo.getValue();
            int troopNumberToPlace = Integer.parseInt(troopNoString);

            TurnManager tm = gm.getTm();
            tm.draft( regionToPlace, troopNumberToPlace);

            Label lbl = (Label) sc.lookup("#lbl" + regionToPlace.getRegionID());
            lbl.setText( ""+regionToPlace.getNumTroops());

            setVisibilityTroopNumber(false);

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

            setVisibilityTroopNumber(false);

            gm.setInstruction("Select a region you want to add troop");
            instructionLabel.setText("Select a region you want to add troop");
        }
    }

    public void playerImageDone( ImageView imv, Player p)
    {
        File f = new File( p.getImageUrl().substring(6));
        Image a = new Image(f.toURI().toString());
        imv.setImage(a);
        imv.setDisable(true);
        imv.setVisible(true);
    }

    /**
     * Initialize method is used for initializing the player images
     * Player images are fetched according to the user selection made in loby
     * Also method takes game manager and media player as a parameter and assigns
     * these to the attributes of the game map controller class
     * This method is public as it is connected to fxml file and used by other classes
     * @param g
     * @param m
     */

    public void initialize( GameManager g, MediaPlayer m) throws IOException {
        gm = g;
        music = m;
        //System.out.println(g);
        Player[] pl = gm.getPlayers();

        for(int i = 0; i <pl.length; i++)
        {
            if( i == 0){
                if( 0 == gm.getWhoseTurn())
                    turn1.setVisible(true);
                if( pl[0].isEliminated())
                    elim1.setVisible(true);
                playerImageDone(playerImg1, pl[0] );
            }

            else if( i == 1){
                if( 1 == gm.getWhoseTurn())
                    turn2.setVisible(true);
                if( pl[1].isEliminated())
                    elim2.setVisible(true);
                playerImageDone(playerImg2, pl[1] );
            }
            else if( i == 2){
                if( 2 == gm.getWhoseTurn())
                    turn3.setVisible(true);
                if( pl[2].isEliminated())
                    elim3.setVisible(true);
                playerImageDone(playerImg3,pl[2]);
            }
            else if( i == 3){
                if( 3 == gm.getWhoseTurn())
                    turn3.setVisible(true);
                if( pl[3].isEliminated())
                    elim3.setVisible(true);
                playerImageDone(playerImg4,pl[3]);
            }
        }

        stageLabel.setText(gm.getStageString());
        instructionLabel.setText(gm.getInstruction());

    }

    /**
     * When players wants to quit game, players is directed to main menu
     * @param event
     */

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


    /**
     * This method listens for the actions on hire mercenary button
     * When the button clicked, turn manager's buy mercenaries method is called
     * Also the disability of the event button is adjusted here
     * If player doesn't have enough money, event button will be disabled
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void hireClicked(ActionEvent e)
    {
        TurnManager tm = gm.getTm();
        String troopNumberValue = (String)chooseTroopNumber.getValue();

        int troopNumber = Integer.parseInt(troopNumberValue);
        tm.buyMercenaries(troopNumber);

        Player p = gm.getPlayers()[gm.getWhoseTurn()];

        if( p.getMoney() < 5)
            eventButton.setDisable(true);

        //update button after hiring mercenary
        motivationButton.setVisible(true);
        climateButton.setVisible(true);
        pauseButton.setVisible(true);
        buyPanel.setVisible(false);
        nextStage.setVisible(true);
        eventButton.setVisible(true);
    }


    /**
     * This method updates the combobox on the player profile
     * This method is public as it is connected to fxml file
     * @param totalNumber, troopCardNumber
     */
    public void updateCombobox( int totalNumber, ComboBox troopCardNum)
    {
        ObservableList<String> cardnumber = FXCollections.observableArrayList();
        cardnumber.add("0");
        for( int i = 1; i <= totalNumber; i++ ){
            String numberr = "" + i;
            cardnumber.add(numberr);
        }
        troopCardNum.setItems(cardnumber);
        troopCardNum.setValue("0");
    }

    /**
     * This method listens for the actions on player images
     * When the player images are clicked, their player profile panel will
     * be adjusted using player's money, number of troop cards and mission
     * If the game is in the buy stage, combine cards button on profile
     * panel will be enabled else it will stay disabled
     * Player profile panel will be visible after adjustments
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void playerProfileClicked( MouseEvent e)
    {
        TurnManager tm = gm.getTm();

        final int  troopPrice = tm.getPRICE_OF_MERCENARY();

        Player p = gm.getPlayers()[gm.getWhoseTurn()];

        playerMoney.setText("$" + p.getMoney());

        int totalTroops = p.getMoney() / troopPrice;

        //make combobox
        ObservableList<String> numbers = FXCollections.observableArrayList();
        numbers.add("0");

        for( int i = 1; i <= totalTroops; i++ ){
            String numberr = "" + i;
            numbers.add(numberr);
        }

        chooseTroopNumber.setItems(numbers);
        chooseTroopNumber.setValue("0");

        //determine the number of troop cars for each type
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

        updateCombobox( type1, troopCardNum1);
        updateCombobox( type2, troopCardNum2);
        updateCombobox( type3, troopCardNum3);
        updateCombobox( type4, troopCardNum4);

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

        //update buttons after player profile clicked
        buyPanel.setVisible(true);
        nextStage.setVisible(false);
        eventButton.setVisible(false);
        motivationButton.setVisible(false);
        climateButton.setVisible(false);
        pauseButton.setVisible(false);
    }

    /**
     * This method listens for the actions on combine cards button
     * When the combine cards button is clicked number of each troop card
     * is acquired from the combo boxes and cards are combined accordingly
     * by using turn manager class
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void combineClicked( ActionEvent e)
    {
        TurnManager tm = gm.getTm();
        String troopCard1 = (String)troopCardNum1.getValue();
        int troopCardNumber1 = Integer.parseInt(troopCard1);

        String troopCard2 = (String)troopCardNum2.getValue();
        int troopCardNumber2 = Integer.parseInt(troopCard2);

        String troopCard3 = (String)troopCardNum3.getValue();
        int troopCardNumber3 = Integer.parseInt(troopCard3);

        String troopCard4 = (String)troopCardNum4.getValue();
        int troopCardNumber4 = Integer.parseInt(troopCard4);

        ArrayList<TroopCardType> cardsToCombin = new ArrayList<>();
        for( int i = 0; i < troopCardNumber1; i++)
            cardsToCombin.add(TroopCardType.INFANTRY);
        for( int i = 0; i < troopCardNumber2; i++)
            cardsToCombin.add(TroopCardType.ARTILLERY);
        for( int i = 0; i < troopCardNumber3; i++ )
            cardsToCombin.add(TroopCardType.MARINES);
        for( int i = 0; i < troopCardNumber4; i++)
            cardsToCombin.add(TroopCardType.MERCENARY);

        tm.combineCards(cardsToCombin);

        //updatebuttons after combine clicked
        buyPanel.setVisible(false);
        nextStage.setVisible(true);
        eventButton.setVisible(true);
        motivationButton.setVisible(true);
        climateButton.setVisible(true);
        pauseButton.setVisible(true);
    }

    /**
     * This method listens for the actions on organize event button
     * When the organize event button is clicked, all the regions
     * which doesn't belong to current player is disabled
     * Instruction label and other components are updated accordingly
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void organizeEvent( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();

        Player p = gm.getPlayers()[gm.getWhoseTurn()];
        Region[] r = gm.getRegions();

        //make all regions that player does not have disabled
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



    /**
     * This method listens for the actions on exit buttons on panels
     * There are three different type of exit buttons such as
     * buy exit
     * attack exit
     * turn exit
     * All of the exit buttons closes the related panel
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void panelExit( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        Button b = (Button)e.getSource();

        if( b == buyExit)
        {
            Player p = gm.getPlayers()[gm.getWhoseTurn()];

            if( p.getMoney() < 5)
                eventButton.setDisable(true);
            buyPanel.setVisible(false);

            //change the visibility of buttons after exit buy profile
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

            //change the visibility of buttons
            nextStage.setVisible(true);
            dicePanel.setVisible(false);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);

            setDıceImageUnvisible();

            Label lbl = (Label)sc.lookup("#lbl"+regionToAttackWith.getRegionID());
            lbl.setText(""+regionToAttackWith.getNumTroops());
            lbl = (Label)sc.lookup("#lbl"+regionToAttack.getRegionID());
            lbl.setText(""+regionToAttack.getNumTroops());

            pauseButton.setVisible(true);
        }
        else if( b == exitTurn)
        {
            eventButton.setVisible(true);

            Player p = gm.getPlayers()[gm.getWhoseTurn()];
            Region[] allRegion = gm.getRegions();

            //set al regions that player doen not have disabled
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

    /**
     * The game is paused
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void pauseClicked( ActionEvent e)
    {
        pausePanel.setVisible(true);
    }

    /**
     * Save game panel will be displayed
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void saveGameOpened( ActionEvent e)
    {
        savePanel.setVisible(true);
        pausePanel.setVisible(false);
    }

    /**
     * Current game is saved by using file manager
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void saveGameClicked( ActionEvent e)
    {
        FileManager fm = new FileManager();
        fm.writeGame( saveGameName.getText(), gm);
        savePanel.setVisible(false);
    }

    /**
     * When close button is clicked on the pause panel
     * pause panel becomes unvisible
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void exitPause( ActionEvent e)
    {
        pausePanel.setVisible(false);
    }

    /**
     * When open setting is clicked the setting panel become visible
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void changeSettingOpened( ActionEvent e)
    {
        pausePanel.setVisible(false);
        settingsPanel.setVisible(true);
    }

    /**
     * When close setting is clicked the setting panel become unvisible
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void closeSetting( ActionEvent e)
    {
        settingsPanel.setVisible(false);
    }

    /**
     * This method updates the info view of maps
     * When different regions map are displayed, info views
     * which contains labels and colors will be updated
     * This method is public as it is connected to fxml file
     * @param l, r, set1, set 2, s1, s2
     */
    private void updateInfoView( Label l, Rectangle r, boolean set1, boolean set2, String s1, String s2)
    {
        l.setVisible(set1);
        l.setText(s1);
        r.setVisible(set2);
        r.setFill(Paint.valueOf(s2));
    }

    /**
     * This method listens for the actions on show climate/show map button
     * When the show climate button is clicked color of the regions updated
     * according to their climates and buttons text is updated as show map
     * When the show map button is clicked, by calling show map method
     * map is updated such as regions will have their old view
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void showClimate( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();

        if(((Button)(e.getSource())).getText().equals("Show Climate"))
        {
            Region[] allregion = gm.getRegions();

            //make map view according to climates of region
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

            updateInfoView( infoLabel1, info1, true, true, "Hot", "#c41910");
            updateInfoView(infoLabel2,info2,true,true,"Warm", "#bf8b22");
            updateInfoView(infoLabel3, info3, true, true, "Cold","#175ba3" );

            ((Button)(e.getSource())).setText("Show Map");
        }
        else {
            showMap(sc);
            motivationButton.setVisible(true);

            updateInfoView( infoLabel1, info1, false, false, "Hot", "#c41910");
            updateInfoView( infoLabel1, info1, false, false, "Hot", "#c41910");
            updateInfoView( infoLabel1, info1, false, false, "Hot", "#c41910");
            ((Button)(e.getSource())).setText("Show Climate");
        }
    }

    /**
     * This method listens for the actions on next stage button
     * When the next stage button is clicked, according to the current stage
     * different operations are executed. There are 4 stages such as
     * Buy Stage
     * Draft Stage
     * Attack Stage
     * Fortify Stage
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void nextStageClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if( stageLabel.getText().equals("BUY STAGE"))
        {
            Player p = gm.getPlayers()[gm.getWhoseTurn()];
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


            stageLabel.setText("DRAFT STAGE");
            gm.setStageString("DRAFT STAGE");

            instructionLabel.setText("Select a region you want to place troops");
            gm.setInstruction("Select a region you want to place troops");

            //update buttons
            moveCom.setVisible(true);
            eventButton.setVisible(false);
            pauseButton.setVisible(true);
            eventButton.setVisible(false);
        }
        else if( stageLabel.getText().equals("DRAFT STAGE") )
        {
            Player p = gm.getPlayers()[gm.getWhoseTurn()];
            Region[] all = gm.getRegions();

            //make all regions that player does not have disabled and other regions abled according to attack possibility of a region
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


            stageLabel.setText("ATTACK STAGE");
            gm.setStageString("ATTACK STAGE");
            instructionLabel.setText("Select a region you want to attack from");
            gm.setInstruction("ATTACK STAGE");

            moveCom.setVisible(false);
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

            //if player is the last player in a turn update game logic
            if( gm.isLast(p) ){
                gm.operationsAfterLastPlayer(allRegion, gm.getTurnCount());
                gm.setTurnCount(gm.getTurnCount() + 1);
            }

            //the turns of the player changes
            gm.nextTurn();

            Player p2 = gm.getPlayers()[gm.getWhoseTurn()];

            //new player which has turn will be displayed
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
                        turn1.setVisible(true);
                    if( players[0].isEliminated())
                        elim1.setVisible(true);
                }
                else if( i == 1){
                    if( 1 == gm.getWhoseTurn())
                        turn2.setVisible(true);
                    if( players[1].isEliminated())
                        elim2.setVisible(true);
                }
                else if( i == 2){
                    if( 2 == gm.getWhoseTurn())
                        turn3.setVisible(true);
                    if( players[2].isEliminated())
                        elim3.setVisible(true);
                }
                else if( i == 3){
                    if( 3 == gm.getWhoseTurn())
                        turn3.setVisible(true);
                    if( players[3].isEliminated())
                        elim3.setVisible(true);
                }
            }

            gm.setStageString("BUY STAGE");
            stageLabel.setText("BUY STAGE");
            gm.setInstruction("Click on your avatar to buy or organize event");
            instructionLabel.setText("Click on your avatar to buy or organize event");

            //updatebuttons
            nextTurnPanel.setVisible(true);
            nextStage.setVisible(false);
            motivationButton.setVisible(false);
            climateButton.setVisible(false);
            pauseButton.setVisible(false);
        }
    }

    /**
     * This method listens for the actions on move commander button
     * When the move commander button is clicked instruction label
     * is updated as Select a region to place commander
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void moveComClicked( ActionEvent e)
    {
        nextStage.setVisible(false);
        moveCom.setVisible(false);
        gm.setInstruction("Select a region to place commander");
        instructionLabel.setText("Select a region to place commander");
        pauseButton.setVisible(false);
    }

    /**
     * This method listens for the actions on show motivation/show map button
     * When the show motivation button is clicked color of the regions updated
     * according to their motivation levels and buttons text is updated as show map
     * When the show map button is clicked, by calling show map method
     * map is updated such as regions will have their old view
     * This method is public as it is connected to fxml file
     * @param e
     */
    public void motivationClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if(((Button)(e.getSource())).getText().equals("Show Motivation"))
        {
            Region[] allregion = gm.getRegions();

            //update the view of the game according to motivation of troops
            for( int i = 0 ; i < allregion.length; i++ )
            {
                MotivationLevel ml= allregion[i].getMotivation();
                SVGPath svg = (SVGPath) sc.lookup("#svg" + i);

                if( ml == MotivationLevel.HIGH)
                    svg.setFill(Paint.valueOf( "#21b09f"));

                if( ml == MotivationLevel.NORMAL)
                    svg.setFill(Paint.valueOf( "#a4cf23"));

                if( ml == MotivationLevel.LOW)
                    svg.setFill(Paint.valueOf( "#eba234"));

                if( ml == MotivationLevel.NONE)
                    svg.setFill(Paint.valueOf("#eb4c34"));

                Label lbl = (Label) sc.lookup("#lbl" + i);
                String s = "" +allregion[i].getNumTroops();
                lbl.setText(s);
                lbl.setVisible(true);
            }

            updateInfoView(infoLabel4, info4, true, true,"Unmotivated", "#eb4c34");

            updateInfoView(infoLabel3, info3, true, true,"Low Motivated", "#eba234");

            updateInfoView(infoLabel2, info2, true, true,"Normal Motivated", "#a4cf23");

            updateInfoView(infoLabel1, info1, true, true,"High Motivated", "#21b09f");


            climateButton.setVisible(false);
            ((Button)(e.getSource())).setText("Show Map");
        }
        else {
            showMap(sc);

            updateInfoView(infoLabel4,info4,false,false,"","#eb4c34");

            updateInfoView(infoLabel3,info3,false,false,"","#eb4c34");

            updateInfoView(infoLabel2,info2,false,false,"","#eb4c34");

            updateInfoView(infoLabel1,info1,false,false,"","#eb4c34");

            climateButton.setVisible(true);
            ((Button)(e.getSource())).setText("Show Motivation");
        }
    }
}