package com.cheung.timothy.day05;

import com.cheung.timothy.day04.CeresSearchPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class PrintQueuePart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = CeresSearchPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("PrintQueue/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            Map<Integer, List<Integer>> rules = new HashMap();

            String line;
            while (!(line = reader.readLine()).equals("")) {
                String[] rule = line.split("\\|");

                Integer smaller = Integer.parseInt(rule[0]);
                Integer larger = Integer.parseInt(rule[1]);
                if (rules.containsKey(smaller)) {
                    rules.get(smaller).add(larger);
                } else {
                    List<Integer> largerNums = new ArrayList<>();
                    largerNums.add(larger);
                    rules.put(smaller, largerNums);
                }
            }

            int middlePageTotal = 0;
            while ((line = reader.readLine()) != null) {
                List<Integer> pages = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
                Map<Integer, Integer> pageIndexes = new HashMap<>();
                for (int i = 0; i < pages.size(); i++) {
                    pageIndexes.put(pages.get(i), i);
                }
                boolean correctlyOrdered = true;
                for (int i = 0; i < pages.size(); i++) {
                    Integer currentPage = pages.get(i);
                    for (int largerNum: rules.get(currentPage)) {
                        if (pageIndexes.containsKey(largerNum) && pageIndexes.get(largerNum) < i) {
                            correctlyOrdered = false;
                        }
                    }
                }
                if (correctlyOrdered) {
                    middlePageTotal += pages.get(pages.size()/2);
                }
            }
            System.out.println("Middle Page Total: " + middlePageTotal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
