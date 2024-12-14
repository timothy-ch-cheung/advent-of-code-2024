package com.cheung.timothy.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GardenGroupsPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = GardenGroupsPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("GardenGroups/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            List<List<Node>> garden = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                List<Node> row = new ArrayList<>();
                for (String group : line.split("")) {
                    row.add(new Node(group));
                }
                garden.add(row);
            }


            for (int y = 0; y < garden.size(); y++) {
                for (int x = 0; x < garden.get(y).size(); x++) {
                    Node currPlot = garden.get(y).get(x);

                    if (y - 1 >= 0 && currPlot.getGroup().equals(garden.get(y - 1).get(x).getGroup())) {
                        currPlot.setTop(garden.get(y - 1).get(x));
                    }
                    if (y + 1 < garden.size() && currPlot.getGroup().equals(garden.get(y + 1).get(x).getGroup())) {
                        currPlot.setDown(garden.get(y + 1).get(x));
                    }
                    if (x - 1 >= 0 && currPlot.getGroup().equals(garden.get(y).get(x - 1).getGroup())) {
                        currPlot.setLeft(garden.get(y).get(x - 1));
                    }
                    if (x + 1 < garden.get(0).size() && currPlot.getGroup().equals(garden.get(y).get(x + 1).getGroup())) {
                        currPlot.setRight(garden.get(y).get(x + 1));
                    }
                }
            }

            AtomicInteger totalCost = new AtomicInteger();
            garden.forEach(nodes -> {
                nodes.forEach(node -> {
                    if (!node.isVisited()) {
                        PlotStats plotStats = new PlotStats();
                        calculateCost(node, plotStats);
                        totalCost.addAndGet(plotStats.getFenceCost());
                    }
                });
            });

            System.out.println("Fence Cost: " + totalCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void calculateCost(Node node, PlotStats plotStats) {
        if (node == null) {
            return;
        }
        if (!node.isVisited()) {
            plotStats.incrementStats(1, node.getPerimeter());
            node.setVisited();
            calculateCost(node.getLeft(), plotStats);
            calculateCost(node.getRight(), plotStats);
            calculateCost(node.getTop(), plotStats);
            calculateCost(node.getDown(), plotStats);
        }
    }
}

class Node {
    private final String group;
    private Node left;
    private Node right;
    private Node top;
    private Node down;
    private boolean visited;

    public Node(String group) {
        this.group = group;
    }

    public Node getLeft() {
        return this.left;
    }

    public void setLeft(Node node) {
        this.left = node;
    }

    public Node getRight() {
        return this.right;
    }

    public void setRight(Node node) {
        this.right = node;
    }

    public Node getTop() {
        return this.top;
    }

    public void setTop(Node node) {
        this.top = node;
    }

    public Node getDown() {
        return this.down;
    }

    public void setDown(Node node) {
        this.down = node;
    }

    public String getGroup() {
        return group;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

    public int getPerimeter() {
        int perimeter = 0;
        if (this.left == null) {
            perimeter++;
        }
        if (this.right == null) {
            perimeter++;
        }
        if (this.top == null) {
            perimeter++;
        }
        if (this.down == null) {
            perimeter++;
        }
        return perimeter;
    }
}

class PlotStats {
    private int area;
    private int perimeter;

    public void incrementStats(int areaIncrement, int perimeterIncrement) {
        area += areaIncrement;
        perimeter += perimeterIncrement;
    }

    public int getFenceCost() {
        return area * perimeter;
    }
}