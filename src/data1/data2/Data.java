package data1.data2;

public class Data {
    int Vars;
    int[] variables;
    int type;

    Data(int Vars) {
        this.Vars = Vars;
        this.variables = new int[Vars];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int v : variables) {
            s.append(v);
        }
        return s.toString() + type;


    }
}
