package common;

import data1.data2.Data1;
import data1.data2.Data2;
import data3.Data3;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Run {
    private static ArrayList<GenerationResult> AverageResults(ArrayList<ArrayList<GenerationResult>> DataResults, int numRounds) {
        ArrayList<GenerationResult> AverageDataResults = new ArrayList<>();
        if (DataResults.size() > 0) {
            for (int i = 0; i < DataResults.get(0).size() - 1; i++) {
                int generation = 0;
                int averageAverage = 0;
                int averageMax = 0;
                int averageMin = 0;
                for (int j = 0; j < numRounds; j++) {
                    GenerationResult result = DataResults.get(j).get(i);
                    generation = result.getGeneration();
                    averageAverage += result.getAverageFitness();
                    averageMax += result.getMaxFitness();
                    averageMin += result.getMinFitness();
                }
                averageAverage /= DataResults.size();
                averageMax /= DataResults.size();
                averageMin /= DataResults.size();
                AverageDataResults.add(new GenerationResult(generation, averageMax, averageMin, averageAverage));
            }
        }
        return AverageDataResults;
    }

    private static void WriteCSV(String name, ArrayList<GenerationResult> AverageResults) throws IOException {
        FileWriter writer = new FileWriter(name);
        writer.append("Generation,Max Fitness, Min Fitness, Average Fitness\n");
        for (GenerationResult result : AverageResults) writer.append(result.toString());
        writer.flush();
        writer.close();
    }

    public static void main(String[] args) throws IOException {

        ArrayList<ArrayList<GenerationResult>> Data1Results = new ArrayList<>();
        ArrayList<ArrayList<GenerationResult>> Data2Results = new ArrayList<>();
        ArrayList<ArrayList<GenerationResult>> Data3Results = new ArrayList<>();

        GAResult Data1_GAResult = null;
        GAResult Data2_GAResult = null;
        GAResult Data3_GAResult = null;

        int numRounds = 3;
        for (int i = 0; i < numRounds; i++) {
            Data1_GAResult = Data1.run();
            Data1Results.add(Data1_GAResult.getResults());

//            Data2_GAResult = Data2.run();
//            Data2Results.add(Data2_GAResult.getResults());

//            Data3_GAResult = Data3.run();
//            Data3Results.add(Data3_GAResult.getResults());
        }

        if (Data1Results.size() > 0) WriteCSV("CSVs/Data1 "
                + "Mutation" +  Data1_GAResult.getMutationRate() + " "
                + "Generations" +  Data1_GAResult.getNumGenerations() + " "
                + "Population" +  Data1_GAResult.getPopulationSize() + " "
                + "Rules"+  Data1_GAResult.getNumberOfRules() + " "
                +  ".csv", AverageResults(Data1Results, numRounds));
        if (Data2Results.size() > 0) WriteCSV("CSVs/Data2 "
                + "Mutation" +  Data2_GAResult.getMutationRate() + " "
                + "Generations" +  Data2_GAResult.getNumGenerations() + " "
                + "Population" +  Data2_GAResult.getPopulationSize() + " "
                + "Rules"+  Data2_GAResult.getNumberOfRules() + " "
                +  ".csv", AverageResults(Data2Results, numRounds));
        if (Data3Results.size() > 0) WriteCSV("CSVs/Data3 "
                + "Mutation" +  Data3_GAResult.getMutationRate() + " "
                + "Generations" +  Data3_GAResult.getNumGenerations() + " "
                + "Population" +  Data3_GAResult.getPopulationSize() + " "
                + "Rules"+  Data3_GAResult.getNumberOfRules() + " "
                +  ".csv", AverageResults(Data3Results, numRounds));

    }
}
