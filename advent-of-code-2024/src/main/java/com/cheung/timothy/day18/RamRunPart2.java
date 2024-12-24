package com.cheung.timothy.day18;

import com.cheung.timothy.day06.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RamRunPart2 {

    private static final char SPACE = '.';
    private static final char WALL = '#';
    private static final int SIZE = 71;

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = RamRunPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("RamRun/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<CorruptedStatus> corruptedBytes = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                corruptedBytes.add(new CorruptedStatus(line));
            }

            int low = 0;
            int high = corruptedBytes.size() -1;
            int mid = low + (high-low)/2;
            while (!isFirstByteFound(mid, corruptedBytes)) {
                mid = low + (high-low)/2;
                int cost = getEndCost(corruptedBytes, mid +1);
                corruptedBytes.get(mid).setIsBlocking(cost < 0);
                if (corruptedBytes.get(mid).getIsBlocking()) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            System.out.println("First blocking byte: " + corruptedBytes.get(mid).getByte());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isFirstByteFound(int pointer, List<CorruptedStatus> corruptedBytes) {
        CorruptedStatus currByte = corruptedBytes.get(pointer);
        CorruptedStatus prevByte = corruptedBytes.get(pointer -1);
        return currByte.getIsBlocking() != null && currByte.getIsBlocking() && prevByte.getIsBlocking() != null && !prevByte.getIsBlocking();
    }

    private static int getEndCost(List<CorruptedStatus> corruptedBytes, int numToRead) {
        char[][] map = new char[SIZE][SIZE];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                map[y][x] = SPACE;
            }
        }
        for (int i =0; i < numToRead; i++) {
            var coordinate = corruptedBytes.get(i).getByte().split(",");
            map[Integer.parseInt(coordinate[1])][Integer.parseInt(coordinate[0])] = WALL;
        }

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


        return distances.get(end);
    }

    private static void updateAdjacent(Coord curr, Coord adjacent, char[][] map, Map<Coord, Integer> distances) {
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

class CorruptedStatus {
    private String corruptedByte;
    private Boolean isBlockingExit;

    public CorruptedStatus(String corruptedByte) {
        this.corruptedByte = corruptedByte;
    }

    public String getByte() {
        return corruptedByte;
    }

    public void setIsBlocking(Boolean isBlockingExit) {
        this.isBlockingExit = isBlockingExit;
    }

    public Boolean getIsBlocking() {
        return this.isBlockingExit;
    }
}
