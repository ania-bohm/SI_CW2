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
        double a1=0.0, a2=0.0, b1=0.0, b2=0.0, x1=0.0, y1=0.0, x2=0.0, y2=0.0, x=0.0, y=0.0;
        // if 'this' is vertical
        if (this.startPoint.getX() == this.endPoint.getX()) {
            // if 'connection' is horizontal
            if (c.startPoint.getY() == c.endPoint.getY()) {
                // if the vertical and horizontal sections are crossing
                if ((this.startPoint.getX() > Math.min(c.startPoint.getX(), c.endPoint.getX())
                        && this.startPoint.getX() < Math.max(c.startPoint.getX(), c.endPoint.getX()))
                        && c.startPoint.getY() > Math.min(this.startPoint.getY(), this.endPoint.getY())
                        && c.startPoint.getY() < Math.max(this.startPoint.getY(), this.endPoint.getY())) {
                     return true;
                } else { // the vertical and horizontal sections are not crossing
                    return false;
                }
            } else { // 'connection' is not horizontal and 'this' is vertical
                Connection xyMirroredConnectionThis = new Connection(new Point(this.startPoint.getY(), this.startPoint.getX()),
                        new Point(this.endPoint.getY(), this.endPoint.getX()));
                Connection xyMirroredConnectionC = new Connection(new Point(c.startPoint.getY(), c.startPoint.getX()),
                        new Point(c.endPoint.getY(), c.endPoint.getX()));
                a1 = calculateA(xyMirroredConnectionThis);
                a2 = calculateA(xyMirroredConnectionC);
                b1 = calculateB(xyMirroredConnectionThis);
                b2 = calculateB(xyMirroredConnectionC);

                // both sections have the same angle
                if (a1 == a2) {
                    // both sections lie on the same line
                    if (b1 == b2) {
                        if (xyMirroredConnectionThis.isParallelOverlapping(xyMirroredConnectionC)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
                x = division((b2 - b1), (a1 - a2));
                y = a1 * x + b1;
                if (isInConnectionRange(x, y, xyMirroredConnectionThis) + isInConnectionRange(x, y, xyMirroredConnectionC) > 3) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        // if 'connection' is vertical
        else if (c.startPoint.getX() == c.endPoint.getX()) {
            // if 'this' is horizontal
            if (this.startPoint.getY() == this.endPoint.getY()) {
                // if the vertical and horizontal sections are crossing
                if ((c.startPoint.getX() > Math.min(this.startPoint.getX(), this.endPoint.getX())
                        && c.startPoint.getX() < Math.max(this.startPoint.getX(), this.endPoint.getX()))
                        && this.startPoint.getY() > Math.min(c.startPoint.getY(), c.endPoint.getY())
                        && this.startPoint.getY() < Math.max(c.startPoint.getY(), c.endPoint.getY())) {
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
                x = division((b2 - b1), (a1 - a2));
                y = a1 * x + b1;
                if (isInConnectionRange(x, y, xyMirroredConnectionThis) + isInConnectionRange(x, y, xyMirroredConnectionC) > 3) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        else{
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
            x1 = division((b2 - b1), (a1 - a2));
            y1 = a1 * x1 + b1;
            x2 = division((b2 - b1), (a1 - a2));
            y2 = a2 * x2 + b2;
            if (isInConnectionRange(x1, y1, this) + isInConnectionRange(x2, y2, c) > 3) {
                return true;
            } else {
                System.out.println("this: "+this.toString() +", c: "+c.toString()+"x1,x2,y1,y2"+x1+","+x2+","+y1+","+y2);
                return false;
            }
        }

    }

    public double calculateA(Connection c) { // a = (y1-y2)/(x1-x2)
        double outcome = division( (double)(c.getStartPoint().getY() - c.getEndPoint().getY()) , (double)(c.getStartPoint().getX() - c.getEndPoint().getX()));
        return outcome;
    }

    public double calculateB(Connection c) { // b = (x1*y2-x2*y1)/(x1-x2)
        double outcome = division((double)((c.getStartPoint().getX()) * (c.getEndPoint().getY()) - (c.getStartPoint().getY() ) * (c.getEndPoint().getX())) , (c.getStartPoint().getX() * 1.0 - c.getEndPoint().getX() * 1.0));
        return outcome;
    }
    public double division(double a, double b){
        return (double) a/b;
    }

    public int isInConnectionRange(double x, double y, Connection c) {//0 - not crossing, 1 - at end, 3 - crossing
        double offset = 0.000001;
        if(c.startPoint.getY()==c.endPoint.getY()){
            offset =0.000001;
        }
        else{
            offset = -0.000001;
        }

        boolean isInRange = (x+offset > Math.min(c.getStartPoint().getX(), c.getEndPoint().getX())
                && x-offset < Math.max(c.getStartPoint().getX(), c.getEndPoint().getX())
                && y+offset > Math.min(c.getStartPoint().getY(), c.getEndPoint().getY())
                && y-offset < Math.max(c.getStartPoint().getY(), c.getEndPoint().getY()));
        boolean isAtEnd = (x == c.getStartPoint().getX() && y == c.getStartPoint().getY()
                || x == c.getEndPoint().getX() && y == c.getEndPoint().getY()) ||
                (x == this.getStartPoint().getX() && y == this.getStartPoint().getY()
                        || x == this.getEndPoint().getX() && y == this.getEndPoint().getY());
        if (isAtEnd) {
            return 1;
        } else {
            if (isInRange) {
                return 3;
            } else {
                return 0;
            }
        }
    }

    public boolean isParallelOverlapping(Connection c) {
        return (this.getStartPoint().getX() > Math.min(c.getStartPoint().getX(), c.getEndPoint().getX())
                && this.getStartPoint().getX() < Math.max(c.getStartPoint().getX(), c.getEndPoint().getX()))
                || (c.getStartPoint().getX() > Math.min(this.getStartPoint().getX(), this.getEndPoint().getX())
                && c.getStartPoint().getX() < Math.max(this.getStartPoint().getX(), this.getEndPoint().getX())
                || (this.getEndPoint().getX() > Math.min(c.getStartPoint().getX(), c.getEndPoint().getX())
                && this.getEndPoint().getX() < Math.max(c.getStartPoint().getX(), c.getEndPoint().getX()))
                || (c.getEndPoint().getX() > Math.min(this.getStartPoint().getX(), this.getEndPoint().getX())
                && c.getEndPoint().getX() < Math.max(this.getStartPoint().getX(), this.getEndPoint().getX())));
    }

}
