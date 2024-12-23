package com.cheung.timothy.day19;

import com.cheung.timothy.day13.ClawContraptionPart1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LinenLayoutPart2 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = LinenLayoutPart2.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("LinenLayout/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            List<String> patterns = Arrays.asList(reader.readLine().split(", "));
            reader.readLine();

            long numDesigns = 0;
            Map<String, Long> cache = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                long designs = countDesigns(line, patterns, cache);
                System.out.println("Pattern " + line + " has num designs: "+ designs);
                numDesigns += designs;
            }

            System.out.println("Num Designs: " + numDesigns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long countDesigns(String design, List<String> patterns, Map<String, Long> cache) {
        if ("".equals(design)) {
            return 1;
        }

        long numDesigns = 0;
        for (String pattern: patterns) {
            if (design.startsWith(pattern)) {
                String remainingDesign = design.substring(pattern.length());
                Long count = cache.get(remainingDesign);
                if (count != null) {
                    numDesigns += count;
                } else {
                    count = countDesigns(design.substring(pattern.length()), patterns, cache);
                    cache.put(remainingDesign, count);
                    numDesigns += count;
                }
            }
        }
        return numDesigns;
    }
}
