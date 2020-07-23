/*
Manages the New Task Form page
 */
package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.model.User;

public class addItemFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane leafAnchorPane;

    @FXML
    private TextField taskField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Button saveTaskButton;

    private DatabaseHandler databaseHandler;

    private User loggedUser;

    @FXML
    void initialize() {

        //Adds the new task, based on filled out text fields.
        saveTaskButton.setOnAction(actionEvent -> {

            //Get current user info
            loggedUser = LoginController.user;

            //If the user doesn't contain username and password, return error message.
            if(loggedUser.getUserName().isEmpty() || loggedUser.getPassword().isEmpty()) {
                System.out.println("No user is logged in");
                //return;
            }

            //Establish connection with the database.
            databaseHandler = new DatabaseHandler();

            //Get the information entered into the text fields in the page.
            String taskName = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();
            Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

            //Create a new Task Object based on entered info
            Task task = new Task(dateCreated, taskDescription, taskName);

            //Check if the user is registered.
            loggedUser = databaseHandler.getUser(loggedUser);

            if (loggedUser.isInDatabase()) {
                databaseHandler.addTask(task, loggedUser);
            } else {
                System.out.println("User not registered");
            }

            //Send user to the tasks table page.
            try {
                AnchorPane formPane = FXMLLoader.load(getClass().getResource("/sample/view/addItem.fxml"));

                FadeTransition fadeFormPane = new FadeTransition(Duration.millis(2000), formPane);

                fadeFormPane.setFromValue(0f);
                fadeFormPane.setToValue(1f);
                fadeFormPane.setCycleCount(1);
                fadeFormPane.setAutoReverse(false);
                fadeFormPane.play();

                leafAnchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}

