package com.brunoguic.tsp;

public class MathUtils {

    public static final double EPS = 0.00001;

    public static double dist2d(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static boolean isEquals(double expected, double value) {
        return Math.abs(expected - value) <= EPS;
    }

    public static boolean isNotEquals(double expected, double value) {
        return !isEquals(expected, value);
    }
}
