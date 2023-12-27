package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;

public class GridHandler extends Grid {


    private Timeline timeline;
    private KeyFrame keyframe;
    private int duration = 25;
    private int stagger = 0;
    private boolean visualizationRunning = false;
    private boolean DFS_Selected = false;
    private boolean BFS_Selected = false;

    public GridHandler(double width, double height, int gridSize, AnchorPane anchorPane, int[][] values, boolean[][] visited, Statistics statistics){
        super(width, height, gridSize, anchorPane, values, visited, statistics);
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

    public void visualizeMaxPerimeter(){
        List<Pair<Integer, Integer>> maxIslandPerimeterPoints = getStatistics().getMaxPerimeterPoints();
        int n = maxIslandPerimeterPoints.size();

        for(int a = 0; a < n; a++){
            Pair<Integer, Integer> pos = maxIslandPerimeterPoints.get(a);
            int i = pos.getKey();
            int j = pos.getValue();
            Rectangle oldRectangle = (Rectangle) getAnchorPane().lookup(Integer.toString(i)+Integer.toString(j));
            Rectangle newRectangle = new Rectangle(j * getGridSize(), (i) * getGridSize(), getGridSize(), getGridSize());
            newRectangle.setStyle("-fx-fill: rgba(255,0,0,0.25); -fx-stroke: rgba(0,0,0,0.25); -fx-stroke-width: 1;");
            getAnchorPane().getChildren().remove(oldRectangle);
            getAnchorPane().getChildren().add(newRectangle);
        }
    }

    public boolean isOutOfBounds(int i, int j){
        return i < 0 || i >= getTilesDown() || j < 0 || j >= getTilesAcross();
    }

    public void DFS(int i, int j, int startI, int startJ){
        if(isOutOfBounds(i, j)){
            stagger += duration;
            keyframe = new KeyFrame(Duration.millis((duration*(startI*getTilesAcross() + startJ) + stagger)), e -> {
                getStatistics().incrementIslandPerimeter();
                getStatistics().setMaxIslandPerimeter(Math.max(getStatistics().getIslandPerimeter(), getStatistics().getMaxIslandPerimeter()));
                getStatistics().updateMaxIslandPerimeterLabel(getStatistics().getMaxIslandPerimeter());
            });
            timeline.getKeyFrames().add(keyframe);
            return;
        }
        if(getVisited()[i][j]){
            return;
        }
        if(getValues()[i][j] == 0){
            stagger += duration;
            keyframe = new KeyFrame(Duration.millis((duration*(startI*getTilesAcross() + startJ) + stagger)), e -> {
                if(!getStatistics().getPerimeterPoints().contains(new Pair<>(i, j))){
                    getStatistics().incrementIslandPerimeter();
                    getStatistics().addPerimeterPoint(new Pair<>(i, j));
                    if(getStatistics().getIslandPerimeter() >= getStatistics().getMaxIslandPerimeter()){
                        getStatistics().setMaxIslandPerimeter(getStatistics().getIslandPerimeter());
                        getStatistics().setMaxPerimeterPoints(getStatistics().getPerimeterPoints());
                    }
                    getStatistics().setMaxIslandPerimeter(Math.max(getStatistics().getIslandPerimeter(), getStatistics().getMaxIslandPerimeter()));
                    getStatistics().updateMaxIslandPerimeterLabel(getStatistics().getMaxIslandPerimeter());
                }
            });
            timeline.getKeyFrames().add(keyframe);
            return;
        }


        getVisited()[i][j] = true;
        stagger += duration;
        keyframe = new KeyFrame(Duration.millis((duration*(startI*getTilesAcross() + startJ) + stagger)), e -> {
            getStatistics().incrementVisitedCount();
            getStatistics().incrementIslandArea();
            getStatistics().updateVisitedCountLabel(getStatistics().getVisitedCount());
            getStatistics().updateIslandAreaLabel(getStatistics().getIslandArea());
            compute(i,j);
        });
        timeline.getKeyFrames().add(keyframe);

        DFS(i+1, j, startI, startJ);
        DFS(i-1, j, startI, startJ);
        DFS(i, j+1, startI, startJ);
        DFS(i, j-1, startI, startJ);
    }

    public void BFS(int i, int j){

        Queue<Pair<Integer, Integer>> toSearch = new LinkedList<>();
        List<Pair<Integer, Integer>> dirs = new ArrayList<>();
        dirs.add(new Pair<>(1,0));
        dirs.add(new Pair<>(0,1));
        dirs.add(new Pair<>(-1,0));
        dirs.add(new Pair<>(0,-1));

        keyframe = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
            getStatistics().incrementVisitedCount();
            getStatistics().incrementIslandArea();
            getStatistics().updateVisitedCountLabel(getStatistics().getVisitedCount());
            getStatistics().updateIslandAreaLabel(getStatistics().getIslandArea());
            compute(i,j);
        });
        timeline.getKeyFrames().add(keyframe);
        toSearch.add(new Pair<>(i,j));
        getVisited()[i][j] = true;
        while(!toSearch.isEmpty()){
            Pair<Integer, Integer> pos = toSearch.poll();
            for(Pair<Integer, Integer> dir : dirs){
                int a = pos.getKey() + dir.getKey();
                int b = pos.getValue() + dir.getValue();
                if(isOutOfBounds(a, b)){
                    stagger += duration;
                    keyframe = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                        getStatistics().incrementIslandPerimeter();
                        getStatistics().setMaxIslandPerimeter(Math.max(getStatistics().getIslandPerimeter(), getStatistics().getMaxIslandPerimeter()));
                        getStatistics().updateMaxIslandPerimeterLabel(getStatistics().getMaxIslandPerimeter());
                    });
                    timeline.getKeyFrames().add(keyframe);
                }
                if(!isOutOfBounds(a, b) && getValues()[a][b] == 1 && !getVisited()[a][b]){
                    getVisited()[a][b] = true;
                    stagger += duration;
                    keyframe = new KeyFrame(Duration.millis(duration * ((i*getTilesAcross()) + j) + stagger), e -> {
                        getStatistics().incrementVisitedCount();
                        getStatistics().incrementIslandArea();
                        getStatistics().updateVisitedCountLabel(getStatistics().getVisitedCount());
                        getStatistics().updateIslandAreaLabel(getStatistics().getIslandArea());
                        compute(a,b);
                    });
                    timeline.getKeyFrames().add(keyframe);
                    toSearch.add(new Pair<>(a, b));
                }
                else if(!isOutOfBounds(a, b) && getValues()[a][b] == 0 && !getVisited()[a][b]){
                    stagger += duration;
                    keyframe = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                        if(!getStatistics().getPerimeterPoints().contains(new Pair<>(a, b))){
                            getStatistics().incrementIslandPerimeter();
                            getStatistics().addPerimeterPoint(new Pair<>(a, b));
                            if(getStatistics().getIslandPerimeter() >= getStatistics().getMaxIslandPerimeter()){
                                getStatistics().setMaxIslandPerimeter(getStatistics().getIslandPerimeter());
                                getStatistics().setMaxPerimeterPoints(getStatistics().getPerimeterPoints());
                            }
                            getStatistics().setMaxIslandPerimeter(Math.max(getStatistics().getIslandPerimeter(), getStatistics().getMaxIslandPerimeter()));
                            getStatistics().updateMaxIslandPerimeterLabel(getStatistics().getMaxIslandPerimeter());
                        }
                    });
                    timeline.getKeyFrames().add(keyframe);
                }
            }
        }
    }

    public void visualize(){
        timeline = new Timeline();
        visualizationRunning = true;
        stagger = duration;
        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                int startX = i;
                int startY = j;
                keyframe = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                    if(!getVisited()[startX][startY]){
                        getStatistics().incrementVisitedCount();
                        getStatistics().updateVisitedCountLabel(getStatistics().getVisitedCount());
                    }
                    visualizeIteration(startX,startY);
                });
                timeline.getKeyFrames().add(keyframe);
                if(getValues()[i][j] == 1 && !getVisited()[i][j]){
                    keyframe = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                        getStatistics().setIslandPerimeter(0);
                        getStatistics().setPerimeterPoints(new ArrayList<>());
                        getStatistics().incrementIslandCount();
                        getStatistics().updateIslandCountLabel(getStatistics().getIslandCount());
                    });
                    timeline.getKeyFrames().add(keyframe);

                    if(DFS_Selected && !BFS_Selected){
                        DFS(i,j,startX,startY);
                    }
                    else if(BFS_Selected && !DFS_Selected){
                        BFS(i, j);
                    }
                }
                if(getValues()[i][j] == 0 && !getVisited()[i][j]){
                    keyframe = new KeyFrame(Duration.millis((duration*(i*getTilesAcross() + j) + stagger)), e -> {
                        getStatistics().incrementWaterArea();
                        getStatistics().updateWaterAreaLabel(getStatistics().getWaterArea());
                    });
                    timeline.getKeyFrames().add(keyframe);
                }
            }
        }
        timeline.play();
        timeline.setOnFinished(event -> {
            visualizeMaxPerimeter();
            visualizationRunning = false;
        });
    }

    public void resetVisited(){
        int m = getVisited().length;
        int n = getVisited()[0].length;
        setVisited(new boolean[m][n]);
    }

    public void resetGrid(){
        for(int i = 0; i < getTilesDown(); i++){
            for(int j = 0; j < getTilesAcross(); j++){
                displayCell(i, j);
            }
        }
    }

    public boolean isVisualizationRunning(){
        return visualizationRunning;
    }
    public void stopVisualisation(){
        timeline.stop();
        visualizationRunning = false;
    }
    public void setBFS_Selected(boolean isSelected){
        BFS_Selected = isSelected;
    }
    public void setDFS_Selected(boolean isSelected){
        DFS_Selected = isSelected;
    }
}
