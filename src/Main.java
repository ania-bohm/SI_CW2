import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println((double) 5/3);
        Main.runCode();
    }
    public static void runCode() throws Exception {
        int width = 5;
        int height = 5;
        int read = 1;
        Scanner scanner = new Scanner(System.in);
        while (read != 0) {
            MapProblem mapProblem = new MapProblem(4);
            mapProblem.initialiseGraph(width, height, 5);
            mapProblem.solveProblem();
            mapProblem.getMapGraph().displayGraph();
            Visualization.visualizeMapProblem(mapProblem, width + 1, height + 1);
            read = scanner.nextInt();
        }
    }
}
