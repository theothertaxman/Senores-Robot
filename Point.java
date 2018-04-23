package Bonacosa;
import robocode.control.RobotSetup;

public class Point {
    private int x;
    private int y;

   public Point(int n, int m) {
        x = n;
        y = m;
    }

   public Point(RobotSetup r) {
        x = r.getX().intValue();
        y = r.getY().intValue();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Point p) {
        return (x == p.getX()) && (y == p.getY());
    }


}
