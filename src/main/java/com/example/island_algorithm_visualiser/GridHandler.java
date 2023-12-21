package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;

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

        Rectangle newRectangle = new Rectangle(j * getGridSize(), i * getGridSize(), getGridSize(), getGridSize());
        newRectangle.setStyle("-fx-fill: red; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
        getAnchorPane().getChildren().remove(rectangle);
        getAnchorPane().getChildren().add(newRectangle);
    }

    public void visualize(){

        Timeline timeline = new Timeline();
        int duration = 100;

        List<Pair<Integer, Integer>> dirs = new ArrayList<>();
        dirs.add(new Pair<>(1,0));
        dirs.add(new Pair<>(0,1));
        dirs.add(new Pair<>(-1,0));
        dirs.add(new Pair<>(0,-1));

        Queue<Pair<Integer, Integer>> toSearch = new LinkedList<>();
        int stagger = 100;

        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                int x = i;
                int y = j;

                if(getValues()[i][j] == 1){
                    KeyFrame keyFrame = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                        compute(x,y);
                    });
                    timeline.getKeyFrames().add(keyFrame);
                    toSearch.add(new Pair<>(i,j));
                    getValues()[i][j] = 0;
                    while(!toSearch.isEmpty()){
                        Pair<Integer, Integer> pos = toSearch.poll();
                        for(Pair<Integer, Integer> dir : dirs){
                            int a = pos.getKey() + dir.getKey();
                            int b = pos.getValue() + dir.getValue();
                            if(a >= 0 && a < getTilesDown() && b >= 0 && b < getTilesAcross() && getValues()[a][b] == 1){
                                getValues()[a][b] = 0;
                                stagger += 150;
                                keyFrame = new KeyFrame(Duration.millis(duration * ((i*getTilesAcross()) + j) + stagger), e -> {
                                    compute(a,b);
                                });
                                timeline.getKeyFrames().add(keyFrame);
                                toSearch.add(new Pair<>(a, b));
                            }
                        }
                    }
                }
            }
        }
        timeline.play();
    }
}
