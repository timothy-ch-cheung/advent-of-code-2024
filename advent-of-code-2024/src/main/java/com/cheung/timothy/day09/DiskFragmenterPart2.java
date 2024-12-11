package com.cheung.timothy.day09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DiskFragmenterPart2 {

    private static final String SPACE = ".";

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = DiskFragmenterPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("DiskFragmenter/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String diskMap = reader.readLine();

            int fileId = 0;
            boolean isFile = true;
            List<String> disk = new ArrayList<>();
            List<Block> spaces = new ArrayList<>();
            List<Block> files = new ArrayList<>();
            for (int i = 0; i < diskMap.length(); i++) {
                String curr = isFile ? Integer.toString(fileId++) : SPACE;
                int size = Integer.parseInt(diskMap.substring(i, i + 1));
                if (!isFile) {
                    spaces.add(new Block(size, disk.size(), curr));
                } else {
                    files.add(new Block(size, disk.size(), curr));
                }
                for (int j = 0; j < size; j++) {
                    disk.add(curr);
                }
                isFile = !isFile;
            }

            fileId--;
            for (int i = fileId; i >= 0; i--) {
                Block file = files.get(i);
                Block space = null;
                for (int j = 0; j < spaces.size(); j++) {
                    if (spaces.get(j).size >= file.size && spaces.get(j).start < file.start) {
                        space = spaces.get(j);
                        break;
                    }
                }
                if (space != null) {
                    for (int j = space.start; j < space.start + file.size; j++) {
                        disk.set(j, file.character);
                    }
                    for (int j = file.start; j < file.start + file.size; j++) {
                        disk.set(j, SPACE);
                    }
                    file.start = space.start;
                    space.start += file.size;
                    space.size -= file.size;
                }
            }


            long checksum = 0;
            for (int i = 0; i < disk.size(); i++) {
                if (disk.get(i).equals(SPACE)) {
                    continue;
                }
                checksum += i * Long.parseLong(disk.get(i));
            }
            System.out.println("Checksum: " + checksum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Block {
        private int size;
        private int start;
        private String character;

        public Block(int size, int start, String character) {
            this.size = size;
            this.start = start;
            this.character = character;
        }
    }
}
