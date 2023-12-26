package com.example.island_algorithm_visualiser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane pane;
    @FXML
    private AnchorPane button_pane;
    @FXML
    private AnchorPane text_pane;
    @FXML
    private Button start_button;
    @FXML
    private Button stop_button;
    @FXML
    private Label island_counter_label;
    @FXML
    private Label cells_visited_label;
    @FXML
    private CheckBox DFS_CheckBox;
    @FXML
    private CheckBox BFS_CheckBox;

    private Grid grid;
    private int gridSize = 30;
    private int[][] values;
    private boolean[][] visited;
    private GridHandler gridHandler;
    private Statistics statistics;

    @FXML
    void startButtonClick(MouseEvent event) {
        if(!gridHandler.isVisualizationRunning() && (DFS_CheckBox.isSelected() || BFS_CheckBox.isSelected())){
            gridHandler.visualize();
        }
    }
    @FXML
    void stopButtonClick(MouseEvent event){
        gridHandler.stopVisualisation();
        gridHandler.resetVisited();
        gridHandler.resetGrid();
        statistics.resetStats();
    }

    @FXML
    void generateNewButtonClick(MouseEvent event) {
        if(gridHandler.isVisualizationRunning()){
            gridHandler.stopVisualisation();
        }
        statistics = new Statistics(pane, island_counter_label, cells_visited_label);
        values = new int[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        visited = new boolean[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);
        gridHandler.initializeGrid();
        statistics.resetStats();
    }

    @FXML
    public void DFS_CheckBoxSelected(MouseEvent event){
        gridHandler.setDFS_Selected(true);
        gridHandler.setBFS_Selected(false);
        BFS_CheckBox.setSelected(false);
    }
    @FXML
    public void BFS_CheckBoxSelected(MouseEvent event){
        gridHandler.setBFS_Selected(true);
        gridHandler.setDFS_Selected(false);
        DFS_CheckBox.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statistics = new Statistics(text_pane, island_counter_label, cells_visited_label);
        values = new int[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        visited = new boolean[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);

        gridHandler.initializeGrid();
    }
}