package com.brunoguic.tsp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {

    @Test
    void dist2d() {
        assertEquals(5.0, MathUtils.dist2d(3,0,0,4), MathUtils.EPS);
    }
}