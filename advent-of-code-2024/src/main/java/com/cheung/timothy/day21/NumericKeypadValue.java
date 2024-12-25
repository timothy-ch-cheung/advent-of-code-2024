package com.cheung.timothy.day21;

import com.cheung.timothy.day06.Coord;

import java.util.ArrayList;
import java.util.List;

public enum NumericKeypadValue {
    A_BUTTON(2, 3),
    ZERO(1, 3),
    ONE(0, 2),
    TWO(1, 2),
    THREE(2, 2),
    FOUR(0, 1),
    FIVE(1, 1),
    SIX(2, 1),
    SEVEN(0, 0),
    EIGHT(1, 0),
    NINE(2, 0);

    private final Coord loc;

    NumericKeypadValue(int x, int y) {
        this.loc = new Coord(x, y);
    }

    public static NumericKeypadValue fromString(char character) {
        switch (character) {
            case 'A' -> {
                return A_BUTTON;
            }
            case '0' -> {
                return ZERO;
            }
            case '1' -> {
                return ONE;
            }
            case '2' -> {
                return TWO;
            }
            case '3' -> {
                return THREE;
            }
            case '4' -> {
                return FOUR;
            }
            case '5' -> {
                return FIVE;
            }
            case '6' -> {
                return SIX;
            }
            case '7' -> {
                return SEVEN;
            }
            case '8' -> {
                return EIGHT;
            }
            case '9' -> {
                return NINE;
            }
            default -> {
                return null;
            }
        }
    }

    public List<DirectionalKeypadValue> move(NumericKeypadValue newVal) {
        List<DirectionalKeypadValue> moves = new ArrayList<>();
        int dx = newVal.loc.getX() - loc.getX();
        int dy = newVal.loc.getY() - loc.getY();
        var keypadBtn = DirectionalKeypadValue.fromDx(dx);
        if (dy < 0) {
            for (int i = 0; i < Math.abs(dy); i++) {
                moves.add(DirectionalKeypadValue.UP);
            }
            for (int i = 0; i < Math.abs(dx); i++) {
                moves.add(keypadBtn);
            }
        } else {
            for (int i = 0; i < Math.abs(dx); i++) {
                moves.add(keypadBtn);
            }
            for (int i = 0; i < Math.abs(dy); i++) {
                moves.add(DirectionalKeypadValue.DOWN);
            }
        }
        return moves;
    }
}
