package ru.job4j.kiss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

class MaxMinTest {

    @Test
    public void whenIntegerMaxEqualsFive() {
        var list = List.of(3, -2, 4, 1, 5, 0, -1);
        MaxMin maxMin = new MaxMin();
        Assertions.assertEquals(5, maxMin.max(list, Integer::compareTo));
    }

    @Test
    public void whenIntegerMinEqualsZero() {
        var list = List.of(3, 2, 4, 1, 5, 0, 1);
        MaxMin maxMin = new MaxMin();
        Assertions.assertEquals(0, maxMin.min(list, Integer::compareTo));
    }

    @Test
    public void whenStringMax() {
        var list = List.of("Ivan", "Petr", "Jakob", "Alex");
        MaxMin maxMin = new MaxMin();
        Assertions.assertEquals("Petr", maxMin.max(list, String::compareTo));
    }

    @Test
    public void whenStringMin() {
        var list = List.of("Ivan", "Petr", "Jakob", "Alex");
        MaxMin maxMin = new MaxMin();
        Assertions.assertEquals("Alex", maxMin.min(list, String::compareTo));
    }
}