import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapProblem extends CSP {
    private MapGraph mapGraph;
    private int numberOfColours;

    public MapProblem() {
        this.mapGraph = new MapGraph();
        numberOfColours = 4;
    }

    public void initialiseGraph(int width, int height, int n) throws Exception {
        List<Point> pointList = generatePointList(width, height, n);
        mapGraph.setNodeList(generateNodeList(pointList));

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
        List<MapNode> nodesToChooseFrom = List.copyOf(mapGraph.getNodeList());
        List<Connection> connectionList = new ArrayList<>();
        Random random = new Random();
        while (!nodesToChooseFrom.isEmpty()) {
            int randomInt = random.nextInt(nodesToChooseFrom.size());
            MapNode nodeToConnect = nodesToChooseFrom.get(randomInt);
            int nodeToConnectIndex = mapGraph.getNodeList().indexOf(nodeToConnect);
            boolean stopCondition = true;
            int whileIterator = 0;
            while(stopCondition){
                int nodeToConnectToIndex = mapGraph.findNthDistanceIndex(nodeToConnectIndex, whileIterator);
                MapNode nodeToConnectTo = mapGraph.getNodeList().get(nodeToConnectToIndex);
                if(nodeToConnect.neighbourList.contains(nodeToConnectTo)){
                    whileIterator++;
                }
                else{
                    Connection newConnection = new Connection(nodeToConnect.getPoint(), nodeToConnectTo.getPoint());
                    for(int i=0;i<connectionList.size();i++){

                    }
                }
            }


        }
    }

    public boolean isConnectionValid(Connection c, List<Connection> allConnections) {
        for (Connection connection : allConnections) {

        }
    }

}
