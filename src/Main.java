import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Main.runCode();
    }
    public static void runCode() throws Exception {
        //width, height - size of the graph
        int width = 10;
        int height = 10;
        int read = 1;
        //number of points that will become nodes in graph
        int numberOfNodes = 5;
        Scanner scanner = new Scanner(System.in);
        // pressing any int except 0 will repeat the whole process (generating new graph and solving it)
        while (read != 0) {
            MapProblem mapProblem = new MapProblem(3);

            //number of colours 3 or 4
            //MapProblem mapProblem = new MapProblem(4);

            //generating graph
            mapProblem.initialiseGraph(width, height, numberOfNodes);

            //visualizing empty graph
            Visualization.visualizeMapProblem(mapProblem, width + 1, height + 1);

            //solving problem with backtracking
            mapProblem.solveProblem();

            //visualizing solutions
            Visualization.visualizeSolvedMapProblem(mapProblem, width + 1, height + 1);
            mapProblem.getMapGraph().displayGraph();

            //to repeat the process enter any int except 0, 0 will terminate the program
            read = scanner.nextInt();
        }
    }
}
