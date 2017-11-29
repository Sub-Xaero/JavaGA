package data3;


public class Individual {

    int numRules;
    int conditionLength;
    float[] gene;
    int fitness = 0;
    Rule[] rulebase;

    Individual(int n, int numRules, int conditionLength) {
        this.gene = new float[n];
        this.fitness = 0;
        this.conditionLength = conditionLength;
        this.numRules = numRules;
        updateRulebase();

    }

    Individual(Individual n) {
        this.conditionLength = n.conditionLength;
        this.numRules = n.numRules;
        this.gene = n.gene;
        this.fitness = n.fitness;
        this.rulebase = n.rulebase;

    }

    void updateRulebase() {
        int k = 0;

        this.rulebase = new Rule[numRules];
        for (int i = 0; i < numRules; i++) {
            rulebase[i] = new Rule(conditionLength);
            for (int j = 0; j < conditionLength; j++) {
                rulebase[i].cond[j] = gene[k++];
            }
            rulebase[i].out = (int) gene[k++];
        }

    }

}
