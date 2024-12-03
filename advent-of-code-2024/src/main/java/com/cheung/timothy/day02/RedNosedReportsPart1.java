package com.cheung.timothy.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RedNosedReportsPart1 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/RedNosedReports/input.txt");

        try {
            List<String> lines = Files.readAllLines(path);

            int safeNum = 0;
            for (int i = 0; i < lines.size(); i++) {
                String[] levels = lines.get(i).split(" ");
                boolean safe = true;
                int prev = Integer.parseInt(levels[0]);
                boolean increasing = prev < Integer.parseInt(levels[1]);
                for (int j = 1; j < levels.length; j++) {
                    int curr = Integer.parseInt(levels[j]);
                    int diff = curr - prev;
                    if (increasing && (diff < 1 || diff > 3)) {
                        safe = false;
                        break;
                    } else if (!increasing && (diff > -1 || diff < -3)){
                        safe = false;
                        break;
                    }
                    prev = curr;
                }
                if (safe) {
                    safeNum++;
                }
            }

            System.out.println("Total Safe: " + safeNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
