package com.cheung.timothy.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistorianHysteriaPart2 {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/HistorianHysteria/input.txt");

        try {
            List<String> lines = Files.readAllLines(path);
            int[] leftList = new int[lines.size()];
            int[] rightList = new int[lines.size()];

            for (int i = 0; i < lines.size(); i++) {
                String[] splitLine = lines.get(i).split("   ");
                leftList[i] = Integer.parseInt(splitLine[0]);
                rightList[i] = Integer.parseInt(splitLine[1]);
            }

            Map<Integer, Integer> rightFrequency = new HashMap<>();
            for (int num : rightList) {
                if (rightFrequency.get(num) != null) {
                    rightFrequency.put(num, rightFrequency.get(num) + 1);
                } else {
                    rightFrequency.put(num, 1);
                }
            }

            int similarity = 0;
            for (int num : leftList) {
                Integer frequency = rightFrequency.get(num);
                if (frequency != null) {
                    similarity += num * frequency;
                }
            }
            System.out.println("Similarity: " + similarity);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
