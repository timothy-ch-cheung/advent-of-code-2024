package com.cheung.timothy.day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DiskFragmenterPart1 {

    private static String SPACE = ".";

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = DiskFragmenterPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("DiskFragmenter/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String diskMap = reader.readLine();

            int fileId = 0;
            boolean isFile = true;
            List<String> disk = new ArrayList<>();
            for (int i = 0; i < diskMap.length(); i++) {
                String curr = isFile ? Integer.toString(fileId++) : SPACE;
                for (int j = 0; j < Integer.parseInt(diskMap.substring(i, i+1)); j++) {
                    disk.add(curr);
                }
                isFile = !isFile;
            }

            int left = 0;
            int right = disk.size() - 1;
            while (left < right) {
                while (!disk.get(left).equals(SPACE)) {
                    left++;
                }
                while (disk.get(right).equals(SPACE)) {
                    right--;
                }
                if (left < right) {
                    disk.set(left, disk.get(right));
                    disk.set(right, SPACE);
                }
            }

            long checksum = 0;
            int idx = 0;
            while (!SPACE.equals(disk.get(idx))) {
                checksum += idx * Long.parseLong(disk.get(idx));
                idx++;
            }

            System.out.println("Checksum: " + checksum);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
