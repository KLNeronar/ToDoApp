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
import java.sql.ResultSet;
import java.sql.SQLException;
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

    private DatabaseHandler databaseHandler;

    protected static User user;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        //Handling Sing Up Button
        loginSingupButton.setOnAction(actionEvent -> {

            //Take users to signup screen
            loginSingupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/signup.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

        });

        //Handling Login Button
        loginButton.setOnAction(actionEvent -> {

            String loginText = loginUsername.getText().trim();
            String loginPwd = loginPassword.getText().trim();

            user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPwd);

            user = databaseHandler.getUser(user);

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
