package com.cheung.timothy.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cheung.timothy.day06.Direction.UP;

public class GuardGallivantPart2 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = GuardGallivantPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("GuardGallivant/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<List<String>> map = new ArrayList<>();

            String line;
            Coord start = null;
            int rowNum = 0;
            while ((line = reader.readLine()) != null) {
                List<String> row = new ArrayList<>();
                String[] rowText = line.split("");
                for (int x = 0; x < rowText.length; x++) {
                    row.add(rowText[x]);
                    if (UP.symbol().equals(rowText[x])) {
                        start = new Coord(x, rowNum);
                    }
                }
                map.add(row);
                rowNum++;
            }

            Coord curr = new Coord(start.getX(), start.getY());
            Direction currDir = UP;
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

            int totalCycles = 0;
            for (Coord coord : visited) {
                if(detectCycle(start, coord, map)) {
                    totalCycles++;
                }
            }
            System.out.println("Total Cycles: " + totalCycles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean detectCycle(Coord start, Coord obstacle, List<List<String>> map) {
        Coord curr = start;
        Direction currDir = UP;
        int rows = map.size();
        int columns = map.get(0).size();
        int pathLength = 0;
        int cycleLength = cycleLength(rows, columns);
        while (isInBounds(curr, map.get(0).size(), map.size())) {
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
            if (isInBounds(newPosition, columns, rows) && ("#".equals(map.get(newPosition.getY()).get(newPosition.getX())) || newPosition.equals(obstacle))) {
                currDir = currDir.rotate();
            } else {
                curr = newPosition;
                pathLength += 1;
            }
            if (pathLength > cycleLength) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInBounds(Coord coord, int columns, int rows) {
        return coord.getX() < columns && coord.getX() >= 0 && coord.getY() < rows && coord.getY() >= 0;
    }

    private static int cycleLength(int rows, int columns) {
        return 4 * rows * columns;
    }
}
