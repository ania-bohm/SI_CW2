import java.util.ArrayList;
import java.util.List;

public class EinsteinProblem extends CSP {
    private int houseCount;
    private EinsteinGraph einsteinGraph;
    private List<EinsteinConstraint> constraints;
    private List<EinsteinPositionConstraint> positionConstraints;
    private List<int[][]> solutions;

    public EinsteinProblem(int houseCount) {
        this.houseCount = houseCount;
        positionConstraints = new ArrayList<>();
        constraints = new ArrayList<>();
        einsteinGraph = new EinsteinGraph();
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public EinsteinGraph getEinsteinGraph() {
        return einsteinGraph;
    }

    public void setEinsteinGraph(EinsteinGraph einsteinGraph) {
        this.einsteinGraph = einsteinGraph;
    }

    public List<EinsteinConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<EinsteinConstraint> constraints) {
        this.constraints = constraints;
    }

    public List<EinsteinPositionConstraint> getPositionConstraints() {
        return positionConstraints;
    }

    public void setPositionConstraints(List<EinsteinPositionConstraint> positionConstraints) {
        this.positionConstraints = positionConstraints;
    }

    public List<int[][]> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<int[][]> solutions) {
        this.solutions = solutions;
    }

    public void addConstraints(EinsteinConstraint constraint) {
        constraints.add(constraint);
    }

    public void addPositionConstraints(EinsteinPositionConstraint positionConstraint) {
        positionConstraints.add(positionConstraint);
    }

    public void initialiseGraph() {
        for (int i = 0; i < houseCount; i++) {
            einsteinGraph.addNode();
        }
        for (int i = 0; i < houseCount; i++) {
            einsteinGraph.getNodeList().get(i).generateDomains(houseCount);
        }
    }

    public void solveProblem() {
        ArrayList<EinsteinNode> houses = new ArrayList<>(List.copyOf(einsteinGraph.getNodeList()));
        backtracking(houses);
    }

    public boolean backtracking(ArrayList<EinsteinNode> houses) {
        int nextHouse = chooseNextHouse(houses);
        if (nextHouse != -1) {
            EinsteinNode currentHouse = houses.get(nextHouse);
            int nextVar = chooseNextVar(currentHouse.getVariables());
            if (nextVar != -1) {
                int currentVar = nextVar;
                int value = chooseNextValue(currentHouse.getDomain()[currentVar]);
                while (value != -1) {
                    if (constraintsSatisfied(currentHouse, currentVar, value)) {
                        currentHouse.setValue(currentVar, value);
                        ArrayList<EinsteinNode> newHouses = new ArrayList(List.copyOf(houses));
                        if (noMoreVariables(currentHouse)) {
                            newHouses.remove(currentHouse);
                        }
                        if (backtracking(newHouses)) {

                        } else {
                            if (positionConstraintsSatisfied()) {
                                saveSolution(einsteinGraph);
                            }

                        }

                    }
                    currentHouse.getDomain()[currentVar].remove(value);
                    currentHouse.getVariables()[currentVar] = 0;
                    value = chooseNextValue(currentHouse.getDomain()[currentVar]);
                }
                return true;
            }
            currentHouse.generateDomains(houseCount);
            return false;
        }
        return false;
    }

    private boolean positionConstraintsSatisfied() {
        for (int i = 0; i < positionConstraints.size(); i++) {
            EinsteinPositionConstraint constraint = positionConstraints.get(i);
            for (int j = 0; j < einsteinGraph.getNodeList().size(); j++) {
                if (einsteinGraph.getNodeList().get(j).getVariables()[constraint.getVar1()] == constraint.getValue1()) {
                    switch (constraint.getOffset()) {
                        case -1:
                            break;
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                    }
                }
            }
        }
    }

    private boolean noMoreVariables(EinsteinNode node) {
        for (int i = 0; i < node.getVariables().length; i++) {
            if (node.getVariables()[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private int chooseNextHouse(ArrayList<EinsteinNode> houses) {
        return firstServedHouse(houses);
    }

    private int firstServedHouse(ArrayList<EinsteinNode> houses) {
        if (houses.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    private int chooseNextVar(int[] variables) {
        return firstServedVar(variables);
    }

    private int firstServedVar(int[] variables) {
        for (int i = 0; i < variables.length; i++) {
            if (variables[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private int chooseNextValue(ArrayList<Integer> domain) {
        return firstServedValue(domain);
    }

    private int firstServedValue(ArrayList<Integer> domain) {
        if (domain.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    private void saveSolution(EinsteinGraph currentState) {
        int[][] solution = generateSolution(currentState);
        solutions.add(solution);
    }

    private int[][] generateSolution(EinsteinGraph einsteinGraph) {
        int[][] solution = new int[einsteinGraph.getNodeList().size()][6];
        for (int i = 0; i < einsteinGraph.getNodeList().size(); i++) {
            solution[i][0] = einsteinGraph.getNodeList().get(i).getVariables()[0];
            solution[i][1] = einsteinGraph.getNodeList().get(i).getVariables()[1];
            solution[i][2] = einsteinGraph.getNodeList().get(i).getVariables()[2];
            solution[i][3] = einsteinGraph.getNodeList().get(i).getVariables()[3];
            solution[i][4] = einsteinGraph.getNodeList().get(i).getVariables()[4];
            solution[i][5] = einsteinGraph.getNodeList().get(i).getVariables()[5];
        }
        return solution;
    }

    private boolean constraintsSatisfied(EinsteinNode currentNode, int currentVar, int value) {
        for (int i = 0; i > einsteinGraph.getNodeList().size(); i++) {
            if (einsteinGraph.getNodeList().get(i).getVariables()[0] != currentNode.getVariables()[0]) {
                if (einsteinGraph.getNodeList().get(i).getVariables()[currentVar] == value) {
                    return false;
                }
            }
        }
        EinsteinNode newNode = new EinsteinNode(currentNode);
        newNode.setValue(currentVar, value);
        for (int i = 0; i < constraints.size(); i++) {
            if (!isConstraintSatisfied(newNode, constraints.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isConstraintSatisfied(EinsteinNode currentNode, EinsteinConstraint constraint) {
        boolean isRelevant = false, areAllCorrect = true;
        for (int i = 0; i < 6; i++) {
            if (constraint.getConstraintSet()[i] != 0) {
                if (constraint.getConstraintSet()[i] == currentNode.getVariables()[i]) {
                    isRelevant = true;
                    break;
                }
            }
        }
        if (isRelevant) {
            for (int i = 0; i < 6; i++) {
                if (constraint.getConstraintSet()[i] != 0) {
                    if (constraint.getConstraintSet()[i] == currentNode.getVariables()[i] || currentNode.getVariables()[i] == 0) {

                    } else {
                        areAllCorrect = false;
                        break;
                    }
                }
            }
        }
        return areAllCorrect;
    }
}

