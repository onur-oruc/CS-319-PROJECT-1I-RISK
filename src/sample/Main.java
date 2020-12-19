package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("RiskGame");
        Parent root = FXMLLoader.load(getClass().getResource("ControllerClasses/MainMenu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.initStyle( StageStyle.UNDECORATED);
        primaryStage.show();
    }
    public static void main( String[] args)
    {
        launch(args);
    }



}