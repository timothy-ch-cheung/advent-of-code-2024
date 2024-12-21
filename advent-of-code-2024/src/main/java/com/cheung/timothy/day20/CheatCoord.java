package com.cheung.timothy.day20;

import com.cheung.timothy.day06.Coord;

import java.util.Objects;

public class CheatCoord {
    Coord cheatCoord;
    Coord resultCoord;

    public CheatCoord(Coord cheatCoord, Coord resultCoord) {
        this.cheatCoord = cheatCoord;
        this.resultCoord = resultCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CheatCoord that)) return false;
        return Objects.equals(cheatCoord, that.cheatCoord) && Objects.equals(resultCoord, that.resultCoord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cheatCoord, resultCoord);
    }
}
