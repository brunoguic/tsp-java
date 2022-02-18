package com.brunoguic.tsp;

import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Glover, F., 1986. Future paths for integer programming and links to artificial intelligence.
 * Computers & Operations Research, 13(5), 533–549.
 *
 * It keeps around a history of recently considered candidate solutions (known as the tabu list) and refuses to return
 * to those candidate solutions until they’re sufficiently far in the past. Thus if we wander up a hill, we have no
 * choice but to wander back down the other side because we’re not permitted to stay at or return to the top of the
 * hill.
 */
public class TabuSearchTSP {

    private final List<Tsp.Node> cities;
    // number of temperatures
    private final int K;
    // number of round at each temperature
    private final int MK;
    // intial temperature
    private final double T0;

    // temperature cooling
    private final double B;

    private class Solution {
        List<Tsp.Node> cities;

        public Solution(List<Tsp.Node> cities) {
            this.cities = cities;
        }
    }

    public TabuSearchTSP(List<Tsp.Node> cities, int K, int MK, double T0, double B) {
        this.cities = cities;
        this.K = K;
        this.MK = MK;
        this.T0 = T0;
        // Ensure (0,1)
        this.B = Math.min(Math.max(MathUtils.EPS, B), 1 - MathUtils.EPS);
    }

    private double cost(Solution solution) {
        double cost = 0.0;
        int n = solution.cities.size();
        for (int idx = 0; idx < n; idx++) {
            Tsp.Node curr = solution.cities.get(idx);
            Tsp.Node next = solution.cities.get((idx + 1) % n);
            cost += dist2d(curr, next);
        }
        return cost;
    }

    private double dist2d(Tsp.Node a, Tsp.Node b) {
        return MathUtils.dist2d(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public double run() {
        Random random = new Random();
        // Initial solution | CONSTRUCTIVE
        Solution best = new Solution(new ArrayList<>(cities));
        Collections.shuffle(best.cities);

        double temp = T0;
        for(int k = 0; k < K; k++) {
            for (int m = 0; m < MK; m++) {
                int targetIdx = random.nextInt(cities.size());
                int otherIdx = random.nextInt(cities.size());
                Solution curr = new Solution(new ArrayList<>(best.cities));
                Collections.swap(curr.cities, targetIdx, otherIdx);

                double bestCost = cost(best);
                double currCost = cost(curr);
                double delta = currCost - bestCost;

                // curr is better OR accept with probability
                if (delta <= 0 || random.nextFloat() <= Math.exp(-delta/temp)) {
                    best = curr;
                }
            }
            // high temp: random search
            // low temp: hill climbing
            temp *= B;
        }

        return cost(best);
    }

    public static void main(String[] args) {
        Tsp tsp = TspLibParser.parseTsp("./tsplib/eil51.tsp");
        TabuSearchTSP randomSearchTSP =
                new TabuSearchTSP(tsp.getNodes().get(), 100, 100, 20, 0.5);
        System.out.println(randomSearchTSP.run());
    }
}
