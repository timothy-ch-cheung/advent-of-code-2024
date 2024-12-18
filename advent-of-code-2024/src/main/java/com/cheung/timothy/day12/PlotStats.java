package com.cheung.timothy.day12;

class PlotStats {
    private int area;
    private int perimeter;
    private int sides;

    public void incrementPerimeter(int areaIncrement, int perimeterIncrement) {
        area += areaIncrement;
        perimeter += perimeterIncrement;
    }

    public void incrementArea(int areaIncrement) {
        area += areaIncrement;
    }

    public void incrementSides(int sidesIncrement) {
        sides += sidesIncrement;
    }

    public int getArea() {
        return area;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public int getSides() {
        return sides;
    }
}
