package com.cheung.timothy.day19;

import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class LinenLayoutPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = ClawContraptionPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("LinenLayout/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<String> patterns = Arrays.asList(reader.readLine().split(", "));
            reader.readLine();

            int numDesigns = 0;
            while ((line = reader.readLine()) != null) {
                if (isValidDesign(line, patterns)) {
                    numDesigns++;
                }
            }

            System.out.println("Num Designs: " + numDesigns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidDesign(String design, List<String> patterns) {
        if ("".equals(design)) {
            return true;
        }

        for (String pattern: patterns) {
            if (design.startsWith(pattern)) {
                boolean isValid = isValidDesign(design.substring(pattern.length()), patterns);
                if (isValid) {
                    return true;
                }
            }
        }
        return false;
    }
}
