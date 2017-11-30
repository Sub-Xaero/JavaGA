package common;

import java.util.ArrayList;

public class GAResult {
    private int numberOfRules;
    private int populationSize;
    private int numGenerations;
    private double mutationRate;
    private ArrayList<GenerationResult> results;
    private String bestRulebase;

    public GAResult(int numberOfRules, int populationSize, int numGenerations, double mutationRate, ArrayList<GenerationResult> results, String bestRulebase) {
        this.numberOfRules = numberOfRules;
        this.populationSize = populationSize;
        this.numGenerations = numGenerations;
        this.mutationRate = mutationRate;
        this.results = results;
        this.bestRulebase = bestRulebase;
    }

    public String getBest() {
        return bestRulebase;
    }

    public void setBest(String bestRulebase) {
        this.bestRulebase = bestRulebase;
    }

    int getNumberOfRules() {
        return numberOfRules;
    }

    public void setNumberOfRules(int numberOfRules) {
        this.numberOfRules = numberOfRules;
    }

    int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    int getNumGenerations() {
        return numGenerations;
    }

    public void setNumGenerations(int numGenerations) {
        this.numGenerations = numGenerations;
    }

    double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    ArrayList<GenerationResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<GenerationResult> results) {
        this.results = results;
    }
}
