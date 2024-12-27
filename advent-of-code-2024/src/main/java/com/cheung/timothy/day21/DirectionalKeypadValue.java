package com.cheung.timothy.day21;

import com.cheung.timothy.day06.Coord;

import java.util.ArrayList;
import java.util.List;

public enum DirectionalKeypadValue {
    A_BUTTON(2, 0, 'A'),
    LEFT(0, 1, '<'),
    RIGHT(2, 1, '>'),
    UP(1, 0, '^'),
    DOWN(1, 1, 'v');

    private final Coord loc;
    private final char character;

    DirectionalKeypadValue(int x, int y, char character) {
        this.loc = new Coord(x, y);
        this.character = character;
    }

    public static DirectionalKeypadValue fromDx(int dx) {
        assert dx != 0;
        if (dx < 0) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }

    public static DirectionalKeypadValue fromDy(int dy) {
        assert dy != 0;
        if (dy < 0) {
            return UP;
        } else {
            return DOWN;
        }
    }

    public static DirectionalKeypadValue fromChar(char character) {
        switch (character) {
            case 'A' -> {
                return A_BUTTON;
            }
            case '^' -> {
                return UP;
            }
            case 'v' -> {
                return DOWN;
            }
            case '<' -> {
                return LEFT;
            }
            case '>' -> {
                return RIGHT;
            }
            default -> {
                throw new RuntimeException();
            }
        }
    }

    public char getCharacter() {
        return character;
    }

    public String toString() {
        return Character.toString(character);
    }

    public List<DirectionalKeypadValue> move(DirectionalKeypadValue newVal) {
        List<DirectionalKeypadValue> moves = new ArrayList<>();
        int dx = newVal.loc.getX() - loc.getX();
        int dy = newVal.loc.getY() - loc.getY();
        var horizontalBtn = DirectionalKeypadValue.fromDx(dx);
        var verticalBtn = DirectionalKeypadValue.fromDy(dy);
        if (this.loc.getX() == 0 && newVal.loc.getY() == 1) {
            for (int i = 0; i < Math.abs(dy); i++) {
                moves.add(verticalBtn);
            }
            for (int i = 0; i < Math.abs(dx); i++) {
                moves.add(horizontalBtn);
            }
        } else {
            for (int i = 0; i < Math.abs(dx); i++) {
                moves.add(horizontalBtn);
            }
            for (int i = 0; i < Math.abs(dy); i++) {
                moves.add(verticalBtn);
            }
        }
        return moves;
    }
}
