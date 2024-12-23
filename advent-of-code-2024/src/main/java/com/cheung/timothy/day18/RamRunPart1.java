package com.cheung.timothy.day18;

import com.cheung.timothy.day06.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RamRunPart1 {

    private static final char SPACE = '.';
    private static final char WALL = '#';
    private static final int SIZE = 71;

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = RamRunPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("RamRun/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            char[][] map = new char[SIZE][SIZE];
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    map[y][x] = SPACE;
                }
            }

            int bytesRead = 0;
            while ((line = reader.readLine()) != null && bytesRead < 1024) {
                String[] coordinate = line.split(",");
                map[Integer.parseInt(coordinate[1])][Integer.parseInt(coordinate[0])] = WALL;
                bytesRead++;
            }

            printMap(map);

            Coord start = new Coord(0, 0);
            Coord end = new Coord(SIZE - 1, SIZE - 1);
            Map<Coord, Integer> distances = new HashMap<>();
            Set<Coord> notVisited = new HashSet<>();
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (map[y][x] == SPACE) {
                        Coord coord = new Coord(x, y);
                        distances.put(coord, Integer.MAX_VALUE);
                        notVisited.add(coord);
                    }
                }
            }
            distances.put(start, 0);

            while (!notVisited.isEmpty()) {
                Coord curr = notVisited.stream().min(Comparator.comparing(distances::get)).get();
                notVisited.remove(curr);

                updateAdjacent(curr, new Coord(curr.getX() + 1, curr.getY()), map, distances);
                updateAdjacent(curr, new Coord(curr.getX() - 1, curr.getY()), map, distances);
                updateAdjacent(curr, new Coord(curr.getX(), curr.getY() + 1), map, distances);
                updateAdjacent(curr, new Coord(curr.getX(), curr.getY() - 1), map, distances);
            }


            int lowestScore = distances.get(end);

            System.out.println("Distance to end: " + lowestScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateAdjacent(Coord curr, Coord adjacent, char[][] map, Map<Coord, Integer> distances) {
        if (isWithinBounds(adjacent, map) &&
                map[adjacent.getY()][adjacent.getX()] == SPACE &&
                distances.get(adjacent) > distances.get(curr) + 1
        ) {
            distances.put(adjacent, distances.get(curr) + 1);
        }
    }

    private static boolean isWithinBounds(Coord coord, char[][] map) {
        return coord.getX() >= 0 && coord.getX() < map[0].length && coord.getY() >= 0 && coord.getY() < map.length;
    }

    private static void printMap(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }
}
