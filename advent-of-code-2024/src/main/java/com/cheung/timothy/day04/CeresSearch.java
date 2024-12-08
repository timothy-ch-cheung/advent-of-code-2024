package com.cheung.timothy.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CeresSearch {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = CeresSearch.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("CeresSearch/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<List<String>> horizontals = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                horizontals.add(Arrays.asList(line.split("")));
            }

            List<List<String>> verticals = getVerticals(horizontals);
            List<List<String>> diagonals = getDiagonals(horizontals);
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

    private static List<List<String>> getDiagonals(List<List<String>> horizontals) {
        List<List<String>> diagonals = new ArrayList<>();
        for (int j = 0; j < horizontals.size(); j++) {

        }
        return diagonals;
    }
}
