package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    void initialize() {

        String loginText = loginUsername.getText().trim();
        String loginPwd = loginPassword.getText().trim();

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

        loginButton.setOnAction(actionEvent -> {

            if (!loginText.equals("") || !loginPwd.equals("")) {
                loginUser(loginText, loginPwd);
            } else {
                System.out.println("Error login in user!");
            }
        });
    }

    private void loginUser(String userName, String password) {

        //Check in the database if the user exists
        //If true, we take them to AddItem Screen
    }

}
