package com.brunoguic.tsp;

import com.mlaskows.tsplib.datamodel.tsp.Tsp;
import com.mlaskows.tsplib.parser.TspLibParser;

public class TspApp {
    public static void main(String[] args) {
        // http://elib.zib.de/pub/mp-testdata/tsp/tsplib/stsp-sol.html
        String input = "./tsplib/eil51.tsp"; // 426
        Tsp tsp = TspLibParser.parseTsp(input);
        System.out.println("Random: " + new RandomSearchTSP(tsp.getNodes().get(), 5000).run());
        System.out.println("HillClimbingTSP: " + new HillClimbingTSP(tsp.getNodes().get()).run());
        System.out.println("SimulatedAnnealingTSP: " +
                new SimulatedAnnealingTSP(tsp.getNodes().get(), 200, 100, 5, 0.98).run());
    }
}
