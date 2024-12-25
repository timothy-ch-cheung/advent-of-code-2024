package com.cheung.timothy.day25;

import com.cheung.timothy.day21.KeypadConundrumPart1;
import com.cheung.timothy.day22.MonkeyMarketPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CodeChroniclePart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = CodeChroniclePart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("CodeChronicle/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<List<Integer>> keys = new ArrayList<>();
            List<List<Integer>> locks = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                boolean isKey = false;
                if (line.contains(".")) {
                    isKey = true;
                }
                List<Integer> curr = new ArrayList<>(line.length());
                for (int i = 0; i < line.length(); i++) {
                    curr.add(0);
                }
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '#') {
                            curr.set(i, curr.get(i) + 1);
                        }
                    }
                }
                for (int i = 0; i < curr.size(); i++) {
                    curr.set(i, curr.get(i) - 1);
                }
                if (isKey) {
                    keys.add(curr);
                } else {
                    locks.add(curr);
                }
            }

            int totalPairs = 0;
            for (List<Integer> key: keys) {
                if (locks.contains(toLock(key))) {
                    totalPairs++;
                }
            }

            System.out.println("Total Pairs: " + totalPairs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> toLock(List<Integer> key) {
        List<Integer> lock = new ArrayList<>(key.size());
        for(Integer depth: key) {
            lock.add(5-depth);
        }
        return lock;
    }
}
