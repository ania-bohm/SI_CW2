import java.util.ArrayList;
import java.util.List;

public class Connection {
    private Point startPoint;
    private Point endPoint;
    private double connectionLength;

    public Connection(Point startPoint, Point endPoint) {
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.connectionLength = countConnectionLength();
    }

    public Connection(Connection s) {
        this.startPoint = new Point(s.getStartPoint());
        this.endPoint = new Point(s.getEndPoint());
        this.connectionLength = s.getConnectionLength();
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = new Point(startPoint);
        this.connectionLength = countConnectionLength();
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = new Point(endPoint);
        this.connectionLength = countConnectionLength();
    }

    public double countConnectionLength() {
        return Point.distance(startPoint, endPoint);
    }

    public double getConnectionLength() {
        return countConnectionLength();
    }

    @Override
    public String toString() {
        return "[" + startPoint.toString() + ", " + endPoint.toString() + "]";
    }

    public boolean isCrossing(Connection c) {
        double a1, a2, b1, b2, x, y;
        if (this.startPoint.getX() == this.endPoint.getX()) {
            if (c.startPoint.getY() == c.endPoint.getY()) {
                if ((this.startPoint.getX() >= Math.min(c.startPoint.getX(), c.endPoint.getX())
                        && this.startPoint.getX() <= Math.max(c.startPoint.getX(), c.endPoint.getX()))
                        && c.startPoint.getY() >= Math.min(this.startPoint.getY(), this.endPoint.getY())
                        && c.startPoint.getY() <= Math.max(this.startPoint.getY(), this.endPoint.getY())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                Connection xyMirroredConnectionThis = new Connection(new Point(this.startPoint.getY(), this.startPoint.getX()),
                        new Point(this.endPoint.getY(), this.endPoint.getX()));
                Connection xyMirroredConnectionC = new Connection(new Point(c.startPoint.getY(), c.startPoint.getX()),
                        new Point(c.endPoint.getY(), c.endPoint.getX()));
                a1 = calculateA(xyMirroredConnectionThis);
                a2 = calculateA(xyMirroredConnectionC);
                b1 = calculateB(xyMirroredConnectionThis);
                b2 = calculateB(xyMirroredConnectionC);

                if (a1 == a2) {
                    if (b1 == b2) {
                        if (xyMirroredConnectionThis.isParallelOverlapping(xyMirroredConnectionC)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
                x = (b2 - b1) / (a1 - a2);
                y = a1 * x + b1;
                if (isInConnectionRange(x, y, xyMirroredConnectionThis) && isInConnectionRange(x, y, xyMirroredConnectionC)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (c.startPoint.getX() == c.endPoint.getX()) {
            if (this.startPoint.getY() == this.endPoint.getY()) {
                if ((c.startPoint.getX() >= Math.min(this.startPoint.getX(), this.endPoint.getX())
                        && c.startPoint.getX() <= Math.max(this.startPoint.getX(), this.endPoint.getX()))
                        && this.startPoint.getY() >= Math.min(c.startPoint.getY(), c.endPoint.getY())
                        && this.startPoint.getY() <= Math.max(c.startPoint.getY(), c.endPoint.getY())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                Connection xyMirroredConnectionThis = new Connection(new Point(this.startPoint.getY(), this.startPoint.getX()),
                        new Point(this.endPoint.getY(), this.endPoint.getX()));
                Connection xyMirroredConnectionC = new Connection(new Point(c.startPoint.getY(), c.startPoint.getX()),
                        new Point(c.endPoint.getY(), c.endPoint.getX()));
                a1 = calculateA(xyMirroredConnectionThis);
                a2 = calculateA(xyMirroredConnectionC);
                b1 = calculateB(xyMirroredConnectionThis);
                b2 = calculateB(xyMirroredConnectionC);

                if (a1 == a2) {
                    if (b1 == b2) {
                        if (xyMirroredConnectionThis.isParallelOverlapping(xyMirroredConnectionC)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
                x = (b2 - b1) / (a1 - a2);
                y = a1 * x + b1;
                if (isInConnectionRange(x, y, xyMirroredConnectionThis) && isInConnectionRange(x, y, xyMirroredConnectionC)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        a1 = calculateA(this);
        a2 = calculateA(c);
        b1 = calculateB(this);
        b2 = calculateB(c);

        if (a1 == a2) {
            if (b1 == b2) {
                if (this.isParallelOverlapping(c)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
        x = (b2 - b1) / (a1 - a2);
        y = a1 * x + b1;
        if (isInConnectionRange(x, y, this) && isInConnectionRange(x, y, c)) {
            return true;
        } else {
            return false;
        }
    }

    public double calculateA(Connection c) { // a = (y1-y2)/(x1-x2)
        return (double) (c.getStartPoint().getY() - c.getEndPoint().getY()) / (c.getStartPoint().getX() - c.getEndPoint().getX());
    }

    public double calculateB(Connection c) { // b = (x1*y2-x2*y1)/(x1-x2)
        return (double) (c.getStartPoint().getX() * c.getEndPoint().getY() - c.getStartPoint().getY() * c.getEndPoint().getX()) / (c.getStartPoint().getX() - c.getEndPoint().getX());
    }

    public boolean isInConnectionRange(double x, double y, Connection c) {
        return x >= Math.min(c.getStartPoint().getX(), c.getEndPoint().getX())
                && x <= Math.max(c.getStartPoint().getX(), c.getEndPoint().getX())
                && y >= Math.min(c.getStartPoint().getY(), c.getEndPoint().getY())
                && y <= Math.max(c.getStartPoint().getY(), c.getEndPoint().getY());
    }

    public boolean isParallelOverlapping(Connection c) {
        return (this.getStartPoint().getX() >= Math.min(c.getStartPoint().getX(), c.getEndPoint().getX())
                && this.getStartPoint().getX() <= Math.max(c.getStartPoint().getX(), c.getEndPoint().getX()))
                || (c.getStartPoint().getX() >= Math.min(this.getStartPoint().getX(), this.getEndPoint().getX())
                && c.getStartPoint().getX() <= Math.max(this.getStartPoint().getX(), this.getEndPoint().getX()));
    }

}
