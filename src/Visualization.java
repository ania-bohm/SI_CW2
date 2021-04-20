import javax.swing.*;
import java.util.ArrayList;

public class Visualization {
    public static void visualizeSolvedMapProblem(MapProblem mapProblem, int width, int height) {
        for (ArrayList<Integer> solution : mapProblem.getSolutions()) {
            Runnable r = () -> {
                int scaleUp = 40;
                JFrame frmOpt = new JFrame();
                frmOpt.setVisible(true);
                frmOpt.setLocation(100, 100);
                frmOpt.setAlwaysOnTop(true);
                LineComponent lineComponent = new LineComponent(width * scaleUp, height * scaleUp, solution);
                for (int i = 0; i < mapProblem.getMapGraph().getNodeList().size(); i++) {
                    int x = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i)).getPoint().getX();
                    int y = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getY();
                    lineComponent.addPoint(x, y);
                    for (int j = 0; j < mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().size(); j++) {
                        int x1 = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getX();
                        int x2 = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().get(j)).getPoint().getX();
                        int y1 = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getY();
                        int y2 = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().get(j)).getPoint().getY();
                        lineComponent.addLine(x1, x2, y1, y2);
                    }
                }
                JOptionPane.showMessageDialog(frmOpt, lineComponent);
                frmOpt.dispose();
            };
            SwingUtilities.invokeLater(r);
        }
    }

    public static void visualizeFirstSolvedMapProblem(MapProblem mapProblem, int width, int height) {
        if (!mapProblem.getSolutions().isEmpty()) {
            ArrayList<Integer> solution = mapProblem.getSolutions().get(0);
            System.out.println("Solutions found: " + mapProblem.getSolutions().size());
            Runnable r = () -> {
                int scaleUp = 40;
                JFrame frmOpt = new JFrame();
                frmOpt.setVisible(true);
                frmOpt.setLocation(100, 100);
                frmOpt.setAlwaysOnTop(true);
                LineComponent lineComponent = new LineComponent(width * scaleUp, height * scaleUp, solution);
                for (int i = 0; i < mapProblem.getMapGraph().getNodeList().size(); i++) {
                    int x = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i)).getPoint().getX();
                    int y = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getY();
                    lineComponent.addPoint(x, y);
                    for (int j = 0; j < mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().size(); j++) {
                        int x1 = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getX();
                        int x2 = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().get(j)).getPoint().getX();
                        int y1 = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getY();
                        int y2 = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().get(j)).getPoint().getY();
                        lineComponent.addLine(x1, x2, y1, y2);
                    }
                }
                JOptionPane.showMessageDialog(frmOpt, lineComponent);
                frmOpt.dispose();
            };
            SwingUtilities.invokeLater(r);
        }
    }

    public static void visualizeMapProblem(MapProblem mapProblem, int width, int height) {
        ArrayList<Integer> blank = new ArrayList<>();
        for (int i = 0; i < mapProblem.getMapGraph().getNodeList().size(); i++) {
            blank.add(-1);
        }
        Runnable r = () -> {
            int scaleUp = 40;
            JFrame frmOpt = new JFrame();
            frmOpt.setVisible(true);
            frmOpt.setLocation(100, 100);
            frmOpt.setAlwaysOnTop(true);
            LineComponent lineComponent = new LineComponent(width * scaleUp, height * scaleUp, blank);
            for (int i = 0; i < mapProblem.getMapGraph().getNodeList().size(); i++) {
                int x = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i)).getPoint().getX();
                int y = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getY();
                lineComponent.addPoint(x, y);
                for (int j = 0; j < mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().size(); j++) {
                    int x1 = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getX();
                    int x2 = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().get(j)).getPoint().getX();
                    int y1 = mapProblem.getMapGraph().getNodeList().get(i).getPoint().getY();
                    int y2 = ((MapNode) mapProblem.getMapGraph().getNodeList().get(i).getNeighbourList().get(j)).getPoint().getY();
                    lineComponent.addLine(x1, x2, y1, y2);
                }
            }
            JOptionPane.showMessageDialog(frmOpt, lineComponent);
            frmOpt.dispose();
        };
        SwingUtilities.invokeLater(r);


    }
}
