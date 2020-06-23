package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.model.User;

import javax.xml.transform.Result;

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
    private TextField descriptionField;

    @FXML
    private Button saveTaskButton;

    private DatabaseHandler databaseHandler;

    private User loggedUser;

    @FXML
    void initialize() {

        saveTaskButton.setOnAction(actionEvent -> {

            loggedUser = LoginController.user;

            if(loggedUser.getUserName().isEmpty() || loggedUser.getPassword().isEmpty()) {
                System.out.println("No user is logged in");
                //return;
            }

            databaseHandler = new DatabaseHandler();

            String taskName = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();
            Timestamp dateCreated = new Timestamp(Calendar.getInstance().getTimeInMillis());

            Task task = new Task(dateCreated, taskDescription, taskName);

            loggedUser = databaseHandler.getUser(loggedUser);

            if (loggedUser.isInDatabase()) {
                databaseHandler.addTask(task, loggedUser);
            } else {
                System.out.println("User not registered");
            }

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

