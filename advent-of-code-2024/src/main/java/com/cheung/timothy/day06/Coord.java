package com.cheung.timothy.day06;

import com.cheung.timothy.day13.Delta;

import java.util.Objects;

public class Coord {
    private int x;
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void apply(Delta delta) {
        this.x += delta.getX();
        this.y += delta.getY();
    }

    public Coord clone() {
        return new Coord(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coord coord)) return false;
        return x == coord.x && y == coord.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[%s, %s]".formatted(x, y);
    }
}
