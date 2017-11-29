package data3;

import java.util.ArrayList;
import java.util.Random;


class GA {

    static Individual[] initialisePopulation(int populationSize, int gene_size, int NumR, int conditionLength) {
        Individual[] temp = new Individual[populationSize];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Individual(gene_size, NumR, conditionLength);
            for (int j = 1; j < gene_size + 1; j++) {
                if ((j % (conditionLength + 1)) == 0) {
                    temp[i].gene[j - 1] = new Random().nextInt(2);
                } else {
                    float d = (float) Math.random();
                    temp[i].gene[j - 1] = d;
                }
            }
            temp[i].updateRulebase();
        }
        return temp;
    }

    static Individual[] tournamentSelection(Individual[] original) {
        int parent1, parent2;
        Individual[] temp = initialisePopulation(original.length, original[0].gene.length, original[0].numRules, original[0].conditionLength);
        for (int i = 0; i < original.length; i++) {
            parent1 = new Random().nextInt(original.length);
            parent2 = new Random().nextInt(original.length);

            if (original[parent1].fitness >= original[parent2].fitness) {
                temp[i] = new Individual(original[parent1]);
            } else {
                temp[i] = new Individual(original[parent2]);
            }
        }
        return temp;
    }

    static Individual[] crossover(Individual[] original) {
        int gene_size = original[0].gene.length;
        int populationSize = original.length;
        int NumR = original[0].rulebase.length;
        int conditionLength = original[0].rulebase[0].cond.length;
        Individual[] modified = new Individual[populationSize];

        for (int i = 0; i < populationSize; i += 2) {
            Individual temp1 = new Individual(gene_size, NumR, conditionLength);
            Individual temp2 = new Individual(gene_size, NumR, conditionLength);
            int x_point = new Random().nextInt(gene_size);

            for (int j = 0; j < x_point; j++) {
                temp1.gene[j] = original[i].gene[j];
                temp2.gene[j] = original[i + 1].gene[j];
            }

            for (int j = x_point; j < gene_size; j++) {
                temp1.gene[j] = original[i + 1].gene[j];
                temp2.gene[j] = original[i].gene[j];
            }

            temp1.updateRulebase();
            temp2.updateRulebase();
            modified[i] = new Individual(temp1);
            modified[i + 1] = new Individual(temp2);
        }
        return modified;
    }

    static Individual[] mutation(Individual[] original, double mute_rate, float mute_size) {
        int gene_size = original[0].gene.length;

        for (Individual anOriginal : original) {
            for (int j = 1; j < gene_size + 1; j++) {
                double d = Math.random();
                if (d < mute_rate) {
                    if ((j % 13) == 0) {
                        if (anOriginal.gene[j - 1] == (float) 0.0) {
                            anOriginal.gene[j - 1] = (float) 1.0;
                        } else {
                            anOriginal.gene[j - 1] = (float) 0.0;
                        }
                    } else {
                        int operand_selection = new Random().nextInt(2);
                        if (operand_selection == 0) {
                            anOriginal.gene[j - 1] += mute_size;
                            if (anOriginal.gene[j - 1] > 1.0)
                                anOriginal.gene[j - 1] = (float) 1.0;
                        } else {
                            anOriginal.gene[j - 1] -= mute_size;
                            if (anOriginal.gene[j - 1] < 0.0)
                                anOriginal.gene[j - 1] = (float) 0.0;
                        }
                    }
                }
            }
            anOriginal.updateRulebase();
        }
        return original;
    }

    static void updateFitness(Individual solution, ArrayList<Data> data) {
        solution.fitness = 0;
        for (Data aData : data) {
            for (Rule rulebase : solution.rulebase) {
                if (matches_cond(aData.variables, rulebase.cond)) {
                    if (rulebase.out == aData.type) {
                        solution.fitness++;
                    }
                    break;
                }
            }
        }
    }

    private static boolean matches_cond(float[] data, float[] rule) {
        int k = 0;
        for (float aData : data) {
            if (rule[k] > rule[k + 1]) {
                if ((aData > rule[k]) || (rule[k + 1] > aData)) {
                    return false;
                }
            } else {
                if ((aData < rule[k]) || (rule[k + 1] < aData)) {
                    return false;
                }
            }
            k += 2;
        }
        return true;
    }

    static Individual updateBest(Individual[] array, Individual best) {
        // Compares each solution in the offspring population to the fittest found
        for (Individual array1 : array) {
            if (array1.fitness > best.fitness) {
                best = array1;
            }
        }
        return new Individual(best);
    }

    static Individual getWorst(Individual[] array) {
        Individual worst = array[0];
        for (Individual array1 : array) {
            if (array1.fitness < worst.fitness) {
                worst = array1;
            }
        }
        return new Individual(worst);
    }

    static String printRules(Rule[] rules) {
        StringBuilder s = new StringBuilder();
        int count = 1;
        for (Rule r : rules) {
            s
                    .append("Rule ")
                    .append(count).append(": ");
            for (int i = 0; i < r.cond.length; i++) {
                s.append("(");
                s.append(String.format("%5f", r.cond[i++]));
                s.append(", ");
                s.append(String.format("%5f", r.cond[i]));
                s.append(") ");
            }
            s
                    .append(" = ")
                    .append(r.out).append("\n");
            count++;
        }
        return s.toString();
    }

}
