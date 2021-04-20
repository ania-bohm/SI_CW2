import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class Research {

    public void runTests() throws Exception {
        heuristicMapTest();
        algorithmsMapTest();
        heuristicEinsteinTest();
    }

    public void heuristicMapTest() throws Exception {
        Options options = new Options(0, 0, 0, false, new FileWriter(new File("")));
        int width = 10;
        int height = 10;
        int numberOfNodes = 6;
        MapProblem mapProblem = new MapProblem(3, options);
        mapProblem.initialiseGraph(width, height, numberOfNodes);

        //badanie heurystyki zmiennej kolor3
        for (int varOption = 0; varOption < 3; varOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                options = new Options(varOption, 0, algorithmOption, true, new FileWriter(new File(buildFilePath(varOption, 0, algorithmOption, "BadanieHeurystykiZmiennejMapy_Kolor-3"))));
                mapProblem.setOptions(options);
                int startTime, endTime;
                startTime= Instant.now().getNano();
                mapProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(mapProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
            }
        }

        //badanie heurystyki wartości kolor3
        for (int valOption = 0; valOption < 2; valOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                options = new Options(0, valOption, algorithmOption, true, new FileWriter(new File(buildFilePath(0, valOption, algorithmOption, "BadanieHeurystykiWartosciMapy_Kolor-3"))));
                mapProblem.setOptions(options);
                int startTime, endTime;
                startTime= Instant.now().getNano();
                mapProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(mapProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
            }
        }

        mapProblem.setNumberOfColours(4);
        //badanie heurystyki zmiennej kolor4
        for (int varOption = 0; varOption < 3; varOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                options = new Options(varOption, 0, algorithmOption, true, new FileWriter(new File(buildFilePath(varOption, 0, algorithmOption, "BadanieHeurystykiZmiennejMapy_Kolor-4"))));
                mapProblem.setOptions(options);
                int startTime, endTime;
                startTime= Instant.now().getNano();
                mapProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(mapProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
            }
        }

        //badanie heurystyki wartości kolor4
        for (int valOption = 0; valOption < 2; valOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                mapProblem.clearProblem();
                options = new Options(0, valOption, algorithmOption, true, new FileWriter(new File(buildFilePath(0, valOption, algorithmOption, "BadanieHeurystykiWartosciMapy_Kolor-4"))));
                mapProblem.setOptions(options);
                int startTime, endTime;
                startTime= Instant.now().getNano();
                mapProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(mapProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
            }
        }

    }

    public void algorithmsMapTest() throws Exception {
        File file1, file2, file3, file4;
        file1 = new File(buildFilePath(0, 0, 0, "AlgorithmMapTest"));
        file2 = new File(buildFilePath(0, 0, 1, "AlgorithmMapTest"));
        file3 = new File(buildFilePath(0, 0, 2, "AlgorithmMapTest"));
        file4 = new File(buildFilePath(0, 0, 3, "AlgorithmMapTest"));

        FileWriter writer1, writer2, writer3, writer4;
        writer1 = new FileWriter(file1);
        writer2 = new FileWriter(file2);
        writer3 = new FileWriter(file3);
        writer4 = new FileWriter(file4);
        FileWriter writers[] = new FileWriter[]{writer1, writer2, writer3, writer4};
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
                int startTime, endTime;
                startTime= Instant.now().getNano();
                mapProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(mapProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
            }
        }

    }

    public void heuristicEinsteinTest() throws IOException {
        Options options = new Options(0, 0, 0, false, new FileWriter(new File("")));
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
        for (int varOption = 0; varOption < 3; varOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                options = new Options(varOption, 0, algorithmOption, true, new FileWriter(new File(buildFilePath(varOption, 0, algorithmOption, "BadanieHeurystykiZmiennejEinsteina"))));

                EinsteinProblem einsteinProblem = new EinsteinProblem(5, options);
                einsteinProblem.initialiseGraph();
                einsteinProblem.setConstraints(constraints);
                einsteinProblem.setPositionConstraints(positionConstraints);
                int startTime, endTime;
                startTime= Instant.now().getNano();
                einsteinProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(einsteinProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
            }
        }

        //badanie heurystyki wartości
        for (int valOption = 0; valOption < 2; valOption++) {
            for (int algorithmOption = 0; algorithmOption < 4; algorithmOption++) {
                options = new Options(0, valOption, algorithmOption, true, new FileWriter(new File(buildFilePath(0, valOption, algorithmOption, "BadanieHeurystykiWartosciEinsteina"))));
                EinsteinProblem einsteinProblem = new EinsteinProblem(5, options);
                einsteinProblem.initialiseGraph();
                einsteinProblem.setConstraints(constraints);
                einsteinProblem.setPositionConstraints(positionConstraints);
                int startTime, endTime;
                startTime= Instant.now().getNano();
                einsteinProblem.solveProblem();
                endTime = Instant.now().getNano();
                options.getWriter().write(einsteinProblem.iteratorTest);
                options.getWriter().write((endTime-startTime)/10000000);
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
