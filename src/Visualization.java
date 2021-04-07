import javax.swing.*;

public class Visualization {
    public static void visualizeMapProblem(MapProblem mapProblem, int width, int height) {
        Runnable r = () -> {
            int scaleUp = 40;
            JFrame frmOpt = new JFrame();
            frmOpt.setVisible(true);
            frmOpt.setLocation(100, 100);
            frmOpt.setAlwaysOnTop(true);
            LineComponent lineComponent = new LineComponent(width * scaleUp, height * scaleUp, mapProblem.getMapGraph());
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
