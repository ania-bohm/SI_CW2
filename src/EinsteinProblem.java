import java.util.ArrayList;

public class EinsteinProblem extends CSP {
    int houseCount;
    EinsteinGraph einsteinGraph;
    ArrayList<EinsteinRestriction> restrictions;

    public EinsteinProblem(int houseCount) {
        restrictions = new ArrayList<>();
        this.houseCount = houseCount;
        einsteinGraph =  new EinsteinGraph();
    }
    public void addRestricion(EinsteinRestriction restriction){
        restrictions.add(restriction);
    }
    public void initialiseGraph() {
        for (int i = 0; i < houseCount; i++) {
            einsteinGraph.addNode();
        }
    }
}
