import java.util.ArrayList;
import java.util.List;

public class EinsteinGraph {
    private List<EinsteinNode> nodeList;

    public EinsteinGraph() {
        nodeList = new ArrayList<>();
    }

    public List<EinsteinNode> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<EinsteinNode> nodeList) {
        this.nodeList = nodeList;
    }

    public void addNode() {
        EinsteinNode newNode = new EinsteinNode(nodeList.size()+1);
        nodeList.add(newNode);
    }
}
