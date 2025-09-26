package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BenchTest {

    Bench bench = new Bench();

    @Test
    void testGetNumPigeons() {
        assertEquals(3, bench.getNumPigeons());
    }

    @Test
    void testAddPigeons() {
        bench.addPigeons(2);
        assertEquals(5, bench.getNumPigeons());
    }

    @Test
    void testSubtractPigeons() {
        bench.subtractPigeons(1);
        assertEquals(2, bench.getNumPigeons());
    }

    @Test
    void testSubtractPigeonsMin() {
        bench.subtractPigeons(4);
        assertEquals(0, bench.getNumPigeons());
    }

    @Test
    void testSetNumberPigeons() {
        bench.setNumberPigeons(5);
        assertEquals(5, bench.getNumPigeons());
    }
}
