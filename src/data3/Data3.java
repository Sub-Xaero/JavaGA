package data3;

import common.GAResult;
import common.GenerationResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Data3 {

    public static GAResult run() throws IOException {
        ArrayList<GenerationResult> results = new ArrayList<>();
        String filename = "data3.txt";

        // ArrayList of Data objects to hold the input data
        ArrayList<Data> inputData = loadInputData(filename);
        ArrayList<Data> trainingSet = new ArrayList<>();
        ArrayList<Data> testSet = new ArrayList<>();

        // Splits input file in training and test sets
        int count = 0;
        for (Data t : inputData) {
            if (count < 1500) {
                trainingSet.add(t);
            } else {
                testSet.add(t);
            }
            count++;
        }

        int numberOfRules = 5;
        int conditionLength = inputData.get(0).Vars * 2;
        int geneSize = (conditionLength + 1) * numberOfRules; // size of gene per solution
        int populationSize = 100;
        int numGenerations = 1000;
        double mutationRate = 0.02;
        float mutationStep = (float) 0.1;

        Individual bestEverIndividual = new Individual(geneSize, numberOfRules, conditionLength);
        Individual[] population = GA.initialisePopulation(populationSize, geneSize, numberOfRules, conditionLength);
        Individual[] offspring;

        Individual best = new Individual(geneSize, numberOfRules, conditionLength); // Store the best solution found
        updatePopulationFitnesses(population, trainingSet);

        for (int generation = 0; generation < numGenerations; generation++) {
            offspring = GA.tournamentSelection(population);
            updatePopulationFitnesses(offspring, trainingSet);

            offspring = GA.crossover(offspring);
            updatePopulationFitnesses(offspring, trainingSet);

            offspring = GA.mutation(offspring, mutationRate, mutationStep);
            updatePopulationFitnesses(offspring, trainingSet);

            best = GA.updateBest(offspring, best);
            Individual worst = GA.getWorst(offspring);

            for (int i = 0; i < populationSize; i++) population[i] = new Individual(offspring[i]);
            int totalFitness = 0;
            for (Individual individual : population) totalFitness += individual.fitness;
            results.add(new GenerationResult(generation, best.fitness, worst.fitness, totalFitness / population.length));

            System.out.println();
            System.out.println("Generation: " + generation);
            System.out.println("-------------------");
            System.out.println("Max     : " + best.fitness);
            System.out.println("Min     : " + worst.fitness);
            System.out.println("Average : " + (totalFitness / population.length));
        }

        //check completed GA's best solution and compare with the previous GA
        if (best.fitness > bestEverIndividual.fitness) {
            bestEverIndividual = new Individual(best);
        }

        System.out.println("Training Set : First" + trainingSet.size() + " of input data");
        System.out.println("Best fitness against training set " + bestEverIndividual.fitness);
        System.out.println(GA.printRules(bestEverIndividual.rulebase));
        double percent_training = ((double) 100 / trainingSet.size()) * bestEverIndividual.fitness;

        GA.updateFitness(bestEverIndividual, testSet);
        System.out.println("Test Set : Last" + testSet.size() + " of input data");
        System.out.println("Best fitness against test set " + bestEverIndividual.fitness);
        System.out.format("%.2f%% accuracy on training set\n", percent_training);
        double percent_test = ((double) 100 / testSet.size()) * bestEverIndividual.fitness;
        System.out.format("%.2f%% accuracy on test set\n", percent_test);

        return new GAResult(numberOfRules, populationSize, numGenerations, mutationRate, results, "Fitness : " + best.fitness + "\n" + GA.printRules(best.rulebase));
    }

    private static void updatePopulationFitnesses(Individual[] population, ArrayList<Data> trainingSet) {
        for (Individual pop : population) {
            GA.updateFitness(pop, trainingSet);
        }
    }

    private static ArrayList<Data> loadInputData(String filename) throws IOException {
        ArrayList<String> array = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine(); // this will read the first line
        String line1;
        while ((line1 = reader.readLine()) != null) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < line1.length(); i++) {
                if ((line1.charAt(i) != '\n')) {
                    s.append(line1.charAt(i));
                }
            }
            array.add(s.toString());
        }
        ArrayList<Data> tempA = new ArrayList<>();

        for (String a : array) {
            ArrayList<Float> temp = new ArrayList<>();
            Scanner scanner = new Scanner(a);
            scanner.useDelimiter(" ");
            while (scanner.hasNext()) {
                temp.add(scanner.nextFloat());
            }

            Data data_temp = new Data(temp.size() - 1);
            for (int i = 0; i <= data_temp.Vars - 1; i++) {
                data_temp.variables[i] = temp.get(i);
            }
            data_temp.type = Character.getNumericValue(a.charAt(a.length() - 1));
            tempA.add(data_temp);
        }
        return tempA;
    }
}
