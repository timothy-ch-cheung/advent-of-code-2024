package com.cheung.timothy.day20;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RaceConditionPart1 {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SPACE = '.';

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
                    if (isWithinBounds(coord, rows, cols) && !coord.equals(prev) && (map.get(coord.getY()).get(coord.getX()) == SPACE || map.get(coord.getY()).get(coord.getX()) == END)) {
                        prev = current;
                        current = coord;
                        break;
                    }
                }
                mainPathPicoseconds++;
            }
            path.add(current);
            pathCost.put(current, mainPathPicoseconds);
            System.out.println("Initial Cost: " + mainPathPicoseconds);

            int numCheats = 0;
            Set<Coord> usedCheats = new HashSet<>();
            for (int i = 0; i < path.size(); i++) {
                for (Coord cheat: cheatCoords(path.get(i))) {
                    if (isWithinBounds(cheat, rows, cols) && pathCost.containsKey(cheat) && !usedCheats.contains(cheat)) {
                        usedCheats.add(cheat);
                        int newCost = (mainPathPicoseconds - pathCost.get(cheat)) + i + 2;
                        System.out.println("New cost: " + newCost + " Coord: " + cheat);
                        if (newCost < mainPathPicoseconds - 100) {
                            numCheats++;
                        }
                    }
                }
            }

            System.out.println("Num Cheats over 100 picoseconds: " + numCheats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWithinBounds(Coord coord, int rows, int cols) {
        return coord.getX() >= 0 && coord.getX() < cols && coord.getY() >= 0 && coord.getY() < rows;
    }

    private static List<Coord> nextCoords(Coord curr) {
        List<Coord> coords = new ArrayList<>(4);
        coords.add(new Coord(curr.getX() + 1, curr.getY()));
        coords.add(new Coord(curr.getX() - 1, curr.getY()));
        coords.add(new Coord(curr.getX(), curr.getY() + 1));
        coords.add(new Coord(curr.getX(), curr.getY() -1));
        return coords;
    }

    private static List<Coord> cheatCoords(Coord curr) {
        List<Coord> coords = new ArrayList<>(4);
        coords.add(new Coord(curr.getX() + 2, curr.getY()));
        coords.add(new Coord(curr.getX() - 2, curr.getY()));
        coords.add(new Coord(curr.getX(), curr.getY() + 2));
        coords.add(new Coord(curr.getX(), curr.getY() -2));
        return coords;
    }
}
