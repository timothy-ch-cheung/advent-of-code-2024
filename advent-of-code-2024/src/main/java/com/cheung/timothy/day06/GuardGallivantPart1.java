package com.cheung.timothy.day06;

import com.cheung.timothy.day04.CeresSearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class GuardGallivantPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = CeresSearch.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("GuardGallivant/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<List<String>> map = new ArrayList<>();

            String line;
            Coord curr = null;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                List<String> row = new ArrayList<>();
                String[] rowText = line.split("");
                for (int x = 0; x < rowText.length; x++) {
                    row.add(rowText[x]);
                    if (Direction.UP.symbol().equals(rowText[x])) {
                        curr = new Coord(x, rowNum);
                    }
                }
                map.add(row);
                rowNum++;
            }

            Direction currDir = Direction.UP;
            int rows = map.size();
            int columns = map.get(0).size();
            Set<Coord> visited = new HashSet<>();
            while (isInBounds(curr, columns, rows)) {
                visited.add(new Coord(curr.getX(), curr.getY()));
                Coord newPosition = null;
                switch (currDir) {
                    case UP -> {
                        newPosition = new Coord(curr.getX(), curr.getY() - 1);
                    }
                    case DOWN -> {
                        newPosition = new Coord(curr.getX(), curr.getY() + 1);
                    }
                    case LEFT -> {
                        newPosition = new Coord(curr.getX() - 1, curr.getY());
                    }
                    case RIGHT -> {
                        newPosition = new Coord(curr.getX() + 1, curr.getY());
                    }
                }
                if (isInBounds(newPosition, columns, rows) && "#".equals(map.get(newPosition.getY()).get(newPosition.getX()))) {
                    currDir = currDir.rotate();
                } else {
                    curr = newPosition;
                }
            }
            System.out.println("Distinct Positions: " + visited.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isInBounds(Coord coord, int columns, int rows) {
        return coord.getX() < columns && coord.getX() >= 0 && coord.getY() < rows && coord.getY() >= 0;
    }

    static class Coord {
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

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Coord coord)) return false;
            return x == coord.x && y == coord.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static enum Direction {
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
}
