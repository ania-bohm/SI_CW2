import java.util.ArrayList;
import java.util.List;

public class Node {
    protected List<Node> neighbourList;

    public Node() {
        neighbourList = new ArrayList<>();
    }

    public Node(Node n) {
        neighbourList = new ArrayList<>();
        for (Node neighbour : n.neighbourList) {
            this.neighbourList.add(neighbour);
        }
    }

    public List<Node> getNeighbourList() {
        return neighbourList;
    }

    public void setNeighbourList(List<Node> neighbourList) {
        this.neighbourList.clear();
        for (Node neighbour : neighbourList) {
            this.neighbourList.add(neighbour);
        }
    }
}
