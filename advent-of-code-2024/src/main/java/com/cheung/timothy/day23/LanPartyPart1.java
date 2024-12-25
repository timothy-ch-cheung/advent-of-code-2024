package com.cheung.timothy.day23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class LanPartyPart1 {

    public static void main(String[] args) throws IOException {

        ClassLoader classLoader = LanPartyPart1.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("LanParty/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            Map<String, Set<String>> computers = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                String[] curr = line.split("-");
                addComputer(curr[0], curr[1], computers);
                addComputer(curr[1], curr[0], computers);
            }

            Set<Set<String>> lanParties = new HashSet<>();
            for (Map.Entry<String, Set<String>> connectionEntry: computers.entrySet()) {
                for (String secondComputer: connectionEntry.getValue()) {
                    for (String thirdComputer: computers.get(secondComputer)) {
                        if (!thirdComputer.equals(connectionEntry.getKey()) && computers.get(thirdComputer).contains(connectionEntry.getKey())) {
                            lanParties.add(new HashSet<>(Arrays.asList(connectionEntry.getKey(), secondComputer, thirdComputer)));
                        }
                    }
                }
            }

            int totalLan = lanParties.stream().filter(party -> !party.stream().filter(comp -> comp.startsWith("t")).toList().isEmpty()).toList().size();
            System.out.println("Total Pairs: " + totalLan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addComputer(String comp1, String comp2, Map<String, Set<String>> computers) {
        if (computers.containsKey(comp1)) {
            computers.get(comp1).add(comp2);
        } else {
            Set<String> connections = new HashSet<>();
            connections.add(comp2);
            computers.put(comp1, connections);
        }
    }
}
