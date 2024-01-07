package com.example.island_algorithm_visualiser;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Application extends javafx.application.Application {

    private static final int WINDOW_WIDTH = 1536;
    private static final int WINDOW_HEIGHT = 880;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setTitle("Graph-based Island Detection using DFS/BFS Algorithms");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}