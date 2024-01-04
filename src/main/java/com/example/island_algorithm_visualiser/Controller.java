    package com.example.island_algorithm_visualiser;

    import javafx.beans.NamedArg;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.Node;
    import javafx.scene.control.*;
    import javafx.scene.input.MouseEvent;
    import javafx.scene.layout.AnchorPane;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.shape.Rectangle;

    import java.net.URL;
    import java.util.ResourceBundle;

    public class Controller implements Initializable {

        @FXML
        private BorderPane bg_pane;
        @FXML
        private AnchorPane grid_pane;
        @FXML
        private AnchorPane button_pane;
        @FXML
        private AnchorPane text_pane;
        @FXML
        private Button start_button;
        @FXML
        private Button reset_button;
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
        private CheckBox speed_checkbox_1x;
        @FXML
        private CheckBox speed_checkbox_2x;
        @FXML
        private CheckBox speed_checkbox_4x;
        @FXML
        private CheckBox show_perimeter_checkbox;
        @FXML
        private Slider cell_size_slider;

        private Grid grid;
        private int gridSize;
        private int[][] values;
        private boolean[][] visited;
        private GridHandler gridHandler;
        private Statistics statistics;

        public void enableSettingsChanges(){
            DFS_CheckBox.setDisable(false);
            BFS_CheckBox.setDisable(false);
            speed_checkbox_1x.setDisable(false);
            speed_checkbox_2x.setDisable(false);
            speed_checkbox_4x.setDisable(false);
            show_perimeter_checkbox.setDisable(false);
            cell_size_slider.setDisable(false);
        }

        public void disableSettingsChanges(){
            DFS_CheckBox.setDisable(true);
            BFS_CheckBox.setDisable(true);
            speed_checkbox_1x.setDisable(true);
            speed_checkbox_2x.setDisable(true);
            speed_checkbox_4x.setDisable(true);
            show_perimeter_checkbox.setDisable(true);
            cell_size_slider.setDisable(true);
        }

        @FXML
        void startButtonClick(MouseEvent event) {
            disableSettingsChanges();
            gridHandler.setShowPerimeterSelected(show_perimeter_checkbox.isSelected());
            gridHandler.setBFS_Selected(BFS_CheckBox.isSelected());
            gridHandler.setDFS_Selected(DFS_CheckBox.isSelected());
            gridHandler.setDuration1xSelected(speed_checkbox_1x.isSelected());
            gridHandler.setDuration2xSelected(speed_checkbox_2x.isSelected());
            gridHandler.setDuration4xSelected(speed_checkbox_4x.isSelected());
            invalid_selection_message.setVisible(!BFS_CheckBox.isSelected() && !DFS_CheckBox.isSelected());
            if(!gridHandler.isVisualizationRunning()){
                gridHandler.visualize();
            }
        }
        @FXML
        void resetButtonClick(MouseEvent event){
            if(gridHandler.isVisualizationRunning()){
                gridHandler.stopVisualisation();
            }
            enableSettingsChanges();
            gridHandler.resetGrid();
            gridHandler.resetVisited();
            gridHandler.resetGrid();
            statistics.resetStats();
        }

        @FXML
        void generateNewButtonClick(MouseEvent event) {
            enableSettingsChanges();
            gridSize = (int) cell_size_slider.getValue();
            if(gridHandler.isVisualizationRunning()){
                gridHandler.stopVisualisation();
            }
            statistics.resetStats();
            grid_pane.getChildren().clear();
            values = new int[(int) grid_pane.getPrefHeight() / gridSize][(int) grid_pane.getPrefWidth() / gridSize];
            visited = new boolean[(int) grid_pane.getPrefHeight() / gridSize][(int) grid_pane.getPrefWidth() / gridSize];
            gridHandler = new GridHandler(grid_pane.getPrefWidth(), grid_pane.getPrefHeight(), gridSize, grid_pane, values, visited, statistics);
            gridHandler.initializeGrid();
        }

        @FXML
        public void DFS_CheckBoxSelected(MouseEvent event){
            BFS_CheckBox.setSelected(false);
        }
        @FXML
        public void BFS_CheckBoxSelected(MouseEvent event){
            DFS_CheckBox.setSelected(false);
        }
        @FXML
        public void speed1xSelected(MouseEvent event){
            speed_checkbox_2x.setSelected(false);
            speed_checkbox_4x.setSelected(false);
        }
        @FXML
        public void speed2xSelected(MouseEvent event) {
            speed_checkbox_1x.setSelected(false);
            speed_checkbox_4x.setSelected(false);
        }
        @FXML
        public void speed4xSelected(MouseEvent event) {
            speed_checkbox_2x.setSelected(false);
            speed_checkbox_1x.setSelected(false);
        }

        @FXML
        public void cellSizeSliderReleased(MouseEvent event){
            if(gridSize != (int) cell_size_slider.getValue()){
                gridSize = (int) cell_size_slider.getValue();
                statistics.resetStats();
                grid_pane.getChildren().clear();
                values = new int[(int) grid_pane.getPrefHeight() / gridSize][(int) grid_pane.getPrefWidth() / gridSize];
                visited = new boolean[(int) grid_pane.getPrefHeight() / gridSize][(int) grid_pane.getPrefWidth() / gridSize];
                gridHandler = new GridHandler(grid_pane.getPrefWidth(), grid_pane.getPrefHeight(), gridSize, grid_pane, values, visited, statistics);
                gridHandler.initializeGrid();
            }
        }

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            grid_pane.setStyle("-fx-background-color: lightgrey;");
            bg_pane.setStyle("-fx-background-color: lightgrey;");

            gridSize = (int) cell_size_slider.getValue();
            statistics = new Statistics(text_pane, island_counter_label, cells_visited_label, island_area_Label, water_area_label, max_island_perimeter_label);
            values = new int[(int) grid_pane.getPrefHeight() / gridSize][(int) grid_pane.getPrefWidth() / gridSize];
            visited = new boolean[(int) grid_pane.getPrefHeight() / gridSize][(int) grid_pane.getPrefWidth() / gridSize];
            gridHandler = new GridHandler(grid_pane.getPrefWidth(), grid_pane.getPrefHeight(), gridSize, grid_pane, values, visited, statistics);
            gridHandler.setDuration1xSelected(true);
            speed_checkbox_1x.setSelected(true);
            DFS_CheckBox.setSelected(true);
            gridHandler.initializeGrid();
        }
    }