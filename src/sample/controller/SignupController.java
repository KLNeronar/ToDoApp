/*
Manages the Sign Up scene/screen.
 */
package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signupFirstName;

    @FXML
    private TextField signupLastName;

    @FXML
    private TextField signupUsername;

    @FXML
    private TextField signupLocation;

    @FXML
    private CheckBox signupCheckBoxMale;

    @FXML
    private CheckBox signupCheckBoxFemale;

    @FXML
    private Button signupButton;

    @FXML
    private PasswordField signupPassword;

    @FXML
    private Label missingInfoLabel;

    @FXML
    private Label usernameTakenLabel;

    @FXML
    void initialize() {

        //Initializes the creation of a new user upon the click
        //of the button.
        signupButton.setOnAction(actionEvent -> {

            missingInfoLabel.setVisible(false);
            usernameTakenLabel.setVisible(false);

            createUser();

        });
    }

    //Creates the new user based on the information provided in each
    //data field.
    private void createUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        //Extract all info from every text box on the page
        String name = signupFirstName.getText();
        String lastName = signupLastName.getText();
        String userName = signupUsername.getText();
        String password = signupPassword.getText();
        String location = signupLocation.getText();

        String gender = "";

        //Get the selected gender category and store it.
        if(signupCheckBoxFemale.isSelected()) {
            gender = "Female";
        } else if (signupCheckBoxMale.isSelected()){
            gender = "Male";
        }

        //Check for validity of input
        if(name.isEmpty() || lastName.isEmpty() || userName.isEmpty()
                || password.isEmpty() || location.isEmpty() || gender.isEmpty()) {

            //Display a request to fill out every field.
            missingInfoLabel.setVisible(true);

        } else {

            //Create the new user
            User user = new User(name, lastName, userName, password, location, gender);

            //Check if the user with said userName is already in the system
            if (databaseHandler.usernameTaken(user)) {

                //Notify the user that such login already exists.
                usernameTakenLabel.setVisible(true);
            } else {

                //Enter the new user into the database.
                databaseHandler.signUpUser(user);

                //Get the current stage
                Stage signupStage = (Stage) signupButton.getScene().getWindow();

                //Close the current stage
                signupStage.close();
            }
        }

    }

}
