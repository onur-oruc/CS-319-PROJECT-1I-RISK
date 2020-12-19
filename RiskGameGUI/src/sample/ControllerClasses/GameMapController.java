package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class GameMapController {
    MediaPlayer music;
    String s;
    @FXML
    AnchorPane gamerPanel, dicePanel, nextTurnPanel;
    @FXML
    AnchorPane buyPanel;
    @FXML
    Stage stage;
    @FXML
    Label instructionLabel;
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
    Button eventButton, attackButton1, attackButton2, attackButton3, nextStage, exitTurn;
    @FXML
    Button attackExit, buyExit, distribute, climateButton, motivationButton;
    @FXML
    ImageView playerImg1, playerImg2,playerImg3, playerImg4;
    @FXML
    Label infoLabel1, infoLabel2,infoLabel3,infoLabel4;
    @FXML
    Rectangle info1, info2, info3, info4;

    public void distributeClicked( ActionEvent e)
    {
        instructionLabel.setText("Click on your avatar to buy or organize event");
        stageLabel.setText("BUY STAGE");
        distribute.setVisible(false);
        nextStage.setVisible(true);
        motivationButton.setVisible(true);
        climateButton.setVisible(true);
        eventButton.setVisible(true);
        playerImg1.setDisable(false);
        playerImg2.setDisable(false);
        playerImg3.setDisable(false);
        playerImg4.setDisable(false);

    }

    public void onRegionClicked( MouseEvent event) throws IOException {
        if( instructionLabel.getText().equals("Select a region to organize event"))
        {
            instructionLabel.setText("Click on your avatar to buy or organize event");
            nextStage.setVisible(true);
            eventButton.setVisible(true);
            playerImg1.setDisable(false);
            playerImg2.setDisable(false);
            playerImg3.setDisable(false);
            playerImg4.setDisable(false);
        }
        else if( instructionLabel.getText().equals("Select a region to place commander"))
        {
            instructionLabel.setText("Select a region you want to place troops");
            nextStage.setVisible(true);
            moveCom.setVisible(true);
        }
        else if( instructionLabel.getText().equals("Select a region you want to place troops"))
        {
            instructionLabel.setText("Select the number of troops you want to place");
            troopImage.setVisible(true);
            troopLabel.setVisible(true);
            troopNo.setVisible(true);
            done.setVisible(true);
            nextStage.setVisible(false);
            moveCom.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to attack from"))
        {
            troopImage.setVisible(true);
            troopLabel.setVisible(true);
            troopNo.setVisible(true);
            done.setVisible(true);
            instructionLabel.setText("Select the number of troops to attack");
            nextStage.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to attack "))
        {
            instructionLabel.setText("Select a region you want to attack from");
            dicePanel.setVisible(true);
            motivationButton.setVisible(false);
            climateButton.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to get troop"))
        {
            troopImage.setVisible(true);
            troopLabel.setVisible(true);
            troopNo.setVisible(true);
            done.setVisible(true);
            instructionLabel.setText("Select the number of troops to get");
            nextStage.setVisible(false);
        }
        else if( instructionLabel.getText().equals("Select a region you want to add troop"))
        {
            instructionLabel.setText("Select a region you want to get troop");
            nextStage.setVisible(true);
        }
    }
    public void attackClicked( ActionEvent e)
    {
        if( e.getSource() == attackButton1)
        {
            for( int i = 1; i < 6; i++)
            {
                Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene sc = window.getScene();
                ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            for( int i = 1; i < 6; i++)
            {
                if( i == 1 || i == 4 || i ==5 )
                {
                    Random r = new Random();
                    int numberShowing = r.nextInt(6) + 1;
                    File file = new File("src/sample/Images/dice" + numberShowing + ".jpeg");
                    Image image = new Image(file.toURI().toString());
                    Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene sc = window.getScene();
                    System.out.println(image.getUrl());
                    ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                    diceImage.setImage(image);
                    diceImage.setVisible(true);
                }
            }
        }
        else if( e.getSource() == attackButton2 )
        {
            for( int i = 1; i < 6; i++)
            {
                Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene sc = window.getScene();
                ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            for( int i = 1; i < 6; i++)
            {
                if( i == 1 || i == 2 || i == 4 || i ==5 )
                {
                    Random r = new Random();
                    int numberShowing = r.nextInt(6) + 1;
                    File file = new File("src/sample/Images/dice" + numberShowing + ".jpeg");
                    Image image = new Image(file.toURI().toString());
                    Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene sc = window.getScene();
                    ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                    diceImage.setImage(image);
                    diceImage.setVisible(true);
                }
            }
        }

        else if( e.getSource() == attackButton3 )
        {
            for( int i = 1; i < 6; i++)
            {
                Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                Scene sc = window.getScene();
                ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
            for( int i = 1; i < 6; i++)
            {
                if( i == 1 || i== 2 || i == 3 || i == 4 || i ==5 )
                {
                    Random r = new Random();
                    int numberShowing = r.nextInt(6) + 1;
                    File file = new File("src/sample/Images/dice" + numberShowing + ".jpeg");
                    Image image = new Image(file.toURI().toString());
                    Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
                    Scene sc = window.getScene();
                    ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                    diceImage.setImage(image);
                    diceImage.setVisible(true);
                }
            }
        }

    }

    public void onRegionEntered( MouseEvent event) throws IOException {

    }

    public void onRegionExited( MouseEvent event) throws IOException {

    }

    public void doneClicked( ActionEvent e)
    {
        if( instructionLabel.getText().equals("Select the number of troops to attack"))
        {
            troopImage.setVisible(false);
            troopLabel.setVisible(false);
            troopNo.setVisible(false);
            done.setVisible(false);
            instructionLabel.setText("Select a region you want to attack ");
        }
        else if( instructionLabel.getText().equals("Select the number of troops you want to place"))
        {
            troopImage.setVisible(false);
            troopLabel.setVisible(false);
            troopNo.setVisible(false);
            done.setVisible(false);
            instructionLabel.setText("Select a region you want to place troops");
            nextStage.setVisible(true);
            moveCom.setVisible(true);
        }
        else if( instructionLabel.getText().equals("Select the number of troops to get"))
        {
            troopImage.setVisible(false);
            troopLabel.setVisible(false);
            troopNo.setVisible(false);
            done.setVisible(false);
            instructionLabel.setText("Select a region you want to add troop");
        }
    }

    public void initialize( String g, String[] images, MediaPlayer m) throws IOException {
        //stg = (Stage)anc.getScene().getWindow();
        // String d = (String)stg.getUserData();
        //System.out.println(d);
        //svg1.setFill(Paint.valueOf("#c67b7b"));
        music = m;
        s = g;
        System.out.println(g);
        String[] im = images;

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
        //System.out.println(d);
        foo.setFill(Paint.valueOf("#c67b7b"));*/
    }

    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Loby.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }

    public void playerProfileClicked( MouseEvent e)
    {
        buyPanel.setVisible(true);
        nextStage.setVisible(false);
        eventButton.setVisible(false);
        motivationButton.setVisible(false);
        climateButton.setVisible(false);
    }
    public void organizeEvent( ActionEvent e)
    {
        instructionLabel.setText("Select a region to organize event");
        playerImg1.setDisable(true);
        playerImg2.setDisable(true);
        playerImg3.setDisable(true);
        playerImg4.setDisable(true);
        nextStage.setVisible(false);
        eventButton.setVisible(false);
    }

    public void buyProfileExit( ActionEvent e)
    {
        Button b = (Button)e.getSource();
        if( b == buyExit)
        {
            buyPanel.setVisible(false);
            nextStage.setVisible(true);
            eventButton.setVisible(true);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);
        }

        else if( b == attackExit)
        {
            nextStage.setVisible(true);
            dicePanel.setVisible(false);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);
            Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
            Scene sc = window.getScene();
            for( int i = 1 ; i < 6; i++ )
            {
                ImageView diceImage = (ImageView) sc.lookup("#dice" + i);
                diceImage.setVisible(false);
            }
        }
        else if( b == exitTurn)
        {
            nextStage.setVisible(false);
            eventButton.setVisible(true);
            playerImg1.setDisable(false);
            playerImg2.setDisable(false);
            playerImg3.setDisable(false);
            playerImg4.setDisable(false);
            nextStage.setVisible(true);
            nextTurnPanel.setVisible(false);
            motivationButton.setVisible(true);
            climateButton.setVisible(true);
        }
    }

    public void showClimate( MouseEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if(((Button)(e.getSource())).getText().equals("Show Climate"))
        {
            for( int i= 0; i < 42 ; i++) {
                String svgname = "#svg" + i;
                String labelname = "#lbl" + i;
                String img11 = "#com" + i;
                String img22 = "#card" + i;
                String img33 = "#gold" + i;
                SVGPath foo = (SVGPath) sc.lookup(svgname);
                Label lbl = (Label) sc.lookup(labelname);
                ImageView img1 = (ImageView) sc.lookup(img11);
                ImageView img2 = (ImageView) sc.lookup(img22);
                ImageView img3 = (ImageView) sc.lookup(img33);

                if (foo != null)
                    foo.setFill(Paint.valueOf("#c67b7b"));
                if (lbl != null)
                    lbl.setText("22");
                if (img1 != null)
                    img1.setVisible(false);
                if (img2 != null)
                    img2.setVisible(false);
                if (img3 != null)
                    img3.setVisible(false);
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
            for( int i= 0; i < 42 ; i++) {
                String name = "#svg" + i;
                SVGPath foo = (SVGPath) sc.lookup(name);
                String img11 = "#com" + i;
                String img22 = "#card" + i;
                String img33 = "#gold" + i;
                ImageView img1 = (ImageView) sc.lookup(img11);
                ImageView img2 = (ImageView) sc.lookup(img22);
                ImageView img3 = (ImageView) sc.lookup(img33);
                if (foo != null)
                    foo.setFill(Paint.valueOf("#f55142"));
                if (img1 != null)
                    img1.setVisible(true);
                if (img2 != null)
                    img2.setVisible(true);
                if (img3 != null)
                    img3.setVisible(true);
            }
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
        if( stageLabel.getText().equals("BUY STAGE"))
        {
            eventButton.setVisible(false);
            stageLabel.setText("DRAFT STAGE");
            instructionLabel.setText("Select a region you want to place troops");
            moveCom.setVisible(true);
            eventButton.setVisible(false);
            playerImg1.setDisable(true);
            playerImg2.setDisable(true);
            playerImg3.setDisable(true);
            playerImg4.setDisable(true);
        }
        else if( stageLabel.getText().equals("DRAFT STAGE") )
        {
            moveCom.setVisible(false);
            stageLabel.setText("ATTACK STAGE");
            instructionLabel.setText("Select a region you want to attack from");
        }
        else if( stageLabel.getText().equals("ATTACK STAGE") )
        {
            stageLabel.setText("FORTIFY STAGE");
            instructionLabel.setText("Select a region you want to get troop");
        }
        else if( stageLabel.getText().equals("FORTIFY STAGE") )
        {
            stageLabel.setText("BUY STAGE");
            instructionLabel.setText("Click on your avatar to buy or organize event");
            nextTurnPanel.setVisible(true);
            nextStage.setVisible(false);
            motivationButton.setVisible(false);
            climateButton.setVisible(false);
        }
    }

    public void moveComClicked( ActionEvent e)
    {
        nextStage.setVisible(false);
        moveCom.setVisible(false);
        instructionLabel.setText("Select a region to place commander");
    }

    public void motivationClicked( ActionEvent e)
    {
        Stage window = (Stage) ((Node)e.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        if(((Button)(e.getSource())).getText().equals("Show Motivation"))
        {
            for( int i= 0; i < 42 ; i++) {
                String svgname = "#svg" + i;
                String labelname = "#lbl" + i;
                String img11 = "#com" + i;
                String img22 = "#card" + i;
                String img33 = "#gold" + i;
                SVGPath foo = (SVGPath) sc.lookup(svgname);
                Label lbl = (Label) sc.lookup(labelname);
                ImageView img1 = (ImageView) sc.lookup(img11);
                ImageView img2 = (ImageView) sc.lookup(img22);
                ImageView img3 = (ImageView) sc.lookup(img33);

                if (foo != null)
                    foo.setFill(Paint.valueOf("#a8a232"));
                if (lbl != null)
                    lbl.setVisible(false);
                if (img1 != null)
                    img1.setVisible(false);
                if (img2 != null)
                    img2.setVisible(false);
                if (img3 != null)
                    img3.setVisible(false);
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
            for( int i= 0; i < 42 ; i++) {
                String name = "#svg" + i;
                SVGPath foo = (SVGPath) sc.lookup(name);
                String img11 = "#com" + i;
                String img22 = "#card" + i;
                String img33 = "#gold" + i;
                String lbl44 = "#lbl" + i;
                ImageView img1 = (ImageView) sc.lookup(img11);
                ImageView img2 = (ImageView) sc.lookup(img22);
                ImageView img3 = (ImageView) sc.lookup(img33);
                Label lbl = (Label) sc.lookup(lbl44);
                lbl.setText("22");
                lbl.setVisible(true);
                if (foo != null)
                    foo.setFill(Paint.valueOf("#f55142"));
                if (img1 != null)
                    img1.setVisible(true);
                if (img2 != null)
                    img2.setVisible(true);
                if (img3 != null)
                    img3.setVisible(true);
            }
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