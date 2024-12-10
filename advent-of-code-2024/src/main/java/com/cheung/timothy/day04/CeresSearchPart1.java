package com.cheung.timothy.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CeresSearchPart1 {

    private static final String XMAS = "XMAS";

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = CeresSearchPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("CeresSearch/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<List<String>> allLines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(Arrays.asList(line.split("")));
            }

            List<List<String>> verticals = getVerticals(allLines);
            List<List<String>> leftDiagonals = getLeftDiagonals(allLines);
            List<List<String>> rightDiagonals = getRightDiagonals(allLines);

            allLines.addAll(verticals);
            allLines.addAll(leftDiagonals);
            allLines.addAll(rightDiagonals);

            int totalXmas = 0;
            for (List<String> currLine : allLines) {
                totalXmas += countXmas(currLine);
                Collections.reverse(currLine);
                totalXmas += countXmas(currLine);
            }
            System.out.println("Total XMAS: " + totalXmas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<List<String>> getVerticals(List<List<String>> horizontals) {
        List<List<String>> verticals = new ArrayList<>();
        for (int j = 0; j < horizontals.get(0).size(); j++) {
            List<String> vertical = new ArrayList<>();
            for (int i = 0; i < horizontals.size(); i++) {
                vertical.add(horizontals.get(i).get(j));
            }
            verticals.add(vertical);
        }
        return verticals;
    }

    static List<List<String>> getLeftDiagonals(List<List<String>> horizontals) {
        List<List<String>> diagonals = new ArrayList<>();
        List<String> centerDiagonal = new ArrayList<>();
        for (int i = 0; i < horizontals.get(0).size(); i++) {
            centerDiagonal.add(horizontals.get(i).get(i));
        }
        diagonals.add(centerDiagonal);

        for (int i = 1; i < horizontals.size(); i++) {
            List<String> diagonal = new ArrayList<>();
            int x = 0;
            for (int y = i; y < horizontals.size(); y++) {
                diagonal.add(horizontals.get(y).get(x));
                x++;
                if (x > horizontals.get(0).size() - 1) {
                    break;
                }
            }
            diagonals.add(diagonal);
        }
        for (int i = 1; i < horizontals.get(0).size(); i++) {
            List<String> diagonal = new ArrayList<>();
            int y = 0;
            for (int x = i; x < horizontals.get(0).size(); x++) {
                diagonal.add(horizontals.get(y).get(x));
                y++;
            }
            diagonals.add(diagonal);
        }

        return diagonals;
    }

    static List<List<String>> getRightDiagonals(List<List<String>> horizontals) {
        List<List<String>> diagonals = new ArrayList<>();
        List<String> centerDiagonal = new ArrayList<>();
        int y1 = 0;
        for (int x = horizontals.get(0).size() - 1; x >= 0; x--) {
            centerDiagonal.add(horizontals.get(y1).get(x));
            y1++;
        }
        diagonals.add(centerDiagonal);

        for (int i = horizontals.get(0).size() - 2; i >= 0; i--) {
            List<String> diagonal = new ArrayList<>();
            int y = 0;
            for (int x = i; x >= 0; x--) {
                diagonal.add(horizontals.get(y).get(x));
                y++;
            }
            diagonals.add(diagonal);
        }

        for (int i = 1; i < horizontals.size(); i++) {
            List<String> diagonal = new ArrayList<>();
            int x = horizontals.get(0).size() - 1;
            for (int y = i; y < horizontals.size(); y++) {
                diagonal.add(horizontals.get(y).get(x));
                x--;
                if (x < 0) {
                    break;
                }
            }
            diagonals.add(diagonal);
        }

        return diagonals;
    }

    private static int countXmas(List<String> line) {
        int count = 0;
        int index = 0;
        for (String character : line) {
            if (XMAS.substring(index, index + 1).equals(character)) {
                index++;
            } else {
                index = 0;
                if (XMAS.substring(index, index + 1).equals(character)) {
                    index++;
                }
            }

            if (index == XMAS.length()) {
                index = 0;
                count++;
            }
        }
        return count;
    }
}
