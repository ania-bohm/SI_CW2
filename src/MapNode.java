import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MapNode extends Node {
    private Point point;
    private int colour; // -1, 0, 1, 2, (3)
    private List<Integer> colourDomain;
    private int numberOfColours;

    public MapNode(Point point, int numberOfColours) {
        this.point = point;
        this.colour = -1;
        this.colourDomain = new ArrayList<>();
        generateDomain(numberOfColours);
        this.numberOfColours = numberOfColours;
    }

    public Point getPoint() {
        return this.point;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public List<Integer> getColourDomain() {
        return colourDomain;
    }

    public void setColourDomain(List<Integer> colourDomain) {
        this.colourDomain.clear();
        for (int i = 0; i < colourDomain.size(); i++) {
            this.colourDomain.add(colourDomain.get(i));
        }
    }

    public void generateDomain(int numberOfColours) {
        this.colourDomain.clear();
        for (int i = 0; i < numberOfColours; i++) {
            this.colourDomain.add(i);
        }

    }

    @Override
    public String toString() {
        String domain = "Domain: {";
        for (int i = 0; i < colourDomain.size() - 1; i++) {
            domain += colourDomain.get(i) + ", ";
        }
        domain += colourDomain.get(colourDomain.size() - 1) + "}";
        return domain;
    }
}
