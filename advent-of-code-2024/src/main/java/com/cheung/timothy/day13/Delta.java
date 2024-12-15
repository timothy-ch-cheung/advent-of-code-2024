package com.cheung.timothy.day13;

import java.util.Objects;

public class Delta {
    private int x;
    private int y;

    public Delta(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Delta delta)) return false;
        return x == delta.x && y == delta.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
