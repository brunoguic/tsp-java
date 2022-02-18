package com.brunoguic.tsp;

import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IteratedLocalSearchILS {

    private final List<Tsp.Node> cities;

    private final Random random;

    private class Solution {
        List<Tsp.Node> cities;

        public Solution(List<Tsp.Node> cities) {
            this.cities = cities;
        }
    }

    public IteratedLocalSearchILS(List<Tsp.Node> cities) {
        this.cities = cities;
        this.random = new Random(42);
    }

    private void localSearch(Solution solution) {

        boolean isFinished = false;
        Solution incumbent = solution;
        int n = solution.cities.size();
        do {
            isFinished = true;
            int targetIdx = random.nextInt(cities.size());
            int otherIdx = random.nextInt(cities.size());
            Solution curr = new Solution(new ArrayList<>(n));
            Collections.swap(curr.cities, targetIdx, otherIdx);

            if (cost)



        } while(!isFinished);
    }


    private double run() {
        // generate initial solution
        Solution best = new Solution(new ArrayList<>(cities));
        Collections.shuffle(best.cities);

        // local search
        // while end criteria
            // perturbation
            // local search
            // acceptance criteria
    }

    public static void main(String[] args) {
        Tsp tsp = TspLibParser.parseTsp("./tsplib/eil51.tsp");
        IteratedLocalSearchILS iteratedLocalSearchILS =
                new IteratedLocalSearchILS(tsp.getNodes().get());
        System.out.println(iteratedLocalSearchILS.run());
    }
}
