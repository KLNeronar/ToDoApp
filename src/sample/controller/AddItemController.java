package sample.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sample.animations.Shaker;

public class AddItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label noTaskLabel;

    @FXML
    private ImageView addButton;

    @FXML
    void initialize() {

        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            Shaker addButtonShaker = new Shaker(addButton);
            addButtonShaker.shake();

            FadeTransition fadeAddButton = new FadeTransition(Duration.millis(2000), addButton);
            FadeTransition fadeNoTaskLabel = new FadeTransition(Duration.millis(2000), noTaskLabel);

            System.out.println("Added Clicked!");

            //addButton.relocate(0,20);
            //noTaskLabel.relocate(0,45);
            //addButton.setOpacity(0);
            //noTaskLabel.setOpacity(0);

            fadeAddButton.setFromValue(1f);
            fadeAddButton.setToValue(0f);
            fadeAddButton.setCycleCount(1);
            fadeAddButton.setAutoReverse(false);
            fadeAddButton.play();

            fadeNoTaskLabel.setFromValue(1f);
            fadeNoTaskLabel.setToValue(0f);
            fadeNoTaskLabel.setCycleCount(1);
            fadeNoTaskLabel.setAutoReverse(false);
            fadeNoTaskLabel.play();

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
}

