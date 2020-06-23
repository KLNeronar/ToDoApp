package sample.controller;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.model.User;

public class AddItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView addButton;

    @FXML
    private ListView<String> taskList;

    private DatabaseHandler databaseHandler;

    private User loggedUser;

    private ArrayList<Task> tasks;

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();
        loggedUser = LoginController.user;

        updateGUI();

        taskList.setOnMouseClicked(event -> {
            int selectedItem = taskList.getSelectionModel().getSelectedIndex();

            if ((event.getClickCount() == 2) && (selectedItem != -1)
                    && (tasks.size() > 0)) {

                //Save the task that is being edited
                editTaskController.task = tasks.get(selectedItem);

                //Set up the new stage that will display the popup window for editing and
                //deleting tasks.
                Stage taskStage = new Stage();
                //The primary stage cannot be accessed while task stage is active
                taskStage.initModality(Modality.APPLICATION_MODAL);
                taskStage.setTitle("TODO Task");

                try {
                    //Load the scene for the task stage.
                    Parent editTaskRoot = FXMLLoader.load(getClass().getResource("/sample/view/editTask.fxml"));

                    taskStage.setScene(new Scene(editTaskRoot, 500, 300));
                    //Program waits until the task stage is exited
                    taskStage.showAndWait();
                    updateGUI();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            Shaker addButtonShaker = new Shaker(addButton);
            addButtonShaker.shake();

            FadeTransition fadeAddButton = new FadeTransition(Duration.millis(2000), addButton);

            System.out.println("Added Clicked!");

            //addButton.relocate(0,20);
            //addButton.setOpacity(0);

            fadeAddButton.setFromValue(1f);
            fadeAddButton.setToValue(0f);
            fadeAddButton.setCycleCount(1);
            fadeAddButton.setAutoReverse(false);
            fadeAddButton.play();

            try {
                AnchorPane formPane = FXMLLoader.load(getClass().getResource("/sample/view/addItemForm.fxml"));

                FadeTransition fadeFormPane = new FadeTransition(Duration.millis(2000), formPane);

                fadeFormPane.setFromValue(0f);
                fadeFormPane.setToValue(1f);
                fadeFormPane.setCycleCount(1);
                fadeFormPane.setAutoReverse(false);
                fadeFormPane.play();

                rootAnchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    //Updates the information displayed on the task list window
    private void updateGUI() {
        if (loggedUser.isInDatabase()) {
            tasks = databaseHandler.getUserTasks(loggedUser);
        } else {
            System.out.println("Wrong User!");
        }

        //Display every task the user has in his to do list
        setTaskList();
    }

    //Retrieves task names from the list of tasks the user has
    private void setTaskList() {

        ObservableList<String> list = FXCollections.observableArrayList("No Tasks");

        if(tasks.size() > 0) {
            list.remove(0);

            for(Task task : tasks) {
                list.add(task.getTask());
            }
        }

        taskList.setItems(list);
    }
}

