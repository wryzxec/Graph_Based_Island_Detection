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

    private int islandCount = 0;
    private Timeline timeline;
    private boolean visualizationRunning = false;

    public GridHandler(double width, double height, int gridSize, AnchorPane anchorPane, int[][] values, boolean[][] visited){
        super(width, height, gridSize, anchorPane, values, visited);
    }

    public void generateIsland(int i, int j){

        if(i < 0 || i >= getTilesDown() || j < 0 || j >= getTilesAcross() || getValues()[i][j] == 1){
            return;
        }
        getValues()[i][j] = 1;

        Random rnd = new Random();
        if(rnd.nextDouble() < 0.4){
            generateIsland(i+1, j);
            generateIsland(i-1, j);
            generateIsland(i, j+1);
            generateIsland(i, j-1);
        }
    }

    public void populateGrid(){
        Random rnd = new Random();
        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                if(rnd.nextDouble() < 0.025){
                    generateIsland(i, j);
                }
            }
        }
    }

    public void displayCell(int i, int j){
        Rectangle rectangle = new Rectangle(j * getGridSize(), i * getGridSize(), getGridSize(), getGridSize());
        rectangle.setId(Integer.toString(i)+Integer.toString(j));

        if(getValues()[i][j] == 1){
            rectangle.setStyle("-fx-fill: lightyellow; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
        }
        else{
            rectangle.setStyle("-fx-fill: lightblue; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
        }
        getAnchorPane().getChildren().add(rectangle);
    }

    public void initializeGrid(){
        populateGrid();
        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                displayCell(i, j);
            }
        }
    }
    public void compute(int i, int j){

        Rectangle rectangle = (Rectangle) getAnchorPane().lookup(Integer.toString(i)+Integer.toString(j));

        Rectangle newRectangle = new Rectangle(j * getGridSize(), i * getGridSize(), getGridSize(), getGridSize());
        newRectangle.setStyle("-fx-fill: lightgreen; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
        getAnchorPane().getChildren().remove(rectangle);
        getAnchorPane().getChildren().add(newRectangle);
    }

    public void visualizeIteration(int i, int j){

        Rectangle oldRectangle = (Rectangle) getAnchorPane().lookup(Integer.toString(i)+Integer.toString(j));
        Rectangle newRectangle = new Rectangle(j * getGridSize(), (i) * getGridSize(), getGridSize(), getGridSize());
        newRectangle.setStyle("-fx-fill: red; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
        getAnchorPane().getChildren().remove(oldRectangle);
        getAnchorPane().getChildren().add(newRectangle);

        if(!(i == 0 && j == 0)){
            if(j != 0){
                Rectangle oldPrevRectangle = (Rectangle) getAnchorPane().lookup(Integer.toString(i)+Integer.toString(j-1));
                Rectangle newPrevRectangle = new Rectangle((j-1) * getGridSize(), (i) * getGridSize(), getGridSize(), getGridSize());
                if(getValues()[i][j-1] == 1){
                    newPrevRectangle.setStyle("-fx-fill: lightgreen; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
                }
                else{
                    newPrevRectangle.setStyle("-fx-fill: lightblue; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
                }
                getAnchorPane().getChildren().remove(oldPrevRectangle);
                getAnchorPane().getChildren().add(newPrevRectangle);
            }
            else{
                Rectangle oldPrevRectangle = (Rectangle) getAnchorPane().lookup(Integer.toString(i-1)+Integer.toString(getTilesAcross()-1));
                Rectangle newPrevRectangle = new Rectangle((getTilesAcross()-1) * getGridSize(), (i-1) * getGridSize(), getGridSize(), getGridSize());
                if(getValues()[i-1][getTilesAcross()-1] == 1){
                    newPrevRectangle.setStyle("-fx-fill: lightgreen; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
                }
                else{
                    newPrevRectangle.setStyle("-fx-fill: lightblue; -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
                }
                getAnchorPane().getChildren().remove(oldPrevRectangle);
                getAnchorPane().getChildren().add(newPrevRectangle);
            }
        }

    }

    public void visualize(){

        timeline = new Timeline();
        int duration = 25;
        visualizationRunning = true;

        List<Pair<Integer, Integer>> dirs = new ArrayList<>();
        dirs.add(new Pair<>(1,0));
        dirs.add(new Pair<>(0,1));
        dirs.add(new Pair<>(-1,0));
        dirs.add(new Pair<>(0,-1));

        Queue<Pair<Integer, Integer>> toSearch = new LinkedList<>();
        int stagger = duration;

        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                int x = i;
                int y = j;

                KeyFrame keyFrame = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                    visualizeIteration(x,y);
                });
                timeline.getKeyFrames().add(keyFrame);

                if(getValues()[i][j] == 1 && !getVisited()[i][j]){
                    islandCount++;
                    keyFrame = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                        compute(x,y);
                    });
                    timeline.getKeyFrames().add(keyFrame);
                    toSearch.add(new Pair<>(i,j));
                    getVisited()[i][j] = true;
                    while(!toSearch.isEmpty()){
                        Pair<Integer, Integer> pos = toSearch.poll();
                        for(Pair<Integer, Integer> dir : dirs){
                            int a = pos.getKey() + dir.getKey();
                            int b = pos.getValue() + dir.getValue();
                            if(a >= 0 && a < getTilesDown() && b >= 0 && b < getTilesAcross() && getValues()[a][b] == 1 && !getVisited()[a][b]){
                                getVisited()[a][b] = true;
                                stagger += duration;
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
        timeline.setOnFinished(event -> {
            visualizationRunning = false;
        });
        timeline.play();
    }

    public void resetGrid(){
        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                displayCell(i, j);
            }
        }
    }
    public void resetVisited(){
        int m = getVisited().length;
        int n = getVisited()[0].length;
        setVisited(new boolean[m][n]);
    }

    public void stopVisualisation(){
        timeline.stop();
        visualizationRunning = false;
    }
    public int getIslandCount(){ return islandCount; }
    public void resetIslandCount() { islandCount = 0; }

    public boolean isVisualizationRunning() { return visualizationRunning; }
}
