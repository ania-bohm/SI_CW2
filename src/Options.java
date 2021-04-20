import java.io.FileWriter;
import java.io.PrintWriter;

public class Options {
    // heuristicVariable: 0 - firstServedVar, 1 - randomVar
    private int heuristicVariable;
    // heuristicValue: 0 - firstServedValue, 1 - randomValue
    private int heuristicValue;
    // solving algorithm: 0 - backtracking, 1 - forwardChecking, 2 - backtracking with AC3
    private int solveType;

    private boolean oneSolution;



    private PrintWriter writer;

    public Options(int heuristicVariable, int heuristicValue, int solveType, boolean oneSolution, PrintWriter writer){
        this.heuristicVariable = heuristicVariable;
        this.heuristicValue = heuristicValue;
        this.solveType = solveType;
        this.oneSolution = oneSolution;
        this.writer = writer;
    }
    public PrintWriter getWriter() {
        return writer;
    }
    public int getHeuristicVariable() {
        return heuristicVariable;
    }

    public void setHeuristicVariable(int heuristicVariable) {
        this.heuristicVariable = heuristicVariable;
    }

    public int getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(int heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    public int getSolveType() {
        return solveType;
    }

    public boolean isOneSolution() {
        return oneSolution;
    }

    public void setSolveType(int solveType) {
        this.solveType = solveType;
    }
}
