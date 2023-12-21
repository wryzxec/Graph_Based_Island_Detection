package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class GridHandler extends Grid {

    private Color bg1 = Color.WHITE;
    private Color bg2 = Color.color(0.82, 0.82, 0.82);

    public GridHandler(double width, double height, int gridSize, AnchorPane anchorPane, int[][] values){
        super(width, height, gridSize, anchorPane, values);
    }
    
    public void initializeGrid(){
        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                Random rnd = new Random();
                int num = rnd.nextInt(4);

                Rectangle rectangle = new Rectangle(j * getGridSize(), i * getGridSize(), getGridSize(), getGridSize());
                rectangle.setId(Integer.toString(i)+Integer.toString(j));

                if(num == 1){
                    rectangle.setStyle("-fx-fill: lightyellow; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
                    getValues()[i][j] = 1;
                } else{
                    rectangle.setStyle("-fx-fill: lightblue; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
                    getValues()[i][j] = 0;
                }
                getAnchorPane().getChildren().add(rectangle);
            }
        }
    }
    public void compute(int i, int j){

        Rectangle rectangle = (Rectangle) getAnchorPane().lookup(Integer.toString(i)+Integer.toString(j));
        if(getValues()[i][j] == 1){
            Rectangle newRectangle = new Rectangle(j * getGridSize(), i * getGridSize(), getGridSize(), getGridSize());
            newRectangle.setStyle("-fx-fill: red; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
            getAnchorPane().getChildren().remove(rectangle);
            getAnchorPane().getChildren().add(newRectangle);
        }
    }

    public void visualize(){

        Timeline timeline = new Timeline();
        int duration = 25;

        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                int x = i;
                int y = j;

                KeyFrame keyFrame = new KeyFrame(Duration.millis(duration * (i*getTilesAcross() + j)), e -> {
                    compute(x,y);
                });
                timeline.getKeyFrames().add(keyFrame);
            }
        }
        timeline.play();
    }
}
