import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println((double) 5/3);
        Main.runCode();
    }
    public static void runCode() throws Exception {
        int width = 10;
        int height = 10;
        int read = 1;
        Scanner scanner = new Scanner(System.in);
        while (read != 0) {
            MapProblem mapProblem = new MapProblem(3);
            mapProblem.initialiseGraph(width, height, 5);
            Visualization.visualizeMapProblem(mapProblem, width + 1, height + 1);
            mapProblem.solveProblem();
            Visualization.visualizeSolvedMapProblem(mapProblem, width + 1, height + 1);
            mapProblem.getMapGraph().displayGraph();

            read = scanner.nextInt();
        }
    }
}
