import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapProblem extends CSP {
    private MapGraph mapGraph;
    private int numberOfColours;
    private int width, height;
    private ArrayList<ArrayList<Integer>> solutions;
    private Options options;

    public MapProblem(int numberOfColours, Options options) {
        this.mapGraph = new MapGraph();
        this.numberOfColours = numberOfColours;
        solutions = new ArrayList<>();
        this.options = options;
    }

    public MapGraph getMapGraph() {
        return mapGraph;
    }

    public void setMapGraph(MapGraph mapGraph) {
        this.mapGraph = mapGraph;
    }

    public int getNumberOfColours() {
        return numberOfColours;
    }

    public void setNumberOfColours(int numberOfColours) {
        this.numberOfColours = numberOfColours;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<ArrayList<Integer>> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<ArrayList<Integer>> solutions) {
        this.solutions = solutions;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void initialiseGraph(int width, int height, int n) throws Exception {
        this.width = width;
        this.height = height;
        List<Point> pointList = generatePointList(width, height, n);
        mapGraph.setNodeList(generateNodeList(pointList));
        mapGraph.calculateDistanceMatrix();
        mapGraph = connectNodes();
    }

    public List<Point> generatePointList(int width, int height, int n) throws Exception {
        List<Point> pointList = new ArrayList<>();
        Random random = new Random();

        if (n > width * height / 2) {
            throw new Exception("Too many points");
        }

        for (int i = 0; i < n; i++) {
            int x = random.nextInt(width + 1);
            int y = random.nextInt(height + 1);
            Point newPoint = new Point(x, y);
            while (pointExists(newPoint, pointList)) {
                x = random.nextInt(width + 1);
                y = random.nextInt(height + 1);
                newPoint = new Point(x, y);
            }
            pointList.add(new Point(newPoint));
        }
        return pointList;
    }

    public boolean pointExists(Point point, List<Point> pointList) {
        for (int i = 0; i < pointList.size(); i++) {
            if (point.compareTo(pointList.get(i)) == 0) {
                return true;
            }
        }
        return false;
    }

    public List<MapNode> generateNodeList(List<Point> pointList) {
        List<MapNode> mapNodeList = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            mapNodeList.add(new MapNode(pointList.get(i), numberOfColours));
        }
        return mapNodeList;
    }

    public MapGraph connectNodes() {
        List<MapNode> nodesToChooseFrom = new ArrayList<>(List.copyOf(mapGraph.getNodeList()));
        List<Connection> connectionList = new ArrayList<>();
        Random random = new Random();
        while (!nodesToChooseFrom.isEmpty()) {
            int randomInt = random.nextInt(nodesToChooseFrom.size());
            MapNode nodeToConnect = nodesToChooseFrom.get(randomInt);
            int nodeToConnectIndex = mapGraph.getNodeList().indexOf(nodeToConnect);
            boolean stopCondition = true;
            int whileIterator = 0;
            while (stopCondition) {
                int nodeToConnectToIndex = mapGraph.findNthDistanceIndex(nodeToConnectIndex, whileIterator);
                MapNode nodeToConnectTo = mapGraph.getNodeList().get(nodeToConnectToIndex);
                if (nodeToConnect.neighbourList.contains(nodeToConnectTo)) {
                    whileIterator++;
                } else {
                    Connection newConnection = new Connection(nodeToConnect.getPoint(), nodeToConnectTo.getPoint());
                    boolean connectionOK = true;
                    for (int i = 0; i < connectionList.size(); i++) {
                        if (connectionList.get(i).isCrossing(newConnection)) {
                            connectionOK = false;
                            break;
                        }
                    }
                    if (connectionOK) {
                        connectionList.add(newConnection);
                        nodeToConnect.neighbourList.add(nodeToConnectTo);
                        nodeToConnectTo.neighbourList.add(nodeToConnect);
                        stopCondition = false;
                    } else {
                        whileIterator++;
                    }
                }
                if (whileIterator > (mapGraph.getNodeList().size() - 2)) {
                    stopCondition = false;
                    nodesToChooseFrom.remove(nodeToConnect);
                }
            }
        }
        return mapGraph;
    }

    public void solveProblem() {
        ArrayList<MapNode> variables = new ArrayList<MapNode>(List.copyOf(mapGraph.getNodeList()));
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
        }
    }

    public boolean backtracking(ArrayList<MapNode> variables) {
        int nextVar = chooseNextVar(variables);
        if (nextVar != -1) {
            MapNode currentVar = variables.get(nextVar);
            int colour = chooseNextValue(currentVar.getColourDomain());
            while (colour != -1) {
                if (constraintsSatisfied(currentVar, currentVar.getColourDomain().get(colour))) {
                    currentVar.setColour(currentVar.getColourDomain().get(colour));
                    ArrayList<MapNode> newVariables = new ArrayList(List.copyOf(variables));
                    newVariables.remove(nextVar);
                    if (backtracking(newVariables)) {

                    } else {
                        saveSolution(mapGraph);
                    }
                }
                currentVar.getColourDomain().remove(colour);
                currentVar.setColour(-1);
                colour = chooseNextValue(currentVar.getColourDomain());
            }
            currentVar.generateDomain(this.numberOfColours);
            return true;
        }
        return false;
    }

    public boolean backtrackingWithAC3(ArrayList<MapNode> variables) {
        ac3(variables);
        int nextVar = chooseNextVar(variables);
        if (nextVar != -1) {
            MapNode currentVar = variables.get(nextVar);
            int colour = chooseNextValue(currentVar.getColourDomain());
            while (colour != -1) {
                ArrayList<ArrayList<Integer>> domainsBackup = new ArrayList();
                for (int i = 0; i < mapGraph.getNodeList().size(); i++) {
                    domainsBackup.add((new ArrayList(List.copyOf(mapGraph.getNodeList().get(i).getColourDomain()))));
                }
                if (constraintsSatisfied(currentVar, currentVar.getColourDomain().get(colour))) {
                    currentVar.setColour(currentVar.getColourDomain().get(colour));
                    ArrayList<MapNode> newVariables = new ArrayList(List.copyOf(variables));
                    newVariables.remove(nextVar);
                    if (backtrackingWithAC3(newVariables)) {

                    } else {
                        saveSolution(mapGraph);
                    }
                }
                regenerateNeighbourDomains(domainsBackup);
                currentVar.getColourDomain().remove(colour);
                currentVar.setColour(-1);
                colour = chooseNextValue(currentVar.getColourDomain());
            }
            currentVar.generateDomain(this.numberOfColours);
            return true;
        }
        return false;
    }

    public boolean forwardChecking(ArrayList<MapNode> variables) {
        int nextVar = chooseNextVar(variables);
        if (nextVar != -1) {
            MapNode currentVar = variables.get(nextVar);
            int colour = chooseNextValue(currentVar.getColourDomain());
            while (colour != -1) {
                ArrayList<ArrayList<Integer>> domainsBackup = new ArrayList();
                for (int i = 0; i < mapGraph.getNodeList().size(); i++) {
                    domainsBackup.add((new ArrayList(List.copyOf(mapGraph.getNodeList().get(i).getColourDomain()))));
                }
                if (constraintsSatisfied(currentVar, currentVar.getColourDomain().get(colour))) {
                    currentVar.setColour(currentVar.getColourDomain().get(colour));
                    if (filterNeighbourDomains(currentVar, colour)) {
                        ArrayList<MapNode> newVariables = new ArrayList(List.copyOf(variables));
                        newVariables.remove(nextVar);
                        if (forwardChecking(newVariables)) {

                        } else {
                            saveSolution(mapGraph);
                        }
                    }
                }
                currentVar.getColourDomain().remove(colour);
                currentVar.setColour(-1);
                regenerateNeighbourDomains(domainsBackup);
                colour = chooseNextValue(currentVar.getColourDomain());
            }
            currentVar.generateDomain(this.numberOfColours);
            return true;
        }
        return false;
    }

    public void ac3(ArrayList<MapNode> variables) {
        ArrayList<MapArc> arcs = generateArcsList(variables);
        ArrayList<MapArc> agenda = initialiseAgenda(arcs);

        while (agenda.size() > 0) {
            MapNode left = agenda.get(0).getMapNode1();
            MapNode right = agenda.get(0).getMapNode2();
            ArrayList<Integer> newLeftDomain = new ArrayList<>();
            for (int i = 0; i < left.getColourDomain().size(); i++) {
                for (int j = 0; j < right.getColourDomain().size(); j++) {
                    if (left.getColourDomain().get(i) != right.getColourDomain().get(j)) {
                        newLeftDomain.add(left.getColourDomain().get(i));
                        break;
                    }
                }
            }

            if (newLeftDomain.size() != left.getColourDomain().size()) {
                for (int i = 0; i < arcs.size(); i++) {
                    if (left == arcs.get(i).getMapNode2()) {
                        if (!arcs.get(i).existsInList(agenda)) {
                            agenda.add(arcs.get(i));
                        }
                    }
                }
            }
            left.setColourDomain(newLeftDomain);
            agenda.remove(0);
        }
    }

    public ArrayList<MapArc> generateArcsList(ArrayList<MapNode> variables) {
        ArrayList<MapArc> arcs = new ArrayList<>();
        for (int i = 0; i < variables.size(); i++) {
            for (int j = 0; j < variables.get(i).getNeighbourList().size(); j++) {
                MapArc arc1 = new MapArc(variables.get(i), ((MapNode) variables.get(i).getNeighbourList().get(j)));
                MapArc arc2 = new MapArc(((MapNode) variables.get(i).getNeighbourList().get(j)), variables.get(i));

                if (!arc1.existsInList(arcs)) {
                    arcs.add(arc1);
                }

                if (!arc2.existsInList(arcs)) {
                    arcs.add(arc2);
                }
            }
        }
        return arcs;
    }

    public ArrayList<MapArc> initialiseAgenda(ArrayList<MapArc> arcs) {
        ArrayList<MapArc> agenda = new ArrayList<>();
        for (int i = 0; i < arcs.size(); i++) {
            agenda.add(arcs.get(i));
        }
        return agenda;
    }

    // remove(colour) - it might be index not object
    public boolean filterNeighbourDomains(MapNode currentVar, Integer colour) {
        for (int i = 0; i < currentVar.getNeighbourList().size(); i++) {
            if (((MapNode) currentVar.getNeighbourList().get(i)).getColourDomain().size() != 0
                    && ((MapNode) currentVar.getNeighbourList().get(i)).getColour() == -1) {
                ((MapNode) currentVar.getNeighbourList().get(i)).getColourDomain().remove(colour);
            }
            if (((MapNode) currentVar.getNeighbourList().get(i)).getColourDomain().size() == 0) {
                return false;
            }
        }
        return true;
    }

    public void regenerateNeighbourDomains(ArrayList<ArrayList<Integer>> domainsBackup) {
        for (int i = 0; i < domainsBackup.size(); i++) {
            mapGraph.getNodeList().get(i).setColourDomain(domainsBackup.get(i));
        }
    }

    public int chooseNextValue(List<Integer> domain) {
        switch (options.getHeuristicValue()) {
            case 0:
                return firstServedValue(domain);
            case 1:
                return randomValue(domain);
            default:
                return -1;
        }
    }

    public int firstServedValue(List<Integer> domain) {
        if (domain.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    public int randomValue(List<Integer> domain) {
        Random random = new Random();
        if (!domain.isEmpty()) {
            return random.nextInt(domain.size());
        }
        return -1;
    }

    public int chooseNextVar(ArrayList<MapNode> nodesLeft) {
        switch (options.getHeuristicValue()) {
            case 0:
                return firstServedVar(nodesLeft);
            case 1:
                return randomVar(nodesLeft);
            default:
                return -1;
        }
    }

    public int firstServedVar(ArrayList<MapNode> nodesLeft) {
        if (nodesLeft.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    public int randomVar(ArrayList<MapNode> nodesLeft) {
        Random random = new Random();
        if (!nodesLeft.isEmpty()) {
            return random.nextInt(nodesLeft.size());
        }
        return -1;
    }

    public boolean constraintsSatisfied(MapNode currentNode, int colour) {
        for (Node neighbour : currentNode.getNeighbourList()) {
            if (((MapNode) neighbour).getColour() == colour) {
                return false;
            }
        }
        return true;
    }

    public void saveSolution(MapGraph solution) {
        this.solutions.add(generateSolution(solution));
    }

    public ArrayList<Integer> generateSolution(MapGraph graph) {
        ArrayList<Integer> solution = new ArrayList();
        for (MapNode node : graph.getNodeList()) {
            solution.add(node.getColour());
        }
        return solution;
    }
}
