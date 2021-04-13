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
        solutions = new ArrayList<>();
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

        ArrayList<HouseVariable> variables = makeVariableList(einsteinGraph);
//        backtracking(variables);
        forwardChecking(variables);
    }

    public boolean backtracking(ArrayList<HouseVariable> variableList) {
        int nextVarIndex = chooseNextVar(variableList);
        while (nextVarIndex != -1) {
            HouseVariable currentVar = variableList.get(nextVarIndex);
            int valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            while (valueIndex != -1) {
                int value = einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].get(valueIndex);
                if (constraintsSatisfied(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()), currentVar.getVarIndex(), value)) {
                    einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).setValue(currentVar.getVarIndex(), value);
                    ArrayList<HouseVariable> newVariableList = new ArrayList(List.copyOf(variableList));
                    newVariableList.remove(currentVar);
                    if (backtracking(newVariableList)) {

                    } else {
                        if (positionConstraintsSatisfied()) {
                            saveSolution(einsteinGraph);
                        }
                    }
                }
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].remove(valueIndex);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getVariables()[currentVar.getVarIndex()] = 0;
                valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            }
            einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).generateDomain(currentVar.getVarIndex(), 5);
            return true;
        }
        return false;
    }

    public boolean forwardChecking(ArrayList<HouseVariable> variableList) {
        int nextVarIndex = chooseNextVar(variableList);
        while (nextVarIndex != -1) {
            HouseVariable currentVar = variableList.get(nextVarIndex);
            int valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            while (valueIndex != -1) {
                Integer value = einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].get(valueIndex);

                ArrayList<Integer> [][] domainsBackup = new ArrayList[5][6];
                for(int i=0;i<5;i++){
                    for(int j=0;j<6;j++){
                        domainsBackup[i][j] = new ArrayList(List.copyOf(einsteinGraph.getNodeList().get(i).getDomain()[j]));
                    }
                }

                if (filterNeighbourDomains(currentVar, value)) {
                    if (constraintsSatisfied(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()), currentVar.getVarIndex(), value)) {
                        einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).setValue(currentVar.getVarIndex(), value);
                        ArrayList<HouseVariable> newVariableList = new ArrayList(List.copyOf(variableList));
                        newVariableList.remove(currentVar);
                        if (forwardChecking(newVariableList)) {

                        } else {
                            if (positionConstraintsSatisfied()) {
                                saveSolution(einsteinGraph);
                            }
                        }
                    }
                }
                regenerateNeighbourDomains(domainsBackup);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].remove(value);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getVariables()[currentVar.getVarIndex()] = 0;
                valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            }
            einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).generateDomain(currentVar.getVarIndex(), 5);
            return true;
        }
        return false;
    }

    public boolean filterNeighbourDomains(HouseVariable currentVar, Integer currentVal) {
        for (int i = 0; i < 5; i++) {
            if (einsteinGraph.getNodeList().get(currentVar.getHouseIndex()) != einsteinGraph.getNodeList().get(i)) {
                einsteinGraph.getNodeList().get(i).getDomain()[currentVar.getVarIndex()].remove(currentVal);
            }
        }
        for (int i = 0; i < 5; i++) {
            if (einsteinGraph.getNodeList().get(i).getDomain()[currentVar.getVarIndex()].isEmpty()
            && einsteinGraph.getNodeList().get(currentVar.getHouseIndex()) != einsteinGraph.getNodeList().get(i)) {
                return false;
            }
        }

        return true;
    }

    public void regenerateNeighbourDomains(ArrayList<Integer> [][] domainsBackup) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                 einsteinGraph.getNodeList().get(i).getDomain()[j]=domainsBackup[i][j];
            }
        }
    }

    public ArrayList<HouseVariable> makeVariableList(EinsteinGraph graph) {
        ArrayList<HouseVariable> variables = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j < 6; j++) {
                variables.add(new HouseVariable(i, j));
            }
        }
        return variables;
    }

    private boolean positionConstraintsSatisfied() {
        for (int i = 0; i < positionConstraints.size(); i++) {
            EinsteinPositionConstraint constraint = positionConstraints.get(i);
            for (int j = 0; j < einsteinGraph.getNodeList().size(); j++) {
                EinsteinNode currentNode = einsteinGraph.getNodeList().get(j);
                if (currentNode.getVariables()[constraint.getVar1()] == constraint.getValue1()) {
                    switch (constraint.getOffset()) {
                        case -1: // left neighbour
                            if (currentNode.getVariables()[0] == 1) {
                                return false;
                            } else {
                                if (currentNode.getLeftNeighbour(einsteinGraph).getVariables()[constraint.getVar2()] != constraint.getValue2()) {
                                    return false;
                                }
                            }
                            break;
                        case 0: // one of the neighbours
                            if (currentNode.getVariables()[0] == 1) {
                                if (currentNode.getRightNeighbour(einsteinGraph).getVariables()[constraint.getVar2()] != constraint.getValue2()) {
                                    return false;
                                }
                            } else if (currentNode.getVariables()[0] == 5) {
                                if (currentNode.getLeftNeighbour(einsteinGraph).getVariables()[constraint.getVar2()] != constraint.getValue2()) {
                                    return false;
                                }
                            } else if ((currentNode.getLeftNeighbour(einsteinGraph).getVariables()[constraint.getVar2()] != constraint.getValue2())
                                    && currentNode.getRightNeighbour(einsteinGraph).getVariables()[constraint.getVar2()] != constraint.getValue2()) {
                                return false;
                            }
                            break;
                        case 1: // right neighbour
                            if (currentNode.getVariables()[0] == 5) {
                                return false;
                            } else {
                                if (currentNode.getRightNeighbour(einsteinGraph).getVariables()[constraint.getVar2()] != constraint.getValue2()) {
                                    return false;
                                }
                            }
                            break;
                    }
                }
            }
        }
        return true;
    }

    private int chooseNextVar(ArrayList<HouseVariable> var) {
        return firstServedVar(var);
    }

    private int firstServedVar(ArrayList<HouseVariable> var) {
        if (var.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
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
        for (int i = 0; i < einsteinGraph.getNodeList().size(); i++) {
            if (einsteinGraph.getNodeList().get(i).getVariables()[0] != currentNode.getVariables()[0]) {
                if (einsteinGraph.getNodeList().get(i).getVariables()[currentVar] == value) {
                    return false;
                }
            }
        }
        //EinsteinNode newNode = new EinsteinNode(currentNode);
        currentNode.setValue(currentVar, value);
        for (int i = 0; i < constraints.size(); i++) {
            if (!isConstraintSatisfied(currentNode, constraints.get(i))) {
                currentNode.setValue(currentVar, 0);
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

    public void displaySolutions() {
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("Solution " + i + ":");
            System.out.println("-----------------------------------");
            for (int j = 0; j < houseCount; j++) {
                String[] words = intsToWords(solutions.get(i)[j]);
                System.out.println("House number: " + words[0]);
                System.out.println("Colour      : " + words[1]);
                System.out.println("Nationality : " + words[2]);
                System.out.println("Tobacco     : " + words[3]);
                System.out.println("Drink       : " + words[4]);
                System.out.println("Pet         : " + words[5]);
                System.out.println("");
            }
            System.out.println("-----------------------------------");
        }
    }

    public String[] intsToWords(int[] houseInformation) {
        int houseNumber = houseInformation[0];
        int colour = houseInformation[1];
        int nationality = houseInformation[2];
        int tobacco = houseInformation[3];
        int drink = houseInformation[4];
        int pet = houseInformation[5];

        String[] words = new String[6];

        switch (houseNumber) {
            case 1:
                words[0] = "1";
                break;
            case 2:
                words[0] = "2";
                break;
            case 3:
                words[0] = "3";
                break;
            case 4:
                words[0] = "4";
                break;
            case 5:
                words[0] = "5";
                break;
        }

        switch (colour) {
            case 1:
                words[1] = "Red";
                break;
            case 2:
                words[1] = "Green";
                break;
            case 3:
                words[1] = "White";
                break;
            case 4:
                words[1] = "Yellow";
                break;
            case 5:
                words[1] = "Blue";
                break;
        }

        switch (nationality) {
            case 1:
                words[2] = "Norwegian";
                break;
            case 2:
                words[2] = "English";
                break;
            case 3:
                words[2] = "Danish";
                break;
            case 4:
                words[2] = "German";
                break;
            case 5:
                words[2] = "Swede";
                break;
        }

        switch (tobacco) {
            case 1:
                words[3] = "Light";
                break;
            case 2:
                words[3] = "Cigar";
                break;
            case 3:
                words[3] = "Pipe";
                break;
            case 4:
                words[3] = "Without filter";
                break;
            case 5:
                words[3] = "Menthol";
                break;
        }

        switch (drink) {
            case 1:
                words[4] = "Milk";
                break;
            case 2:
                words[4] = "Water";
                break;
            case 3:
                words[4] = "Beer";
                break;
            case 4:
                words[4] = "Coffee";
                break;
            case 5:
                words[4] = "Tea";
                break;
        }

        switch (pet) {
            case 1:
                words[5] = "Birds";
                break;
            case 2:
                words[5] = "Dogs";
                break;
            case 3:
                words[5] = "Horses";
                break;
            case 4:
                words[5] = "Fish";
                break;
            case 5:
                words[5] = "Cats";
                break;
        }

        return words;
    }
}

