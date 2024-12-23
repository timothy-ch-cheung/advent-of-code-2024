package com.cheung.timothy.day18;

import com.cheung.timothy.day06.Coord;

public class CostCoord extends Coord {

    private int cost;

    public CostCoord(int x, int y) {
        super(x, y);
    }

    public void setCost(int newCost) {
        cost = newCost;
    }

    public int getCost() {
        return cost;
    }
}
