package data1.data2;

import common.GAResult;
import common.GenerationResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data1 {

    public static GAResult run() throws IOException {
        ArrayList<GenerationResult> results = new ArrayList<>();
        String filename = "data1.txt";
        ArrayList<Data> inputData = loadInputData(filename);

        int numberOfRules = 5;
        int conditionLength = inputData.get(0).Vars;
        int populationSize = 100;
        populationSize = (int) (Math.floor(populationSize / 2) * 2); // Ensure even

        int numGenerations = 500;
        int candidateLength = (conditionLength + 1) * numberOfRules; // size of gene per solution
        double mutationRate = 0.02;
        Individual best = new Individual(candidateLength, numberOfRules, conditionLength); // Store the best solution found

        Individual[] population = GA.initialisePopulation(populationSize, candidateLength, numberOfRules, conditionLength);
        Individual[] offspring;

        for (Individual pop : population) updateFitness(pop, inputData);
        printFitness(population);

        for (int generation = 1; generation <= numGenerations; generation++) {
            offspring = GA.tournamentSelection(population);
            updatePopulationFitness(offspring, inputData);
            best = GA.updateBest(offspring, best);

            //  crossover
            offspring = GA.crossover(offspring);
            updatePopulationFitness(offspring, inputData);
            best = GA.updateBest(offspring, best);

            //  mutation
            offspring = GA.mutation(offspring, mutationRate);
            updatePopulationFitness(offspring, inputData);
            best = GA.updateBest(offspring, best);

            for (int i = 0; i < populationSize; i++) population[i] = new Individual(offspring[i]);
            updatePopulationFitness(population, inputData);
            Individual worst = GA.getWorst(offspring);


            int totalFitness = 0;
            for (Individual individual : population) totalFitness += individual.fitness;
            results.add(new GenerationResult(generation, best.fitness, worst.fitness,totalFitness / population.length));
            System.out.println();
            System.out.println("Generation: " + generation);
            System.out.println("-------------------");
            System.out.println("Max     : " + best.fitness);
            System.out.println("Min     : " + worst.fitness);
            System.out.println("Average : " + (totalFitness / population.length));
            generation++;
        }
        System.out.println();
        System.out.println(GA.printRules(best.rulebase));
        return new GAResult(numberOfRules, populationSize, numGenerations, mutationRate, results);
    }

    private static ArrayList<Data> loadInputData(String filename) throws IOException {
        ArrayList<String> array = new ArrayList<>();
        // Read the Data file and create a single String of the contents
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        reader.readLine(); // this will read the first line
        String line1;
        while ((line1 = reader.readLine()) != null) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < line1.length(); i++) {
                if ((line1.charAt(i) == '0') || (line1.charAt(i) == '1')) {
                    s.append(line1.charAt(i));
                }
            }
            array.add(s.toString());
        }

        int size_data = array.get(0).length() - 1;
        ArrayList<Data> tempA = new ArrayList<>();

        for (String a : array) {
            Data temp = new Data(size_data);
            for (int i = 0; i < size_data; i++) {
                temp.variables[i] = Character.getNumericValue(a.charAt(i));
            }
            temp.type = Character.getNumericValue(a.charAt(size_data));
            tempA.add(temp);
        }
        return tempA;
    }

    private static boolean matchesCondition(int[] data, String[] rule) {
        for (int i = 0; i < data.length; i++) {
            String s = "" + data[i];
            if ((!rule[i].equals(s)) && (!rule[i].equals("#"))) {
                return false;
            }
        }
        return true;
    }

    private static void printFitness(Individual[] array) {
        System.out.println("Fitness");
        int avFitness = 0;
        for (Individual anArray : array) {
            System.out.print(anArray.fitness + " ");
            avFitness = avFitness + anArray.fitness;
        }//for i
        System.out.println("\nTotal of all fitness = " + avFitness + "\nAverage fitness = " + (avFitness / array.length) + "\n");
    }

    private static void updateFitness(Individual solution, ArrayList<Data> data) {
        solution.fitness = 0;
        for (Data aData : data) {
            for (Rule rulebase : solution.rulebase) {
                if (matchesCondition(aData.variables, rulebase.cond)) {
                    String s = "" + aData.type;
                    if (rulebase.out.equals(s)) {
                        solution.fitness++;
                    }
                    break;
                }
            }
        }
    }

    private static void updatePopulationFitness(Individual[] population, ArrayList<Data> inputData) {
        for (Individual individual : population)
            updateFitness(individual, inputData);
    }

}
