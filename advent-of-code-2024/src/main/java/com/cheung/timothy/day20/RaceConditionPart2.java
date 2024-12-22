package com.cheung.timothy.day20;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RaceConditionPart2 {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SPACE = '.';

    private static final int CHEAT_SECONDS = 20;
    private static final int SAVE_THRESHOLD = 100;

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("RaceCondition/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<List<Character>> map = new ArrayList<>();

            int rows = 0;
            int cols = 0;
            Coord start = null;
            Coord end = null;
            while ((line = reader.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    row.add(line.charAt(i));
                    if (line.charAt(i) == START) {
                        start = new Coord(i, rows);
                    }
                    if (line.charAt(i) == END) {
                        end = new Coord(i, rows);
                    }
                }
                rows++;
                cols = line.length();
                map.add(row);
            }

            assert start != null;
            assert end != null;
            assert cols > 0;

            int mainPathPicoseconds = 0;
            Coord current = new Coord(start.getX(), start.getY());
            Coord prev = null;
            List<Coord> path = new ArrayList<>();
            Map<Coord, Integer> pathCost = new HashMap<>();
            while (!current.equals(end)) {
                path.add(current);
                pathCost.put(current, mainPathPicoseconds);
                List<Coord> nextCoords = nextCoords(current);
                for (Coord coord: nextCoords) {
                    if (isWithinBounds(coord, map) && !coord.equals(prev) && (map.get(coord.getY()).get(coord.getX()) == SPACE || map.get(coord.getY()).get(coord.getX()) == END)) {
                        prev = current;
                        current = coord;
                        break;
                    }
                }
                mainPathPicoseconds++;
            }
            path.add(current);
            pathCost.put(current, mainPathPicoseconds);
            System.out.println("Initial Cost: " + mainPathPicoseconds + "\n");

            int numCheats = 0;
            Set<CheatCoord> usedCheats = new HashSet<>();
            for (int i = 0; i < path.size(); i++) {
                Map<CheatCoord, Integer> cheatCoords = findCheatCoords(path.get(i), map);
                for (Map.Entry<CheatCoord, Integer> cheatEntry: cheatCoords.entrySet()) {
                    if (!usedCheats.contains(cheatEntry.getKey())) {
                        usedCheats.add(cheatEntry.getKey());
                        int newCost = (mainPathPicoseconds - pathCost.get(cheatEntry.getKey().resultCoord)) + i + cheatEntry.getValue();
                        int saves = mainPathPicoseconds - newCost;
                        if (saves >= SAVE_THRESHOLD) {
                            // System.out.println("Saves: %s picoseconds [Start Cheat=%s] [End Cheat=%s]".formatted(saves, cheatEntry.getKey().cheatCoord, cheatEntry.getKey().resultCoord));
                            numCheats++;
                        }
                    }
                }
            }
            System.out.println("\n");
            System.out.println("Num Cheats that save at least %s picoseconds: %s".formatted(SAVE_THRESHOLD, numCheats));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWithinBounds(Coord coord, List<List<Character>> map) {
        return coord.getX() >= 0 && coord.getX() < map.get(0).size() && coord.getY() >= 0 && coord.getY() < map.size();
    }

    private static List<Coord> nextCoords(Coord curr) {
        List<Coord> coords = new ArrayList<>(4);
        coords.add(new Coord(curr.getX() + 1, curr.getY()));
        coords.add(new Coord(curr.getX() - 1, curr.getY()));
        coords.add(new Coord(curr.getX(), curr.getY() + 1));
        coords.add(new Coord(curr.getX(), curr.getY() -1));
        return coords;
    }

    private static Map<CheatCoord, Integer> findCheatCoords(Coord curr, List<List<Character>> map) {
        Map<CheatCoord, Integer> cheatCoords = new HashMap<>();

        int minX = Math.max(0, curr.getX() - CHEAT_SECONDS);
        int maxX = Math.min(map.get(0).size(), curr.getX() + CHEAT_SECONDS + 1);
        int minY = Math.max(0, curr.getY() - CHEAT_SECONDS);
        int maxY = Math.min(map.size(), curr.getY() + CHEAT_SECONDS + 1);

        for (int y = minY; y < maxY; y++) {
            for (int x = minX; x < maxX; x++) {
                int distance = Math.abs(curr.getX() - x) + Math.abs(curr.getY() - y);
                if (distance > 20) {
                    continue;
                }
                if (map.get(y).get(x) == SPACE || map.get(y).get(x) == END) {
                    cheatCoords.put(new CheatCoord(curr, new Coord(x, y)), distance);
                }
            }
        }
        return cheatCoords;
    }
}

