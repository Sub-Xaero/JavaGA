package data3;

import java.util.Arrays;

class Rule {
    float[] cond;
    int out;

    Rule(int conditionLength) {
        this.cond = new float[conditionLength];
        this.out = 2;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "cond=" + Arrays.toString(cond) +
                ", out=" + out +
                '}';
    }
}
