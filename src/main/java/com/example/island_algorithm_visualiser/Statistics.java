package com.example.island_algorithm_visualiser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Statistics {

    private AnchorPane anchorPane;
    private Label islandCountLabel;
    private Label visitedCountLabel;
    private int islandCount;
    private int visitedCount;

    public Statistics(AnchorPane anchorPane, Label islandCountLabel, Label cellsVisitedLabel){
        this.anchorPane = anchorPane;
        this.islandCountLabel = islandCountLabel;
        this.visitedCountLabel = cellsVisitedLabel;
    }
    public void updateIslandCountLabel(int count){
        islandCountLabel.setText("Island Count: "+Integer.toString(count));
    }

    public void updateVisitedCountLabel(int count){
        visitedCountLabel.setText("Cells Visited: "+Integer.toString(count));
    }
    public void resetStats(){
        islandCount = 0;
        visitedCount = 0;
        updateIslandCountLabel(islandCount);
        updateVisitedCountLabel(visitedCount);
    }
    public int getIslandCount() { return islandCount; }
    public int getVisitedCount() { return visitedCount; }
    public void incrementIslandCount() { islandCount++; }
    public void incrementVisitedCount() { visitedCount++; }
}
