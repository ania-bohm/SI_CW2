import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MapGraph extends Graph {
    private Double[][] distanceMatrix;
    private List<MapNode> nodeList;

    public MapGraph() {
        nodeList = new ArrayList<>();
    }
    public MapGraph(MapGraph mapGraph){

    }

    public void calculateDistanceMatrix() {
        int nodeCount = nodeList.size();
        distanceMatrix = new Double[nodeCount][nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            Point start = nodeList.get(i).getPoint();
            for (int j = 0; j < nodeCount; j++) {
                Point end = nodeList.get(j).getPoint();
                distanceMatrix[i][j] = Point.distance(start, end);
            }
        }
    }


    public List<MapNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<MapNode> nodeList) {
        this.nodeList = nodeList;
    }

    public Double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public int findNthDistanceIndex(int index, int n) {
        Double[] distanceRow = distanceMatrix[index];
        Integer[] indexes = new Integer[distanceMatrix[index].length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        final List<Integer> indexesListCopy = Arrays.asList(indexes);
        ArrayList<Integer> sortedList = new ArrayList(indexesListCopy);
        Collections.sort(sortedList, (left, right) -> (int) (distanceRow[indexesListCopy.indexOf(left)] - distanceRow[indexesListCopy.indexOf(right)]));

        return sortedList.get(n + 1);
    }

    public void displayGraph() {
        for (int i = 0; i < nodeList.size(); i++) {
            System.out.print("Node: " + nodeList.get(i).getPoint().toString() +", its neighbours: {");
            for (int j = 0; j < nodeList.get(i).getNeighbourList().size(); j++) {
                System.out.print(((MapNode) nodeList.get(i).getNeighbourList().get(j)).getPoint().toString() + " ");
            }
            System.out.print("}\n");
        }
    }
}
