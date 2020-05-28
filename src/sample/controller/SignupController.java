package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.database.DatabaseHandler;
import sample.model.User;

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
    void initialize() {

        signupButton.setOnAction(actionEvent -> {

            createUser();
        });
    }

    private void createUser() {

        DatabaseHandler databaseHandler = new DatabaseHandler();

        String name = signupFirstName.getText();
        String lastName = signupLastName.getText();
        String userName = signupUsername.getText();
        String password = signupPassword.getText();
        String location = signupLocation.getText();

        String gender = "";

        if(signupCheckBoxFemale.isSelected()) {
            gender = "Female";
        } else {
            gender = "Male";
        }

        User user = new User(name, lastName, userName, password, location, gender);

        databaseHandler.signUpUser(user);
    }

}
