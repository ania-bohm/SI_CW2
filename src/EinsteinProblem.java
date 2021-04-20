import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EinsteinProblem extends CSP {
    private int houseCount;
    private EinsteinGraph einsteinGraph;
    private ArrayList<EinsteinConstraint> constraints;
    private List<EinsteinPositionConstraint> positionConstraints;
    private List<int[][]> solutions;
    private Options options;

    public EinsteinProblem(int houseCount, Options options) {
        this.houseCount = houseCount;
        positionConstraints = new ArrayList<>();
        constraints = new ArrayList<>();
        einsteinGraph = new EinsteinGraph();
        solutions = new ArrayList<>();
        this.options = options;
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

    public void setConstraints(ArrayList<EinsteinConstraint> constraints) {
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

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
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
        switch (options.getSolveType()) {
            case 0:
                backtracking(variables);
                break;
            case 1:
                forwardChecking(variables);
                break;
            case 2:
                backtrackingWithAC3(variables);
                break;
            case 3:
                forwardCheckingWithAC3(variables);
                break;
        }
        //System.out.println("removes: " + removeIter);
    }

    public int iteratorTest = 0;

    public boolean backtracking(ArrayList<HouseVariable> variableList) {

//        System.out.println("Iteration: " + iteratorTest);
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
//        System.out.println("Iteration: " + iteratorTest);
        int nextVarIndex = chooseNextVar(variableList);
        while (nextVarIndex != -1) {

            HouseVariable currentVar = variableList.get(nextVarIndex);
            int valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            while (valueIndex != -1) {
                int[][][] domainsBackup = new int[5][6][];
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 6; j++) {
                        domainsBackup[i][j] = new int[einsteinGraph.getNodeList().get(i).getDomain()[j].size()];
                        for (int k = 0; k < einsteinGraph.getNodeList().get(i).getDomain()[j].size(); k++) {
                            domainsBackup[i][j][k] = einsteinGraph.getNodeList().get(i).getDomain()[j].get(k);
                        }
                    }
                }
                Integer value = einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].get(valueIndex);


                if (constraintsSatisfied(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()), currentVar.getVarIndex(), value)) {
                    if (filterNeighbourDomains(currentVar, value)) {
                        einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).setValue(currentVar.getVarIndex(), value);
                        ArrayList<HouseVariable> newVariableList = new ArrayList(List.copyOf(variableList));
                        newVariableList.remove(nextVarIndex);
                        if (forwardChecking(newVariableList)) {

                        } else {
                            if (positionConstraintsSatisfied()) {
                                saveSolution(einsteinGraph);

                            }
                        }
                    }
                }
                regenerateNeighbourDomains(domainsBackup);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].remove(valueIndex);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getVariables()[currentVar.getVarIndex()] = 0;
                valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            }
//            regenerateNeighbourDomains(domainsBackup);
//            einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).generateDomain(currentVar.getVarIndex(), 5);
            return true;
        }
        return false;
    }

    public boolean forwardCheckingWithAC3(ArrayList<HouseVariable> variableList) {

        ac3();
        int nextVarIndex = chooseNextVar(variableList);
        while (nextVarIndex != -1) {
            HouseVariable currentVar = variableList.get(nextVarIndex);
            int valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            while (valueIndex != -1) {
                Integer value = einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].get(valueIndex);
                int[][][] domainsBackup = new int[5][6][];
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 6; j++) {
                        domainsBackup[i][j] = new int[einsteinGraph.getNodeList().get(i).getDomain()[j].size()];
                        for (int k = 0; k < einsteinGraph.getNodeList().get(i).getDomain()[j].size(); k++) {
                            domainsBackup[i][j][k] = einsteinGraph.getNodeList().get(i).getDomain()[j].get(k);
                        }
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

    public boolean backtrackingWithAC3(ArrayList<HouseVariable> variableList) {

        ac3();
        int nextVarIndex = chooseNextVar(variableList);
        while (nextVarIndex != -1) {
            HouseVariable currentVar = variableList.get(nextVarIndex);
            int valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);

            while (valueIndex != -1) {
                ArrayList<ArrayList<Integer>[]> backupDomains = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    ArrayList<Integer>[] backupDomain = new ArrayList[6];
                    for (int j = 0; j < 6; j++) {
                        backupDomain[j] = new ArrayList<>(List.copyOf(einsteinGraph.getNodeList().get(i).getDomain()[j]));
                        //System.out.println(backupDomain[j].toString());
                    }
                    backupDomains.add(backupDomain);
                }

                int value = einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].get(valueIndex);
                if (constraintsSatisfied(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()), currentVar.getVarIndex(), value)) {
                    einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).setValue(currentVar.getVarIndex(), value);
                    ArrayList<HouseVariable> newVariableList = new ArrayList(List.copyOf(variableList));
                    newVariableList.remove(currentVar);
                    if (backtrackingWithAC3(newVariableList)) {

                    } else {
                        if (positionConstraintsSatisfied()) {
                            saveSolution(einsteinGraph);

                        }
                    }
                }
                regenerateDomains(backupDomains);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()].remove(valueIndex);
                einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getVariables()[currentVar.getVarIndex()] = 0;

                valueIndex = chooseNextValue(einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[currentVar.getVarIndex()]);
            }
            einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).generateDomain(currentVar.getVarIndex(), 5);
            return true;
        }
        return false;
    }

    public void regenerateDomains(ArrayList<ArrayList<Integer>[]> backupDomains) {
        for (int i = 0; i < 5; i++) {
            einsteinGraph.getNodeList().get(i).setDomain(backupDomains.get(i));
        }
    }

    public void ac3() {
        for (int i = 0; i < einsteinGraph.getNodeList().size(); i++) {
            ArrayList<EinsteinArc> arcs = generateArcsList(constraints);
            ArrayList<EinsteinArc> agenda = initialiseAgenda(arcs);
            while (agenda.size() > 0) {
                boolean domainChanged = false;
                int leftVar = agenda.get(0).getVar1();
                Integer leftVal = agenda.get(0).getVal1();
                int rightVar = agenda.get(0).getVar2();
                Integer rightVal = agenda.get(0).getVal2();

                if (einsteinGraph.getNodeList().get(i).getDomain()[leftVar].contains(leftVal)) {
                    if (!einsteinGraph.getNodeList().get(i).getDomain()[rightVar].contains(rightVal)) {
                        einsteinGraph.getNodeList().get(i).getDomain()[leftVar].remove(leftVal);
                        domainChanged = true;
                    }
                }

                if (domainChanged) {
                    for (int j = 0; j < arcs.size(); j++) {
                        if ((leftVar == arcs.get(j).getVar2()) && (leftVal == arcs.get(j).getVal2())) {
                            if (!arcs.get(j).existsInList(agenda)) {
                                agenda.add(arcs.get(j));
                            }
                        }
                    }
                }
                agenda.remove(0);
            }
        }
    }

    public ArrayList<EinsteinArc> generateArcsList(ArrayList<EinsteinConstraint> constraints) {
        ArrayList<EinsteinArc> arcs = new ArrayList<>();
        for (EinsteinConstraint constraint : constraints) {
            int var1 = -1, val1 = -1, var2 = -1, val2 = -1;
            for (int i = 0; i < 6; i++) {
                if (constraint.getConstraintSet()[i] != 0) {
                    if (var1 == -1) {
                        var1 = i;
                        val1 = constraint.getConstraintSet()[i];
                    } else {
                        var2 = i;
                        val2 = constraint.getConstraintSet()[i];
                    }
                }
            }
            EinsteinArc newArc1 = new EinsteinArc(var1, val1, var2, val2);
            EinsteinArc newArc2 = new EinsteinArc(var2, val2, var1, val1);
            if (!newArc1.existsInList(arcs)) {
                arcs.add(newArc1);
            }
            if (!newArc2.existsInList(arcs)) {
                arcs.add(newArc2);
            }
        }
        return arcs;
    }

    public ArrayList<EinsteinArc> initialiseAgenda(ArrayList<EinsteinArc> arcs) {
        ArrayList<EinsteinArc> agenda = new ArrayList<>();
        for (int i = 0; i < arcs.size(); i++) {
            agenda.add(arcs.get(i));
        }
        return agenda;
    }

    private int removeIter = 0;

    public boolean filterNeighbourDomains(HouseVariable currentVar, Integer currentVal) {
        for (int i = 0; i < 5; i++) {
            if (einsteinGraph.getNodeList().get(currentVar.getHouseIndex()) != einsteinGraph.getNodeList().get(i)) {
                if (einsteinGraph.getNodeList().get(i).getDomain()[currentVar.getVarIndex()].indexOf(currentVal) != -1) {
                    einsteinGraph.getNodeList().get(i).getDomain()[currentVar.getVarIndex()].remove(einsteinGraph.getNodeList().get(i).getDomain()[currentVar.getVarIndex()].indexOf(currentVal));
                    removeIter++;
                }
            }
        }
        for (int i = 0; i < constraints.size(); i++) {
            for (int j = 0; j < 6; j++) {
                if (constraints.get(i).getConstraintSet()[j] == currentVal && j == currentVar.getVarIndex()) {
                    for (int k = 0; k < 6; k++) {
                        if (k != j && constraints.get(i).getConstraintSet()[k] != 0) {
                            for (int l = 0; l < einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[k].size(); l++) {
                                if (einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[k].get(l) != constraints.get(i).getConstraintSet()[k]) {
                                    einsteinGraph.getNodeList().get(currentVar.getHouseIndex()).getDomain()[k].remove(l);
                                    l--;
                                }
                            }
                        }
                    }
                }
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

    public void regenerateNeighbourDomains(int[][][] domainsBackup) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                einsteinGraph.getNodeList().get(i).getDomain()[j].clear();
                for (int k = 0; k < domainsBackup[i][j].length; k++)
                    einsteinGraph.getNodeList().get(i).getDomain()[j].add(domainsBackup[i][j][k]);
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
        switch (options.getHeuristicVariable()) {
            case 0:
                return firstServedVar(var);
            case 1:
                return randomVar(var);
            default:
                return -1;
        }
    }

    private int firstServedVar(ArrayList<HouseVariable> var) {
        if (var.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    private int randomVar(ArrayList<HouseVariable> var) {
        Random random = new Random();
        if (var.isEmpty()) {
            return -1;
        } else {
            return random.nextInt(var.size());
        }
    }

    private int chooseNextValue(ArrayList<Integer> domain) {
        switch (options.getHeuristicValue()) {
            case 0:
                return firstServedValue(domain);
            case 1:
                return randomValue(domain);
            default:
                return -1;
        }
    }

    private int firstServedValue(ArrayList<Integer> domain) {
        if (domain.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    private int randomValue(ArrayList<Integer> domain) {
        Random random = new Random();
        if (domain.isEmpty()) {
            return -1;
        } else {
            return random.nextInt(domain.size());
        }
    }

    private void saveSolution(EinsteinGraph currentState) {
        int[][] solution = generateSolution(currentState);
        solutions.add(solution);
      //  System.out.println("Iteration: " + iteratorTest);
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
        iteratorTest++;
        for (int i = 0; i < einsteinGraph.getNodeList().size(); i++) {
            if (einsteinGraph.getNodeList().get(i).getVariables()[0] != currentNode.getVariables()[0]) {
                if (einsteinGraph.getNodeList().get(i).getVariables()[currentVar] == value) {
                    return false;
                }
            }
        }
//        EinsteinNode newNode = new EinsteinNode(currentNode);
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

