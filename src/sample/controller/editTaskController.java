/*
Manages the popup window that allows the user to manage a task
in the task table.
 */
package sample.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

public class editTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField taskBox;

    @FXML
    private TextArea taskDescriptionBox;

    @FXML
    private Button updateTaskButton;

    @FXML
    private Button deleteTaskButton;

    protected static Task task;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();

        //Set up the text boxes in the scene to display the task name
        //and its description.
        taskBox.setText(task.getTask());
        taskDescriptionBox.setText(task.getDescription());

        //Delete the managed task.
        deleteTaskButton.setOnAction(actionEvent -> {
            //Delete selected task from the database.
            databaseHandler.deleteTask(task);

            //Close the popup window.
            Stage stage = (Stage) deleteTaskButton.getScene().getWindow();

            stage.close();
        });

        //Update the managed task
        updateTaskButton.setOnAction(actionEvent -> {

            //Get the updated text fields
            task.setTask(taskBox.getText());
            task.setDescription(taskDescriptionBox.getText());

            //Update the app database
            databaseHandler.updateTask(task);

            //Close the popup window.
            Stage stage = (Stage) updateTaskButton.getScene().getWindow();

            stage.close();
        });

    }
}