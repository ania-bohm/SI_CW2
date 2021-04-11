import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapProblem extends CSP {
    private MapGraph mapGraph;
    private int numberOfColours;
    private int width, height;
    private ArrayList<ArrayList<Integer>> solutions;

    public ArrayList<ArrayList<Integer>> getSolutions() {
        return solutions;
    }

    public MapProblem(int numberOfColours) {
        this.mapGraph = new MapGraph();
        this.numberOfColours = numberOfColours;
        solutions = new ArrayList<>();
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
        backtracking(variables);
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

    public int chooseNextValue(List<Integer> domain) {
        return ferstServedValue(domain);
    }

    public int ferstServedValue(List<Integer> domain) {
        if (domain.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean constraintsSatisfied(MapNode currentNode, int colour) {
        for (Node neighbour : currentNode.getNeighbourList()) {
            if (((MapNode) neighbour).getColour() == colour) {
                return false;
            }
        }
        return true;
    }

    public int chooseNextVar(ArrayList<MapNode> nodesLeft) {
        return firstServedValue(nodesLeft);
    }

    public int firstServedValue(ArrayList<MapNode> nodesLeft) {
        if (nodesLeft.isEmpty()) {
            return -1;
        } else {
            return 0;
        }
    }

    public void saveSolution(MapGraph solution) {
        System.out.println("New solution is found");
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
