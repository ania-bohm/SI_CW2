import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
//        Main.runEinstein();
        Main.runMap();
    }

    public static void runEinstein() {
        ArrayList<EinsteinConstraint> constraints = new ArrayList<>();
        constraints.add(new EinsteinConstraint(new int[]{1, 0, 1, 0, 0, 0}));
        constraints.add(new EinsteinConstraint(new int[]{0, 1, 2, 0, 0, 0}));
        constraints.add(new EinsteinConstraint(new int[]{0, 0, 3, 0, 5, 0}));
        constraints.add(new EinsteinConstraint(new int[]{0, 4, 0, 2, 0, 0}));
        constraints.add(new EinsteinConstraint(new int[]{0, 0, 4, 3, 0, 0}));
        constraints.add(new EinsteinConstraint(new int[]{3, 0, 0, 0, 1, 0}));
        constraints.add(new EinsteinConstraint(new int[]{0, 0, 0, 4, 0, 1}));
        constraints.add(new EinsteinConstraint(new int[]{0, 0, 5, 0, 0, 2}));
        constraints.add(new EinsteinConstraint(new int[]{0, 0, 0, 5, 3, 0}));
        constraints.add(new EinsteinConstraint(new int[]{0, 2, 0, 0, 4, 0}));

        ArrayList<EinsteinPositionConstraint> positionConstraints = new ArrayList<>();
        positionConstraints.add(new EinsteinPositionConstraint(1, 2, 1, 1, 3));
        positionConstraints.add(new EinsteinPositionConstraint(3, 1, 0, 5, 5));
        positionConstraints.add(new EinsteinPositionConstraint(3, 1, 0, 4, 2));
        positionConstraints.add(new EinsteinPositionConstraint(2, 1, 0, 1, 5));
        positionConstraints.add(new EinsteinPositionConstraint(5, 3, 0, 1, 4));

        EinsteinProblem einsteinProblem = new EinsteinProblem(5, new Options(0, 0, 1));
        einsteinProblem.initialiseGraph();
        einsteinProblem.setConstraints(constraints);
        einsteinProblem.setPositionConstraints(positionConstraints);
        einsteinProblem.solveProblem();
        einsteinProblem.displaySolutions();
    }

    public static void runMap() throws Exception {
        //width, height - size of the graph
        int width = 10;
        int height = 10;
        int read = 1;
        //number of points that will become nodes in graph
        int numberOfNodes = 10;
        Scanner scanner = new Scanner(System.in);
        // pressing any int except 0 will repeat the whole process (generating new graph and solving it)
        while (read != 0) {
            // options: 1. heuristicVariable: 0 - firstServedVar, 1 - randomVar, 2. heuristicValue: 0 - firstServedValue, 1 - randomValue
            // 3. solveType: 0 - backtracking, 1 - forwardChecking, 2 - backtracking with AC3
            MapProblem mapProblem = new MapProblem(4, new Options(0, 0, 1));

            //number of colours 3 or 4
            //MapProblem mapProblem = new MapProblem(4);

            //generating graph
            mapProblem.initialiseGraph(width, height, numberOfNodes);

            //visualizing empty graph
            Visualization.visualizeMapProblem(mapProblem, width + 1, height + 1);

            //solving problem with backtracking
            mapProblem.solveProblem();

            //visualizing solutions
            Visualization.visualizeFirstSolvedMapProblem(mapProblem, width + 1, height + 1);
//            Visualization.visualizeSolvedMapProblem(mapProblem, width + 1, height + 1);
//            mapProblem.getMapGraph().displayGraph();

            //to repeat the process enter any int except 0, 0 will terminate the program
            read = scanner.nextInt();
        }
    }
}
