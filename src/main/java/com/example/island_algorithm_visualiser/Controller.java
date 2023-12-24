package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private Button start_button;
    @FXML
    private Button stop_button;
    @FXML
    private Label island_counter;

    private Grid grid;
    private int gridSize = 30;
    private int[][] values;
    private boolean[][] visited;
    private GridHandler gridHandler;

    @FXML
    void startButtonClick(MouseEvent event) {
        if(!gridHandler.isVisualizationRunning()){
            gridHandler.visualize();
            island_counter.setText("Number of Islands: " + Integer.toString(gridHandler.getIslandCount()));
        }
    }
    @FXML
    void StopButtonClick(MouseEvent event){
        gridHandler.stopVisualisation();
        gridHandler.resetVisited();
        gridHandler.resetGrid();
        gridHandler.resetIslandCount();
    }

    @FXML
    void generateNewButtonClick(MouseEvent event) {
        if(gridHandler.isVisualizationRunning()){
            gridHandler.stopVisualisation();
        }
        values = new int[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        visited = new boolean[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited);
        gridHandler.initializeGrid();
        gridHandler.resetIslandCount();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        values = new int[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        visited = new boolean[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited);
        gridHandler.initializeGrid();
    }
}