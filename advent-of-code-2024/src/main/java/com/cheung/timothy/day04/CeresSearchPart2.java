package com.cheung.timothy.day04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CeresSearchPart2 {

    private static final String XMAS = "XMAS";

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = CeresSearchPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("CeresSearch/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<List<String>> allLines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(Arrays.asList(line.split("")));
            }

            int totalXmas = 0;
            for (int y = 0; y < allLines.size() - 2; y++) {
                for (int x = 0; x < allLines.get(0).size() - 2; x++) {
                    if (matches(allLines, x, y)) {
                        totalXmas++;
                    }
                }
            }

            System.out.println("Total X-MAS: " + totalXmas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean matches(List<List<String>> allLines, int xOffset, int yOffset) {
        // M   M
        //   A
        // S   S
        if ("M".equals(allLines.get(yOffset).get(xOffset)) &&
                "M".equals(allLines.get(yOffset).get(xOffset + 2)) &&
                "A".equals(allLines.get(yOffset + 1).get(xOffset + 1)) &&
                "S".equals(allLines.get(yOffset + 2).get(xOffset)) &&
                "S".equals(allLines.get(yOffset + 2).get(xOffset + 2))
        ) {
            return true;
        }

        // S   S
        //   A
        // M   M
        if ("S".equals(allLines.get(yOffset).get(xOffset)) &&
                "S".equals(allLines.get(yOffset).get(xOffset + 2)) &&
                "A".equals(allLines.get(yOffset + 1).get(xOffset + 1)) &&
                "M".equals(allLines.get(yOffset + 2).get(xOffset)) &&
                "M".equals(allLines.get(yOffset + 2).get(xOffset + 2))
        ) {
            return true;
        }

        // S   M
        //   A
        // S   M
        if ("S".equals(allLines.get(yOffset).get(xOffset)) &&
                "M".equals(allLines.get(yOffset).get(xOffset + 2)) &&
                "A".equals(allLines.get(yOffset + 1).get(xOffset + 1)) &&
                "S".equals(allLines.get(yOffset + 2).get(xOffset)) &&
                "M".equals(allLines.get(yOffset + 2).get(xOffset + 2))
        ) {
            return true;
        }

        // M   S
        //   A
        // M   S
        if ("M".equals(allLines.get(yOffset).get(xOffset)) &&
                "S".equals(allLines.get(yOffset).get(xOffset + 2)) &&
                "A".equals(allLines.get(yOffset + 1).get(xOffset + 1)) &&
                "M".equals(allLines.get(yOffset + 2).get(xOffset)) &&
                "S".equals(allLines.get(yOffset + 2).get(xOffset + 2))
        ) {
            return true;
        }
        return false;
    }
}
