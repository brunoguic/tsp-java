package com.brunoguic.tsp;

import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HillClimbingTSP {

    private final List<Tsp.Node> cities;

    private class Solution {
        List<Tsp.Node> cities;

        public Solution(List<Tsp.Node> cities) {
            this.cities = cities;
        }
    }

    public HillClimbingTSP(List<Tsp.Node> cities) {
        this.cities = cities;
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

        while(true) {
            int targetIdx = random.nextInt(cities.size());

            int bestOtherIdx = -1;
            double bestOtherCost = Double.MAX_VALUE;
            Solution curr = new Solution(new ArrayList<>(best.cities));
            for (int otherIdx = 0; otherIdx < cities.size(); otherIdx++){
                if (otherIdx == targetIdx) continue;

                Collections.swap(curr.cities, targetIdx, otherIdx);
                double currCost = cost(curr);
                // NEIGHBORS SELECTION: best improvement
                if (currCost < bestOtherCost) {
                    bestOtherIdx = otherIdx;
                    bestOtherCost = currCost;
                }
                Collections.swap(curr.cities, otherIdx, targetIdx);
            }

            // STOP CRITERIA: no more improvement
            if (bestOtherCost < cost(best)) {
                // make swap
                Collections.swap(best.cities, targetIdx, bestOtherIdx);
            } else {
                break;
            }
        }

        return cost(best);
    }

    public static void main(String[] args) {
        Tsp tsp = TspLibParser.parseTsp("./tsplib/eil51.tsp");
        HillClimbingTSP randomSearchTSP = new HillClimbingTSP(tsp.getNodes().get());
        System.out.println(randomSearchTSP.run());
    }
}
