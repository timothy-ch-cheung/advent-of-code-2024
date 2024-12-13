package com.cheung.timothy.day10;

import com.cheung.timothy.day06.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HoofItPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = HoofItPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("HoofIt/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<List<Integer>> map = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                List<Integer> row = Arrays.stream(line.split("")).map(Integer::parseInt).toList();
                map.add(row);
            }
            List<Coord> startingPoints = new ArrayList<>();
            for (int y = 0; y < map.size(); y++) {
                for (int x = 0; x < map.get(0).size(); x++) {
                    if (map.get(y).get(x) == 0) {
                        startingPoints.add(new Coord(x, y));
                    }
                }
            }

            int totalTrail = 0;
            for (Coord start : startingPoints) {
                Set<Coord> trailTails = new HashSet<>();
                findTrails(start, 0, map, trailTails);
                totalTrail += trailTails.size();
            }

            System.out.println("Trail Score: " + totalTrail);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findTrails(Coord coord, int level, List<List<Integer>> map, Set<Coord> trailTails) {
        if (map.get(coord.getY()).get(coord.getX()) == 9) {
            trailTails.add(coord);
        }

        if (coord.getY() + 1 < map.size() && map.get(coord.getY() + 1).get(coord.getX()) == level + 1) {
            findTrails(new Coord(coord.getX(), coord.getY() + 1), level + 1, map, trailTails);
        }
        if (coord.getY() - 1 >= 0 && map.get(coord.getY() - 1).get(coord.getX()) == level + 1) {
            findTrails(new Coord(coord.getX(), coord.getY() - 1), level + 1, map, trailTails);
        }
        if (coord.getX() + 1 < map.get(0).size() && map.get(coord.getY()).get(coord.getX() + 1) == level + 1) {
            findTrails(new Coord(coord.getX() + 1, coord.getY()), level + 1, map, trailTails);
        }
        if (coord.getX() - 1 >= 0 && map.get(coord.getY()).get(coord.getX() - 1) == level + 1) {
            findTrails(new Coord(coord.getX() - 1, coord.getY()), level + 1, map, trailTails);
        }
    }
}
