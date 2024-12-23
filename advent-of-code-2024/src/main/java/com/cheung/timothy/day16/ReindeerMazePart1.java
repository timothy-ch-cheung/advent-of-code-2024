package com.cheung.timothy.day16;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ReindeerMazePart1 {

    private static final char START = 'S';
    private static final char END = 'E';
    private static final char SPACE = '.';
    private static final char WALL = '#';

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ReindeerMazePart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("ReindeerMaze/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<List<Character>> map = new ArrayList<>();
            int rows = 0;
            int columns = 0;
            Coord start = null;

            while ((line = reader.readLine()) != null) {
                List<Character> row = new ArrayList<>();
                columns = line.length();
                for (int i = 0; i < line.length(); i++) {
                    row.add(line.charAt(i));
                    if (line.charAt(i) == START) {
                        start = new Coord(i, rows);
                    }
                }
                map.add(row);
                rows++;
            }
            assert rows > 0;
            assert columns > 0;
            assert start != null;

            List<List<Coord>> routes = new ArrayList<>();
            Queue<Coord> queue = new ArrayDeque<>();
            Set<Coord> visited = new HashSet<>();


            int lowestScore = Integer.MAX_VALUE;

            System.out.println("Lowest Score: " + lowestScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isWithinBounds(Coord coord, List<List<Character>> map) {
        return coord.getX() >= 0 && coord.getX() < map.get(0).size() && coord.getY() >= 0 && coord.getY() < map.size();
    }
}
