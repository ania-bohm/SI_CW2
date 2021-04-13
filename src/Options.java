public class Options {
    // heuristicVariable: 0 - firstServedVar, 1 - randomVar
    private int heuristicVariable;
    // heuristicValue: 0 - firstServedValue, 1 - randomValue
    private int heuristicValue;
    // solving algorithm: 0 - backtracking, 1 - forwardChecking, 2 - backtracking with AC3
    private int solveType;

    public Options(int heuristicVariable, int heuristicValue, int solveType){
        this.heuristicVariable = heuristicVariable;
        this.heuristicValue = heuristicValue;
        this.solveType = solveType;
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

    public void setSolveType(int solveType) {
        this.solveType = solveType;
    }
}
