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
        var keypadBtn = DirectionalKeypadValue.fromDx(dx);
        if (dy < 0) {
            for (int i = 0; i < Math.abs(dx); i++) {
                moves.add(keypadBtn);
            }
            for (int i = 0; i < Math.abs(dy); i++) {
                moves.add(DirectionalKeypadValue.UP);
            }
        } else {
            for (int i = 0; i < Math.abs(dy); i++) {
                moves.add(DirectionalKeypadValue.DOWN);
            }
            for (int i = 0; i < Math.abs(dx); i++) {
                moves.add(keypadBtn);
            }
        }
        return moves;
    }
}
