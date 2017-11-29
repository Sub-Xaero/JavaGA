package data1.data2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class GA {

    static Individual[] initialisePopulation(int populationSize, int candidateLength, int numberOfRules, int conditionLength) {
        Individual[] population = new Individual[populationSize];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual(candidateLength, numberOfRules, conditionLength);
            for (int j = 0; j < population[i].gene.length; j++) {
                ArrayList<String> operators = new ArrayList(Arrays.asList("0", "1"));
                population[i].gene[j] = operators.get(new Random().nextInt(2));
            }
            population[i].updateRulebase();
        }
        return population;
    }

    static Individual[] tournamentSelection(Individual[] originalPopulation) {
        int parent1, parent2;
        Individual[] offspring = new Individual[originalPopulation.length];
        for (int i = 0; i < originalPopulation.length; i++) {
            parent1 = new Random().nextInt(originalPopulation.length);
            parent2 = new Random().nextInt(originalPopulation.length);

            if (originalPopulation[parent1].fitness >= originalPopulation[parent2].fitness) {
                offspring[i] = new Individual(originalPopulation[parent1]);
            } else {
                offspring[i] = new Individual(originalPopulation[parent2]);
            }
        }
        return offspring;
    }

    static Individual[] crossover(Individual[] originalPopulation) {
        int candidateLength = originalPopulation[0].gene.length;
        int populationSize = originalPopulation.length;
        int numberOfRules = originalPopulation[0].rulebase.length;
        int conditionLength = originalPopulation[0].rulebase[0].cond.length;
        Individual[] offspring = new Individual[populationSize];

        for (int i = 0; i < populationSize; i += 2) {
            Individual child1 = new Individual(candidateLength, numberOfRules, conditionLength);
            Individual child2 = new Individual(candidateLength, numberOfRules, conditionLength);
            int crossoverPoint = new Random().nextInt(candidateLength);

            for (int j = 0; j < crossoverPoint; j++) {
                child1.gene[j] = originalPopulation[i].gene[j];
                child2.gene[j] = originalPopulation[i + 1].gene[j];
            }

            for (int j = crossoverPoint; j < candidateLength; j++) {
                child1.gene[j] = originalPopulation[i + 1].gene[j];
                child2.gene[j] = originalPopulation[i].gene[j];
            }

            child1.updateRulebase();
            child2.updateRulebase();
            offspring[i] = new Individual(child1);
            offspring[i + 1] = new Individual(child2);
        }

        return offspring;
    }

    static Individual[] mutation(Individual[] originalPopulation, double mutationRate) {
        int candidateLength = originalPopulation[0].gene.length; // total lenth of each solotuion
        for (Individual anOriginalPopulation : originalPopulation) { // Loop over each solution in population
            for (int j = 0; j < candidateLength; j++) { // Loop over each value inthe gene
                double d = Math.random(); //give number between 0.0 - 1.0
                if (d < mutationRate) { // in random number < mutationRate change value
                    ArrayList<String> operators = new ArrayList(Arrays.asList("0", "1", "#"));
                    d = Math.random();
                    for (int k = 0; k < operators.size(); k++) {
                        if (anOriginalPopulation.gene[j].equals(operators.get(k))) {
                            String s = operators.get(k);
                            operators.remove(k);
                            anOriginalPopulation.gene[j] = operators.get(new Random().nextInt(2));//operators.get(temp);

                            System.out.print("");
                            operators.add(s);
                        }
                    }
                }
            }
            anOriginalPopulation.updateRulebase();
        }
        return originalPopulation;
    }

    static Individual updateBest(Individual[] array, Individual best) {
        Individual temp;
        for (Individual array1 : array) {
            if (array1.fitness > best.fitness) {
                best = array1;
            }
        }
        temp = new Individual(best);
        return temp;
    }

    static Individual getWorst(Individual[] array) {
        Individual temp, worst = array[0];
        for (Individual array1 : array) {
            if (array1.fitness < worst.fitness) {
                worst = array1;
            }
        }
        temp = new Individual(worst);
        return temp;
    }

    static String printRules(Rule[] rules) {
        StringBuilder s = new StringBuilder();
        int count = 1;
        for (Rule r : rules) {
            s.append("Rule ").append(count).append(": ");
            for (int i = 0; i < r.cond.length; i++) {
                s.append(r.cond[i]);
            }
            s.append(" = ").append(r.out).append("\n");
            count++;
        }
        return s.toString();
    }

}
