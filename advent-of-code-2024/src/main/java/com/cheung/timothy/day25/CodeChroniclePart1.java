package com.cheung.timothy.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
                int linesRead = 0;
                while ((line = reader.readLine()) != null && !line.isEmpty() && linesRead < 5) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '#') {
                            curr.set(i, curr.get(i) + 1);
                        }
                    }
                }
                reader.readLine();

                if (isKey) {
                    keys.add(curr);
                } else {
                    locks.add(curr);
                }
            }

            int matches = 0;
            for (List<Integer> lock : locks) {
                for (List<Integer> key : keys) {
                    if (fits(lock, key)) {
                        matches++;
                    }
                }
            }

            System.out.println("Total Pairs: " + matches);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean fits(List<Integer> lock, List<Integer> key) {
        for (int i = 0; i < lock.size(); i++) {
            if (lock.get(i) + key.get(i) > 5) {
                return false;
            }
        }
        return true;
    }
}
