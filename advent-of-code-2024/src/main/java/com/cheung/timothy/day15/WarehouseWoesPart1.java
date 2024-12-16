package com.cheung.timothy.day15;

import com.cheung.timothy.day06.Coord;
import com.cheung.timothy.day13.ClawContraptionPart1;
import com.cheung.timothy.day13.Delta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WarehouseWoesPart1 {

    private static final char WALL = '#';
    private static final char BOX = 'O';
    private static final char SPACE = '.';
    private static final char ROBOT = '@';

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        int ROWS = 0;
        int COLUMNS = 0;
        List<List<Character>> map = new ArrayList<>();
        Coord robotCoord = null;
        String instructions = "";

        try (InputStream inputStream = classLoader.getResourceAsStream("WarehouseWoes/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while (!"".equals(line = reader.readLine())) {
                COLUMNS = line.length();
                List<Character> row = new ArrayList<>();
                for (int i = 0; i < COLUMNS; i++) {
                    row.add(line.charAt(i));
                    if (line.charAt(i) == ROBOT) {
                        robotCoord = new Coord(i, ROWS);
                    }
                }
                map.add(row);
                ROWS++;
            }

            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            instructions = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        printMap(map);

        for (int i = 0; i < instructions.length(); i++) {
            Character currInstruction = instructions.charAt(i);
            Delta delta = null;

            switch (currInstruction) {
                case '^': {
                    delta = new Delta(0, -1);
                    break;
                }
                case 'v': {
                    delta = new Delta(0, 1);
                    break;
                }
                case '<': {
                    delta = new Delta(-1, 0);
                    break;
                }
                case '>': {
                    delta = new Delta(1, 0);
                    break;
                }
            }
            assert delta != null;
            assert robotCoord != null;

            int move = 1;
            Coord tmp = new Coord(robotCoord.getX(), robotCoord.getY());
            while (withinBounds(tmp, ROWS, COLUMNS) && map.get(tmp.getY()).get(tmp.getX()) != SPACE && map.get(tmp.getY()).get(tmp.getX()) != WALL) {
                tmp.apply(delta);
                move++;
            }
            if (!withinBounds(tmp, ROWS, COLUMNS) || map.get(tmp.getY()).get(tmp.getX()) != SPACE) {
                move = 0;
            }
            tmp = new Coord(robotCoord.getX(), robotCoord.getY());
            char fill = SPACE;
            for (int j = 0; j < move; j++) {
                char tmpChar = map.get(tmp.getY()).get(tmp.getX());
                map.get(tmp.getY()).set(tmp.getX(), fill);
                fill = tmpChar;
                tmp.apply(delta);
            }
            if (move != 0) {
                robotCoord.apply(delta);
            }
            System.out.println("Move: " + currInstruction);
            // printMap(map);
        }

        int gps = 0;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Character curr = map.get(y).get(x);
                System.out.print(curr);
                if (curr == BOX) {
                    gps += (100 * y) + x;
                }
            }
            System.out.println();
        }
        System.out.println("GPS: " + gps);
    }

    private static void printMap(List<List<Character>> map) {
        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(0).size(); x++) {
                System.out.print(map.get(y).get(x));
            }
            System.out.println();
        }
        System.out.println();
    }

    private static boolean withinBounds(Coord coord, int rows, int cols) {
        return coord.getX() >= 0 && coord.getX() < cols && coord.getY() >= 0 && coord.getY() < rows;
    }
}
