import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;

public class Research {

    public void runTests() throws Exception {
        heuristicMapTest();
        algorithmsMapTestColour3();
        algorithmsMapTestColour4();
        heuristicEinsteinTest();
    }

    public void heuristicMapTest() throws Exception {
        Options options = new Options(0, 0, 0, false, new PrintWriter(new File("test")));
        int width = 10;
        int height = 10;
        int numberOfNodes = 8;
        MapProblem mapProblem = new MapProblem(3, options);
        mapProblem.initialiseGraph(width, height, numberOfNodes);

        //badanie heurystyki zmiennej kolor3
        for (int varOption = 0; varOption < 3; varOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                File file = new File(buildFilePath(varOption, 0, algorithmOption, "BadanieHeurystykiZmiennejMapy_Kolor-3"));
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                options = new Options(varOption, 0, algorithmOption, true, writer);
                mapProblem.setOptions(options);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                mapProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(mapProblem.iteratorTest + ";" + (endTime - startTime));
                writer.close();
            }
        }

        //badanie heurystyki wartości kolor3
        for (int valOption = 0; valOption < 2; valOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                File file = new File(buildFilePath(0, valOption, algorithmOption, "BadanieHeurystykiWartosciMapy_Kolor-3"));
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                options = new Options(0, valOption, algorithmOption, true, writer);
                mapProblem.setOptions(options);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                mapProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(mapProblem.iteratorTest + ";" + (endTime - startTime));
                writer.close();
            }
        }

        mapProblem.setNumberOfColours(4);
        //badanie heurystyki zmiennej kolor4
        for (int varOption = 0; varOption < 3; varOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                File file = new File(buildFilePath(varOption, 0, algorithmOption, "BadanieHeurystykiZmiennejMapy_Kolor-4"));
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                options = new Options(varOption, 0, algorithmOption, true, writer);
                mapProblem.setOptions(options);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                mapProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(mapProblem.iteratorTest + ";" + (endTime - startTime));
                writer.close();
            }
        }

        //badanie heurystyki wartości kolor4
        for (int valOption = 0; valOption < 2; valOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                File file = new File(buildFilePath(0, valOption, algorithmOption, "BadanieHeurystykiWartosciMapy_Kolor-4"));
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                options = new Options(0, valOption, algorithmOption, true, writer);
                mapProblem.setOptions(options);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                mapProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(mapProblem.iteratorTest + ";" + (endTime - startTime));
                writer.close();
            }
        }
    }

    public void algorithmsMapTestColour3() throws Exception {
        File file1, file2, file3, file4;
        file1 = new File(buildFilePath(0, 0, 0, "AlgorithmMapTestColour3"));
        file2 = new File(buildFilePath(0, 0, 1, "AlgorithmMapTestColour3"));
        file3 = new File(buildFilePath(0, 0, 2, "AlgorithmMapTestColour3"));
        file4 = new File(buildFilePath(0, 0, 3, "AlgorithmMapTestColour3"));
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();
        file4.createNewFile();
        PrintWriter writer1, writer2, writer3, writer4;
        writer1 = new PrintWriter(file1);
        writer2 = new PrintWriter(file2);
        writer3 = new PrintWriter(file3);
        writer4 = new PrintWriter(file4);
        PrintWriter writers[] = new PrintWriter[]{writer1, writer2, writer3, writer4};
        int nodes[] = new int[]{2, 3, 4, 6, 8, 9, 10, 12, 13, 14};
        int width = 10;
        int height = 10;
        for (int nodeCountIndex = 0; nodeCountIndex < nodes.length; nodeCountIndex++) {
            Options options = new Options(0, 0, 0, false, writers[0]);
            MapProblem mapProblem = new MapProblem(3, options);
            int numberOfNodes = nodes[nodeCountIndex];
            mapProblem.initialiseGraph(width, height, numberOfNodes);
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                options = new Options(0, 0, algorithmOption, false, writers[algorithmOption]);
                mapProblem.setOptions(options);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                mapProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(mapProblem.iteratorTest + ";" + (endTime - startTime));
            }
        }
        writer1.close();
        writer2.close();
        writer3.close();
        writer4.close();

    }

    public void algorithmsMapTestColour4() throws Exception {
        File file1, file2, file3, file4;
        file1 = new File(buildFilePath(0, 0, 0, "AlgorithmMapTestColour4"));
        file2 = new File(buildFilePath(0, 0, 1, "AlgorithmMapTestColour4"));
        file3 = new File(buildFilePath(0, 0, 2, "AlgorithmMapTestColour4"));
        file4 = new File(buildFilePath(0, 0, 3, "AlgorithmMapTestColour4"));
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();
        file4.createNewFile();
        PrintWriter writer1, writer2, writer3, writer4;
        writer1 = new PrintWriter(file1);
        writer2 = new PrintWriter(file2);
        writer3 = new PrintWriter(file3);
        writer4 = new PrintWriter(file4);
        PrintWriter writers[] = new PrintWriter[]{writer1, writer2, writer3, writer4};
        int nodes[] = new int[]{2, 3, 4, 6, 8, 9, 10, 12, 13, 14};
        int width = 10;
        int height = 10;
        for (int nodeCountIndex = 0; nodeCountIndex < nodes.length; nodeCountIndex++) {
            Options options = new Options(0, 0, 0, false, writers[0]);
            MapProblem mapProblem = new MapProblem(4, options);
            int numberOfNodes = nodes[nodeCountIndex];
            mapProblem.initialiseGraph(width, height, numberOfNodes);
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                options = new Options(0, 0, algorithmOption, false, writers[algorithmOption]);
                mapProblem.setOptions(options);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                mapProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(mapProblem.iteratorTest + ";" + (endTime - startTime));
            }
        }
        writer1.close();
        writer2.close();
        writer3.close();
        writer4.close();

    }

    public void heuristicEinsteinTest() throws IOException {
        Options options ;
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


        //badanie heurystyki zmiennej
        for (int varOption = 0; varOption < 2; varOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                File file = new File(buildFilePath(varOption, 0, algorithmOption, "BadanieHeurystykiZmiennejEinsteina"));
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                options = new Options(varOption, 0, algorithmOption, true, writer);

                EinsteinProblem einsteinProblem = new EinsteinProblem(5, options);
                einsteinProblem.initialiseGraph();
                einsteinProblem.setConstraints(constraints);
                einsteinProblem.setPositionConstraints(positionConstraints);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                einsteinProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(einsteinProblem.iteratorTest + ";" + (endTime - startTime));
                writer.close();
            }
        }

        //badanie heurystyki wartości
        for (int valOption = 0; valOption < 2; valOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                File file = new File(buildFilePath(0, valOption, algorithmOption, "BadanieHeurystykiWartosciEinsteina"));
                file.createNewFile();
                PrintWriter writer = new PrintWriter(file);
                options = new Options(0, valOption, algorithmOption, true, writer);
                EinsteinProblem einsteinProblem = new EinsteinProblem(5, options);
                einsteinProblem.initialiseGraph();
                einsteinProblem.setConstraints(constraints);
                einsteinProblem.setPositionConstraints(positionConstraints);
                long startTime, endTime;
                startTime = System.currentTimeMillis();
                einsteinProblem.solveProblem();
                endTime = System.currentTimeMillis();
                options.getWriter().println(einsteinProblem.iteratorTest + ";" + (endTime - startTime));
                writer.close();
            }
        }
    }


    public String buildFilePath(int var, int val, int algorithm, String testType) {
        String[] varNames = new String[]{"Default", "Random", "SmallestDomain"};
        String[] valNames = new String[]{"Default", "Random"};
        String[] algorithmNames = new String[]{"Backtracking", "ForwardChecking", "BacktrackingAC3", "ForwardCheckingAC3"};
        return testType + "_Var-" + varNames[var] + "_Val-" + valNames[val] + "_algorithm-" + algorithmNames[algorithm] + ".csv";
    }

}
