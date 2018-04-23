package Bonacosa;



import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class AllStar2 extends robocode.Robot {

    private final int NumRows = 10;
    private final int NumCols = 10;
    private final Point[] map = seed();


    public void run(){
        int[] directions = getDirections();

        int heading = (int) getHeading();

        for (int i = 0; i < directions.length; i++) {

            if (directions[i] == 0) {

                switch(heading) {

                    case 0: ahead(64);
                        break;
                    case 90: turnLeft(90);
                        ahead(64);
                        break;
                    /*case 180: turnLeft(180);
                    ahead(64);
                    break;*/
                    case 270: turnRight(90);
                        ahead(64);
                        break;
                }


            } else if (directions[i] == 90) {

                switch(heading) {

                    case 90: ahead(64);
                        break;
                    case 180: turnLeft(90);
                        ahead(64);
                        break;
                    /*case 270: turnLeft(180);
                    ahead(64);
                    break;*/
                    case 0: turnRight(90);
                        ahead(64);
                        break;
                }

            } else if (directions[i] == 180) {

                switch(heading) {

                    case 180: ahead(64);
                        break;
                    case 270: turnLeft(90);
                        ahead(64);
                        break;
                    /*case 0: turnLeft(180);
                    ahead(64);
                    break;*/
                    case 90: turnRight(90);
                        ahead(64);
                        break;
                }

            } else if (directions[i] == 270) {

                switch(heading) {

                    case 270: ahead(64);
                        break;
                    case 0: turnLeft(90);
                        ahead(64);
                        break;
                    /* case 90: turnLeft(180);
                    ahead(64);
                    break;*/
                    case 180: turnRight(90);
                        ahead(64);
                        break;
                }
            }
        }

    }


        //Generate map
         private Point[] seed(){

            int NumPixelRows = 13 * 64;
            int NumPixelCols = 10 * 64;
            int NumObstacles = (int) Math.floor(((NumPixelRows / 64) * (NumPixelCols / 64)) * 30 / 100);
            long seed = (long) 23.12345;
            ObstacleGenerator m = new ObstacleGenerator(NumPixelRows, NumPixelCols, NumObstacles + 2, seed);

            return m.getObs();
        }


    //A* algorithm----------------------------------------------------------------------
    private Node aStar() {
        //Initialize closed set
        TreeSet<Point> closed = new TreeSet<>();
        for (int c = 0; c < map.length - 2; c++) {
            closed.add(map[c]);
        }

        HashMap<Point, Node> open = new HashMap<>();
        Node current = new Node(map[2]);
        open.put(current.getPoint(), current);
        while (!open.isEmpty()) {
            current = cheapest(open);
            if (isGoal(current.getPoint())) {
                return current;
            }
            open.remove(current.getPoint());
            closed.add(current.getPoint());
            for (Node neighbour : current.neighbours()) {
                if (!closed.contains(neighbour.getPoint())) {
                    if (!open.containsKey(neighbour.getPoint())) {
                        open.put(neighbour.getPoint(), neighbour);
                    } else {
                        if (neighbour.getCost() < open.get(neighbour.getPoint()).getCost()) {
                            open.put(neighbour.getPoint(), neighbour);
                        }
                    }
                }
            }
        }
        throw new RuntimeException("Something went wrong while searching for a path");
    }


    //Cheapest Node function ----------------------------------------------------------------------
    private Node cheapest(HashMap op) {
        Node cheap = null, current = null;
        int g = 0, cur;
        Iterator<Node> it = op.values().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Search error (No nodes in openset)");
        }
        cheap = it.next();
        g = current.getCost() + heuristic(current.getPoint());
        while (it.hasNext()) {
            current = it.next();
            cur = current.getCost() + heuristic(current.getPoint());
            if (cur < g) {
                cheap = current;
            }
        }
        return cheap;
    }



    // Is goal Function----------------------------------------------------------------------
    private boolean isGoal(Point p) {
        Point pt = map[map.length - 1];
        return pt.equals(p);

    }

    //Heuristic function----------------------------------------------------------------------
    private int heuristic(Point p) {
        Point goal = map[map.length - 1];
        return Math.abs(goal.getX() - p.getX()) + Math.abs(goal.getY() - p.getY());
    }



    //Get Directions-------------------------------------------------------------------------
    private int[] getDirections() {

        Node child = aStar();

        int[] directions = new int[child.cost + 1];

        while (child.parent != null) {

            if (child.getPoint().getY() > child.parent.getPoint().getY()) {

                directions[child.cost] = 0;

            } else if (child.getPoint().getY() < child.parent.getPoint().getY()) {

                directions[child.cost] = 180;

            } else if (child.getPoint().getX() > child.parent.getPoint().getX()) {

                directions[child.cost] = 90;

            } else {

                directions[child.cost] = 270;
            }


            child = child.parent;

        }

        return directions;
    }





    //Node declaration----------------------------------------------------------------------
    private class Node {

        Node parent;
        Point p;
        int cost;

        private Node(Node par, Point pt) {
            parent = par;
            p = pt;
            cost = par.getCost() + 1;
        }

        private Node(Point pt) {
            p = pt;
            cost = 0;
            parent = null;
        }

        private Point getPoint() {
            return p;
        }

        private int getCost() {
            return cost;
        }

        private Node[] neighbours() {
            Point up = new Point(p.getX(), p.getY() + 64);
            Point down = new Point(p.getX(), p.getY() - 64);
            Point right = new Point(p.getX() + 64, p.getY());
            Point left = new Point(p.getX() - 64, p.getY());
            int c = 0;
            Node[] neighbours = new Node[4];
            if (up.getY() < NumRows) {
                Node a = new Node(this, up);
                neighbours[c] = a;
                c++;
            }
            if (down.getY() >= 0) {
                Node b = new Node(this, down);
                neighbours[c] = b;
                c++;
            }
            if (right.getX() < NumCols) {
                Node cn = new Node(this, right);
                neighbours[c] = cn;
                c++;
            }
            if (left.getY() >= 0) {
                Node d = new Node(this, left);
                neighbours[c] = d;
            }
            return neighbours;
        }
    }


}
