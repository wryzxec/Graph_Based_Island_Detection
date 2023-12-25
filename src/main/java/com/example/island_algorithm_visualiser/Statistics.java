package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Statistics {

    private int islandCount;
    private int BFS_cellsVisited;
    private int DFS_cellsVisited;
    private int duration;
    private AnchorPane anchorPane;
    private Timeline timeline;
    private Label islandCountLabel;


    public Statistics(AnchorPane anchorPane, Label islandCountLabel){
        this.anchorPane = anchorPane;
        this.islandCountLabel = islandCountLabel;
    }
    public void updateIslandCount(int count){
        islandCountLabel.setText("Island Count: "+Integer.toString(count));
    }
    public void resetStats(){
        updateIslandCount(0);
    }

    public int getIslandCount() { return islandCount; }
    public AnchorPane getAnchorPane() { return anchorPane; }
    public void setIslandCount(int newIslandCount) { islandCount = newIslandCount; }
    public Label getIslandCountLabel() { return islandCountLabel; }

}
