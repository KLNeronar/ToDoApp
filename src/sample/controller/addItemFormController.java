package sample.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

            ResultSet userRow = databaseHandler.getUser(loggedUser);

            int counter = 0;

            Integer userID = 0;

            try {
                while(userRow.next()) {
                    counter++;

                    String name = userRow.getString("firstname");

                    userID = userRow.getInt("userid");

                    System.out.println("Welcome! " + name);
                }

                if (counter ==1) {
                    databaseHandler.addTask(task, userID);
                } else {
                    System.out.println("User not registered");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        });
    }
}

