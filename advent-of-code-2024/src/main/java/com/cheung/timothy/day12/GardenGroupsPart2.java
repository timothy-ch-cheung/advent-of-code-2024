package com.cheung.timothy.day12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GardenGroupsPart2 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = GardenGroupsPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("GardenGroups/example.txt");
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
                        Set<Set<Node>> knownInnerCorners = new HashSet<>();
                        calculateCost(node, plotStats, knownInnerCorners);
                        int plotCost = plotStats.getSides() * plotStats.getArea();
                        totalCost.addAndGet(plotCost);
                    }
                });
            });

            System.out.println("Fence Cost: " + totalCost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void calculateCost(Node node, PlotStats plotStats, Set<Set<Node>> knownInnerCorners) {
        if (node == null) {
            return;
        }
        if (!node.isVisited()) {
            plotStats.incrementArea(1);
            if (isQuadrupleCorner(node)) {
                plotStats.incrementSides(4);
            } else {
                if (isDoubleCorner(node)) {
                    plotStats.incrementSides(2);
                }
                if (isOuterCorner(node)) {
                    plotStats.incrementSides(1);
                }
                Set<Node> innerCorner = isInnerCorner(node);
                if (innerCorner != null && !knownInnerCorners.contains(innerCorner)) {
                    plotStats.incrementSides(1);
                    knownInnerCorners.add(innerCorner);
                }
            }
            node.setVisited();
            calculateCost(node.getLeft(), plotStats, knownInnerCorners);
            calculateCost(node.getRight(), plotStats, knownInnerCorners);
            calculateCost(node.getTop(), plotStats, knownInnerCorners);
            calculateCost(node.getDown(), plotStats, knownInnerCorners);
        }
    }

    private static boolean isQuadrupleCorner(Node node) {
        return node.getTop() == null && node.getLeft() == null && node.getRight() == null && node.getDown() == null;
    }

    private static boolean isDoubleCorner(Node node) {
        return (node.getTop() == null && node.getRight() == null && node.getDown() == null && node.getLeft() != null) ||
                (node.getRight() == null && node.getDown() == null && node.getLeft() == null && node.getTop() != null) ||
                (node.getDown() == null && node.getLeft() == null && node.getTop() == null && node.getRight() != null) ||
                (node.getLeft() == null && node.getTop() == null && node.getRight() == null && node.getDown() != null);
    }

    private static boolean isOuterCorner(Node node) {
        return (node.getTop() == null && node.getRight() == null && node.getDown() != null && node.getLeft() != null) ||
                (node.getRight() == null && node.getDown() == null && node.getLeft() != null && node.getTop() != null) ||
                (node.getDown() == null && node.getLeft() == null && node.getTop() != null && node.getRight() != null) ||
                (node.getLeft() == null && node.getTop() == null && node.getRight() != null && node.getDown() != null);
    }

    private static Set<Node> isInnerCorner(Node node) {
        if (node.getRight() == null && node.getTop() != null && node.getTop().getRight() != null) {
            return new HashSet<>(Arrays.asList(node, node.getTop(), node.getTop().getRight()));

        } else if (node.getDown() == null && node.getRight() != null && node.getRight().getDown() != null) {
            return new HashSet<>(Arrays.asList(node, node.getRight(), node.getRight().getDown()));

        } else if (node.getLeft() == null && node.getDown() != null && node.getDown().getLeft() != null) {
            return new HashSet<>(Arrays.asList(node, node.getDown(), node.getDown().getLeft()));

        } else if (node.getTop() == null && node.getLeft() != null && node.getLeft().getTop() != null) {
            return new HashSet<>(Arrays.asList(node, node.getLeft(), node.getLeft().getTop()));

        } else if (node.getRight() == null && node.getTop() != null && node.getTop().getLeft() != null) {
            return new HashSet<>(Arrays.asList(node, node.getTop(), node.getTop().getLeft()));

        } else if (node.getDown() == null && node.getLeft() != null && node.getLeft().getDown() != null) {
            return new HashSet<>(Arrays.asList(node, node.getLeft(), node.getLeft().getDown()));

        } else if (node.getLeft() == null && node.getTop() != null && node.getTop().getLeft() != null) {
            return new HashSet<>(Arrays.asList(node, node.getTop(), node.getTop().getLeft()));

        } else if (node.getRight() == null && node.getDown() != null && node.getDown().getRight() != null) {
            return new HashSet<>(Arrays.asList(node, node.getDown(), node.getDown().getRight()));
        }

        return null;
    }
}
