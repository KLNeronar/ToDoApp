/*
An App starter. Initiates the primary stage of the app and
calls the first scene to be displayed on the stage.
 */
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Get the scene to be displayed on the primary stage
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));

        //Set up the primary stage and make it display the first scene
        primaryStage.setTitle("TODO");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    //Launches the app
    public static void main(String[] args) {
        launch(args);
    }
}
