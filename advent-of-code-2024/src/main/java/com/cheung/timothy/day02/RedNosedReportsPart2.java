package com.cheung.timothy.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RedNosedReportsPart2 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/RedNosedReports/input.txt");

        try {
            List<String> lines = Files.readAllLines(path);

            int safeNum = 0;
            for (int i = 0; i < lines.size(); i++) {
                List<Integer> levels = Arrays.stream(lines.get(i).split(" ")).map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
                if (isSafe(levels)) {
                    safeNum++;
                } else {
                    Collections.reverse(levels);
                    if (isSafe(levels)) {
                        safeNum++;
                    }
                }
            }

            System.out.println("Total Safe: " + safeNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isSafe(List<Integer> levels) {
        boolean safe = true;
        int prev = levels.get(0);
        boolean increasing = prev < levels.get(1);
        boolean tolerate = true;
        for (int i = 1; i < levels.size(); i++) {
            int curr = levels.get(i);
            int diff = curr - prev;
            boolean skip = false;
            if (increasing && (diff < 1 || diff > 3)) {
                if (tolerate) {
                    tolerate = false;
                    skip = true;
                } else {
                    safe = false;
                    break;
                }
            } else if (!increasing && (diff > -1 || diff < -3)) {
                if (tolerate) {
                    tolerate = false;
                    skip = true;
                } else {
                    safe = false;
                    break;
                }
            }
            if (!skip) {
                prev = curr;
            }
        }
        return safe;
    }
}
