/*
Manages the first scene that will be displayed when the app is started.
 */
package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginUsername;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginSingupButton;

    //Sets up the Object that will interact with the database
    private DatabaseHandler databaseHandler;

    //Sets up the User Object that is currently using the app.
    //This Object will be accessed by every other scene to make sure
    //they are accessing the right data.
    protected static User user;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        //Handling Sing Up Button
        loginSingupButton.setOnAction(actionEvent -> {

            //Take the user to signup screen

            //Get the current stage and hide it
            Stage loginStage = (Stage) loginSingupButton.getScene().getWindow();
            loginStage.hide();
            //Get the location of the signup page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));

            //Try to load that location
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Get root of the page that we are loading
            Parent root = loader.getRoot();
            //Set that page as the new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            //Display the stage and wait till it is closed.
            stage.showAndWait();

            //Show the current stage after the new one was closed
            loginStage.show();

        });

        //Handling Login Button
        loginButton.setOnAction(actionEvent -> {

            //Retrieve entered username and password data from appropriate
            //entry fields.
            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            //Create a new User object containing the username and password
            user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            //Get the logged in user info from the database.
            user = databaseHandler.getUser(user);

            //If the retrieved user is in the database, take the user to
            //the screen that will contain the task list.
            //Otherwise, notify the user that the info he/she entered is
            //incorrect.
            if (user.isInDatabase()) {
                showAddItemScreen();
            } else {
                Shaker usernameShaker = new Shaker(loginUsername);
                Shaker passwordShaker = new Shaker(loginPassword);
                usernameShaker.shake();
                passwordShaker.shake();
            }
        });
    }

    //Displays the scene in which the table of user tasks is displayed
    private void showAddItemScreen() {

        //Take users to add item screen
        loginSingupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/addItem.fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
