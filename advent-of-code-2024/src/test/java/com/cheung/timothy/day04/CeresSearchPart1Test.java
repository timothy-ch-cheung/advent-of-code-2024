package com.cheung.timothy.day04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

class CeresSearchPart1Test {

    private List<List<String>> map1;
    private List<List<String>> map2;

    @BeforeEach
    void setup() {
        this.map1 = Arrays.asList(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("D", "E", "F"),
                Arrays.asList("G", "H", "I")
        );
        this.map2 = Arrays.asList(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("D", "E", "F"),
                Arrays.asList("G", "H", "I"),
                Arrays.asList("J", "K", "L")
        );
    }

    @Test
    void getLeftDiagonals1() {
        List<List<String>> diagonals = CeresSearchPart1.getLeftDiagonals(map1);
        assertThat(diagonals.size(), is(5));
        assertThat(diagonals.get(0), equalTo(Arrays.asList("A", "E", "I")));
        assertThat(diagonals.get(1), equalTo(Arrays.asList("D", "H")));
        assertThat(diagonals.get(2), equalTo(Arrays.asList("G")));
        assertThat(diagonals.get(3), equalTo(Arrays.asList("B", "F")));
        assertThat(diagonals.get(4), equalTo(Arrays.asList("C")));
    }

    @Test
    void getRightDiagonals1() {
        List<List<String>> diagonals = CeresSearchPart1.getRightDiagonals(map1);
        assertThat(diagonals.size(), is(5));
        assertThat(diagonals.get(0), equalTo(Arrays.asList("C", "E", "G")));
        assertThat(diagonals.get(1), equalTo(Arrays.asList("B", "D")));
        assertThat(diagonals.get(2), equalTo(Arrays.asList("A")));
        assertThat(diagonals.get(3), equalTo(Arrays.asList("F", "H")));
        assertThat(diagonals.get(4), equalTo(Arrays.asList("I")));
    }

    @Test
    void getLeftDiagonals2() {
        List<List<String>> diagonals = CeresSearchPart1.getLeftDiagonals(map2);
        assertThat(diagonals.size(), is(6));
        assertThat(diagonals.get(0), equalTo(Arrays.asList("A", "E", "I")));
        assertThat(diagonals.get(1), equalTo(Arrays.asList("D", "H", "L")));
        assertThat(diagonals.get(2), equalTo(Arrays.asList("G", "K")));
        assertThat(diagonals.get(3), equalTo(Arrays.asList("J")));
        assertThat(diagonals.get(4), equalTo(Arrays.asList("B", "F")));
        assertThat(diagonals.get(5), equalTo(Arrays.asList("C")));
    }

    @Test
    void getRightDiagonals2() {
        List<List<String>> diagonals = CeresSearchPart1.getRightDiagonals(map2);
        assertThat(diagonals.size(), is(6));
        assertThat(diagonals.get(0), equalTo(Arrays.asList("C", "E", "G")));
        assertThat(diagonals.get(1), equalTo(Arrays.asList("B", "D")));
        assertThat(diagonals.get(2), equalTo(Arrays.asList("A")));
        assertThat(diagonals.get(3), equalTo(Arrays.asList("F", "H", "J")));
        assertThat(diagonals.get(4), equalTo(Arrays.asList("I", "K")));
        assertThat(diagonals.get(5), equalTo(Arrays.asList("L")));
    }
}