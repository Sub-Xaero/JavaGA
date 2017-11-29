package common;

public class GenerationResult {
    private int generation;
    private int maxFitness;
    private int minFitness;
    private int averageFitness;

    public GenerationResult(int generation, int maxFitness, int minFitness, int averageFitness) {
        this.generation = generation;
        this.maxFitness = maxFitness;
        this.minFitness = minFitness;
        this.averageFitness = averageFitness;
    }

    int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    int getMaxFitness() {
        return maxFitness;
    }

    public void setMaxFitness(int maxFitness) {
        this.maxFitness = maxFitness;
    }

    int getMinFitness() {
        return minFitness;
    }

    public void setMinFitness(int minFitness) {
        this.minFitness = minFitness;
    }

    int getAverageFitness() {
        return averageFitness;
    }

    public void setAverageFitness(int averageFitness) {
        this.averageFitness = averageFitness;
    }

    @Override
    public String toString() {
        return generation + "," + maxFitness + "," + minFitness + "," +averageFitness + "\n";
    }
}
