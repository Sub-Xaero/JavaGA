package data1.data2;

class Rule {
    String[] cond;
    String out;

    // change to a new size of condition
    Rule(int conditionLength) {
        this.cond = new String[conditionLength];
        this.out = "";
    }
}
