package com.example.island_algorithm_visualiser;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

import java.util.List;

public class Statistics {

    private AnchorPane anchorPane;
    private Label islandCountLabel;
    private Label visitedCountLabel;
    private Label islandAreaLabel;
    private Label waterAreaLabel;
    private Label maxIslandPerimeterLabel;
    private int islandCount;
    private int visitedCount;
    private int islandArea;
    private int waterArea;
    private int islandPerimeter;
    private int maxIslandPerimeter;
    private List<Pair<Integer, Integer>> perimeterPoints;
    private List<Pair<Integer, Integer>> maxPerimeterPoints;
    private List<Pair<Integer, Integer>> lakePoints;

    public Statistics(AnchorPane anchorPane, Label islandCountLabel, Label cellsVisitedLabel, Label islandAreaLabel, Label waterAreaLabel, Label maxIslandPerimeterLabel){
        this.anchorPane = anchorPane;
        this.islandCountLabel = islandCountLabel;
        this.visitedCountLabel = cellsVisitedLabel;
        this.islandAreaLabel = islandAreaLabel;
        this.waterAreaLabel = waterAreaLabel;
        this.maxIslandPerimeterLabel = maxIslandPerimeterLabel;
    }
    public void updateIslandCountLabel(int count){
        islandCountLabel.setText("Island Count: "+Integer.toString(count));
    }
    public void updateVisitedCountLabel(int count){
        visitedCountLabel.setText("Cells Visited: "+Integer.toString(count));
    }
    public void updateIslandAreaLabel(int area){
        islandAreaLabel.setText("Total Island Area: "+Integer.toString(area));
    }
    public void updateWaterAreaLabel(int area){
        waterAreaLabel.setText("Total Water Area: "+Integer.toString(area));
    }
    public void updateMaxIslandPerimeterLabel(int perimeter){
        maxIslandPerimeterLabel.setText("Max Island Perimeter: " + Integer.toString(perimeter));
    }
    public void addPerimeterPoint(Pair<Integer, Integer> point){
        perimeterPoints.add(point);
    }
    public void addLakePoint(Pair<Integer, Integer> point) { lakePoints.add(point); }

    public void resetStats(){
        islandCount = 0;
        visitedCount = 0;
        islandArea = 0;
        waterArea = 0;
        islandPerimeter = 0;
        maxIslandPerimeter = 0;
        updateIslandCountLabel(islandCount);
        updateVisitedCountLabel(visitedCount);
        updateIslandAreaLabel(islandArea);
        updateWaterAreaLabel(waterArea);
        updateMaxIslandPerimeterLabel(maxIslandPerimeter);
    }
    public int getIslandCount() { return islandCount; }
    public int getVisitedCount() { return visitedCount; }
    public int getIslandArea() { return islandArea; }
    public int getWaterArea() { return waterArea; }
    public int getIslandPerimeter() { return  islandPerimeter; }
    public int getMaxIslandPerimeter() { return maxIslandPerimeter; }
    public List<Pair<Integer, Integer>> getPerimeterPoints() { return perimeterPoints; }
    public List<Pair<Integer, Integer>> getMaxPerimeterPoints() { return maxPerimeterPoints; }
    public List<Pair<Integer, Integer>> getLakePoints() { return lakePoints; }
    public void incrementIslandCount() { islandCount++; }
    public void incrementVisitedCount() { visitedCount++; }
    public void incrementIslandArea() { islandArea++; }
    public void incrementWaterArea() { waterArea++; }
    public void incrementIslandPerimeter() { islandPerimeter++; }
    public void setIslandPerimeter(int perimeter) { islandPerimeter = perimeter; }
    public void setMaxIslandPerimeter(int perimeter) { maxIslandPerimeter = perimeter; }
    public void setMaxPerimeterPoints(List<Pair<Integer, Integer>> points){ maxPerimeterPoints = points; }
    public void setPerimeterPoints(List<Pair<Integer, Integer>> points){ perimeterPoints = points; }
    public void setLakePoints(List<Pair<Integer, Integer>> points){ lakePoints = points; }
}
