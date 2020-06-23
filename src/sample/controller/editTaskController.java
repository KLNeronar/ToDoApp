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

        taskBox.setText(task.getTask());
        taskDescriptionBox.setText(task.getDescription());

        deleteTaskButton.setOnAction(actionEvent -> {
            databaseHandler.deleteTask(task);

            Stage stage = (Stage) deleteTaskButton.getScene().getWindow();

            stage.close();
        });

    }
}