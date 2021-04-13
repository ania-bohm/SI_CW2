import java.util.ArrayList;

public class MapArc {
    private MapNode mapNode1, mapNode2;

    public MapArc(MapNode mapNode1, MapNode mapNode2) {
        this.mapNode1 = mapNode1;
        this.mapNode2 = mapNode2;
    }

    public MapNode getMapNode1() {
        return mapNode1;
    }

    public void setMapNode1(MapNode mapNode1) {
        this.mapNode1 = mapNode1;
    }

    public MapNode getMapNode2() {
        return mapNode2;
    }

    public void setMapNode2(MapNode mapNode2) {
        this.mapNode2 = mapNode2;
    }

    public boolean existsInList(ArrayList<MapArc> arcs) {
        for (int i = 0; i < arcs.size(); i++) {
            if (this.mapNode1 == arcs.get(i).mapNode1 && this.mapNode2 == arcs.get(i).mapNode2) {
                return true;
            }
        }
        return false;
    }
}
