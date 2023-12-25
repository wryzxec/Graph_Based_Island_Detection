package com.example.island_algorithm_visualiser;

import javafx.scene.layout.AnchorPane;

public class Grid {

    private double width;
    private double height;
    private int tilesAcross;
    private int tilesDown;
    private int tileAmount;
    private int gridSize;
    private AnchorPane anchorPane;
    private int[][] values;
    private boolean[][] visited;
    private Statistics statistics;

    public Grid(double width, double height, int gridSize, AnchorPane anchorPane, int[][] values, boolean[][] visited, Statistics statistics){
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.anchorPane = anchorPane;
        this.values = values;
        this.visited = visited;
        this.statistics = statistics;

        tilesAcross = (int) (width / gridSize);
        tilesDown = (int) (height /gridSize);
        tileAmount = tilesAcross * tilesDown;
    }

    //Get Methods.
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public int getGridSize() { return gridSize; }
    public int getTilesAcross() { return tilesAcross; }
    public int getTileAmount() { return tileAmount; }
    public int getTilesDown() { return tilesDown; }
    public AnchorPane getAnchorPane() { return anchorPane; }
    public int[][] getValues() { return values; }
    public boolean[][] getVisited() { return visited; }

    public Statistics getStatistics() { return statistics; }
    public void setVisited(boolean[][] newVisited){
        visited = newVisited;
    }
}
