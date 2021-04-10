import java.util.ArrayList;

public class EinsteinGraph {
    private ArrayList<EinsteinNode> nodeList;
    public EinsteinGraph(){

    }
    public void addNode(){
        EinsteinNode newNode = new EinsteinNode(nodeList.size());
        nodeList.add(newNode);
        nodeList.get(nodeList.size()-2).rightNeighbour = newNode;
        newNode.leftNeighbour = nodeList.get(nodeList.size()-2);
    }
}
