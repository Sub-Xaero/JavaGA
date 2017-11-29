package data1.data2;

class Individual {

    String[] gene;
    int fitness = 0;
    Rule[] rulebase;
    private int numberOfRules;
    private int conditionLength;

    Individual(int n, int numberOfRules, int conditionLength) {
        this.gene = new String[n];
        this.fitness = 0;
        this.conditionLength = conditionLength;
        this.numberOfRules = numberOfRules;
        updateRulebase();

    }

    Individual(Individual n) {
        this.conditionLength = n.conditionLength;
        this.numberOfRules = n.numberOfRules;
        this.gene = n.gene;
        this.fitness = n.fitness;
        this.rulebase = n.rulebase;
    }

    void updateRulebase() {
        int k = 0;
        this.rulebase = new Rule[numberOfRules];
        for (int i = 0; i < numberOfRules; i++) {
            rulebase[i] = new Rule(conditionLength);
            for (int j = 0; j < conditionLength; j++) {
                rulebase[i].cond[j] = gene[k++];
            }
            rulebase[i].out = gene[k++];
        }

    }

}
