package com.cheung.timothy.day06;

public enum Direction {
    UP("^"),
    DOWN("V"),
    LEFT("<"),
    RIGHT(">");

    private final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

    public Direction rotate() {
        switch (this) {
            case RIGHT -> {
                return DOWN;
            }
            case DOWN -> {
                return LEFT;
            }
            case LEFT -> {
                return UP;
            }
            case UP -> {
                return RIGHT;
            }
            default -> {
                return null;
            }
        }

    }
}