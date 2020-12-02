package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.io.IOException;


public class GameMapController {

    @FXML
    Label lbl1;
    @FXML
    SVGPath svg1;
    @FXML
    AnchorPane anc;
    String s;

    public void mouseClicked( MouseEvent event) throws IOException {

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene sc = window.getScene();
        SVGPath foo = (SVGPath) sc.lookup("#svg1");
        Label foo1 = (Label)sc.lookup("#lbl1");
        foo.setFill(Paint.valueOf("#000000"));
        System.out.println(foo.getId());
    }

    public void initialize( String g)
    {
        //stg = (Stage)anc.getScene().getWindow();
        // String d = (String)stg.getUserData();
        //System.out.println(d);
        //svg1.setFill(Paint.valueOf("#c67b7b"));
        s = g;
        System.out.println(g);
    }

    /*public void initObje( String g)
    {
        s = g;
    }*/

    public void mouseEntered(MouseEvent event) throws IOException
    {
        SVGPath sv = (SVGPath) event.getSource();
        sv.setFill(Paint.valueOf("#c67b7b"));
        sv.setCursor( Cursor.cursor("HAND"));
    }
    public void mouseExited(MouseEvent event) throws IOException
    {
        SVGPath sv = (SVGPath) event.getSource();
        sv.setFill(Paint.valueOf("#000000"));
    }

    public void onBackClicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Loby.fxml"));
        Scene ng =  new Scene(root);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ng);
        window.show();
    }
}
