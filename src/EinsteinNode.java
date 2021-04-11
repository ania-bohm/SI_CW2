import java.util.ArrayList;
import java.util.List;

public class EinsteinNode extends Node {
    private EinsteinNode leftNeighbour, rightNeighbour;
    private int houseCount;
    private int[] variables;
    private ArrayList<Integer>[] domain;

    public EinsteinNode(int houseNumber) {
        domain = new ArrayList[6];
        leftNeighbour = null;
        rightNeighbour = null;
        variables = new int[6];
        variables[0] = houseNumber;
        for (int i = 0; i < variables.length; i++) {
            this.domain[i] = new ArrayList<>();
        }
    }

    public EinsteinNode(EinsteinNode oldNode) {
        this.domain = new ArrayList[6];
        this.houseCount = oldNode.houseCount;
        this.leftNeighbour = oldNode.leftNeighbour;
        this.rightNeighbour = oldNode.rightNeighbour;
        this.variables = new int[6];
        for (int i = 0; i < variables.length; i++) {
            this.domain[i] = new ArrayList<>(List.copyOf(oldNode.domain[i]));
        }
        for (int i = 0; i < variables.length; i++) {
            this.variables = oldNode.variables;
        }
    }

    public EinsteinNode getLeftNeighbour() {
        return leftNeighbour;
    }

    public void setLeftNeighbour(EinsteinNode leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public EinsteinNode getRightNeighbour() {
        return rightNeighbour;
    }

    public void setRightNeighbour(EinsteinNode rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(int houseCount) {
        this.houseCount = houseCount;
    }

    public int[] getVariables() {
        return variables;
    }

    public void setVariables(int[] variables) {
        this.variables = variables;
    }

    public ArrayList<Integer>[] getDomain() {
        return domain;
    }

    public void setDomain(ArrayList<Integer>[] domain) {
        this.domain = domain;
    }

    public void generateDomain(int domainToGenerate, int houseCount) {
        for (int j = 0; j < houseCount; j++) {
            domain[domainToGenerate].add(j+1);
        }
    }

    public void generateDomains(int houseCount) {
        for (int i = 0; i < 6; i++) {
            generateDomain(i, houseCount);
        }
    }

    public void setValue(int var, int value) {
        variables[var] = value;
    }
}
