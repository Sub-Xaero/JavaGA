
package data3;


public class Data {
    int Vars;
    float[] variables;
    int type;

    Data(int Vars) {
        this.Vars = Vars;
        this.variables = new float[Vars];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (double v : variables) {
            s.append(v);
        }
        return s.toString() + type;


    }
}
