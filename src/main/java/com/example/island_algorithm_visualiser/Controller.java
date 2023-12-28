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
    private Button generate_new_button;
    @FXML
    private Label island_counter_label;
    @FXML
    private Label cells_visited_label;
    @FXML
    private Label island_area_Label;
    @FXML
    private Label water_area_label;
    @FXML
    private Label max_island_perimeter_label;
    @FXML
    private Label invalid_selection_message;
    @FXML
    private CheckBox DFS_CheckBox;
    @FXML
    private CheckBox BFS_CheckBox;
    @FXML
    private CheckBox show_perimeter_checkbox;

    private Grid grid;
    private int gridSize = 30;
    private int[][] values;
    private boolean[][] visited;
    private GridHandler gridHandler;
    private Statistics statistics;

    @FXML
    void startButtonClick(MouseEvent event) {
        gridHandler.setShowPerimeterSelected(show_perimeter_checkbox.isSelected());
        gridHandler.setBFS_Selected(BFS_CheckBox.isSelected());
        gridHandler.setDFS_Selected(DFS_CheckBox.isSelected());
        invalid_selection_message.setVisible(!BFS_CheckBox.isSelected() && !DFS_CheckBox.isSelected());
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
        statistics = new Statistics(pane, island_counter_label, cells_visited_label, island_area_Label, water_area_label, max_island_perimeter_label);
        values = new int[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        visited = new boolean[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);
        gridHandler.initializeGrid();
        statistics.resetStats();
    }

    @FXML
    public void DFS_CheckBoxSelected(MouseEvent event){
        BFS_CheckBox.setSelected(false);
    }
    @FXML
    public void BFS_CheckBoxSelected(MouseEvent event){
        DFS_CheckBox.setSelected(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statistics = new Statistics(text_pane, island_counter_label, cells_visited_label, island_area_Label, water_area_label, max_island_perimeter_label);
        values = new int[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        visited = new boolean[(int) pane.getPrefHeight() / gridSize][(int) pane.getPrefWidth() / gridSize];
        grid = new Grid(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);
        gridHandler = new GridHandler(pane.getPrefWidth(), pane.getPrefHeight(), gridSize, pane, values, visited, statistics);

        gridHandler.initializeGrid();
    }
}