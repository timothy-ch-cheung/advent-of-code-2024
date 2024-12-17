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
                        totalCost.addAndGet(plotStats.getArea() * plotStats.getPerimeter());
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
            plotStats.incrementPerimeter(1, node.getPerimeter());
            node.setVisited();
            calculateCost(node.getLeft(), plotStats);
            calculateCost(node.getRight(), plotStats);
            calculateCost(node.getTop(), plotStats);
            calculateCost(node.getDown(), plotStats);
        }
    }
}

