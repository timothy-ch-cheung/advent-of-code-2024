package com.cheung.timothy.day13;

import java.util.Objects;

public class LongDelta {
    private long x;
    private long y;

    public LongDelta(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LongDelta delta)) return false;
        return x == delta.x && y == delta.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
