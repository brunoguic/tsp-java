package com.brunoguic.tsp;

import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomSearchTSP {

    private final List<Tsp.Node> cities;
    private final int rounds;

    private class Solution {
        List<Tsp.Node> cities;

        public Solution(List<Tsp.Node> cities) {
            this.cities = cities;
        }
    }

    public RandomSearchTSP(List<Tsp.Node> cities, int rounds) {
        this.cities = cities;
        this.rounds = rounds;
    }

    private double cost(Solution solution) {
        double cost = 0.0;
        int n = solution.cities.size();
        for (int idx = 0; idx < n; idx++) {
            Tsp.Node curr = solution.cities.get(idx);
            Tsp.Node next = solution.cities.get((idx + 1) % n);
            cost += MathUtils.dist2d(curr.getX(), curr.getY(), next.getX(), next.getY());
        }
        return cost;
    }

    public double run() {
        Solution best = null;
        for (int round = 0; round < rounds; round++) {
            Solution solution = new Solution(new ArrayList<>(cities));
            Collections.shuffle(solution.cities);

            if (best == null || cost(solution) < cost(best)) {
                best = solution;
            }
        }
        return cost(best);
    }

    public static void main(String[] args) {
        Tsp tsp = TspLibParser.parseTsp("./tsplib/eil51.tsp");
        RandomSearchTSP randomSearchTSP = new RandomSearchTSP(tsp.getNodes().get(), 1000);
        System.out.println(randomSearchTSP.run());
    }
}
