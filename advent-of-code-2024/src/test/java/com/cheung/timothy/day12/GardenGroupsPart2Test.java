package com.cheung.timothy.day12;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.cheung.timothy.day12.GardenGroupsPart2.calculateCost;
import static org.junit.jupiter.api.Assertions.*;

class GardenGroupsPart2Test {

    @Test
    void shouldCountSides() {
        Node node1 = new Node("A");
        Node node2 = new Node("A");
        Node node3 = new Node("A");
        Node node4 = new Node("A");
        Node node5 = new Node("A");
        Node node6 = new Node("A");

        node1.setDown(node2);
        node2.setTop(node1);

        node2.setDown(node3);
        node3.setTop(node2);

        node2.setRight(node4);
        node4.setLeft(node2);

        node4.setRight(node5);
        node5.setLeft(node4);

        node5.setDown(node6);
        node6.setTop(node5);

        PlotStats plotStats = new PlotStats();
        Set<Set<Node>> knownInnerCorners = new HashSet<>();
        calculateCost(node1, plotStats, knownInnerCorners);

        assertEquals(10, plotStats.getSides());
        assertEquals(6, plotStats.getArea());
    }
}