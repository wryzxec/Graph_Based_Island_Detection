package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private int gridSize = 30;
    private int[][] values = new int[gridSize][gridSize];
    private GridHandler gridHandler;
    private Grid grid;

    @FXML
    private AnchorPane pane;
    @FXML
    private Button button1;

    // Constructs a timeline of keyframes, which form an animation.
    @FXML
    void visualize(MouseEvent event) {

        Timeline timeline = new Timeline();
        int duration = 25;

        // each keyframe is offset from t = 0, by an increasing factor 'i'.
        for (int i = 0; i < grid.getTileAmount(); i++) {

            final int x = i % grid.getTilesAcross();
            final int y = i / grid.getTilesAcross();
            KeyFrame keyFrame = new KeyFrame(Duration.millis(duration * i), e -> {
                gridHandler.compute(x,y);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values);
        gridHandler.initializeGrid();
    }
}