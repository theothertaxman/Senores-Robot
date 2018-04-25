
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class AllStar extends robocode.Robot {

    private final int NumRows = 13;
    private final int NumCols = 10;
    private final Point[] map = seed();


    public void run(){
    	
        int[] directions = getDirections();
        
        for (int i = 0; i < directions.length; i++) {

            if (directions[i] == 0) {
                switch((int) getHeading()) {

                    case 0: ahead(64);
                        break;
                    case 90: turnLeft(90);
                        ahead(64);
                        break;
                    case 180: turnLeft(180);
                    	ahead(64);
                    	break;
                    case 270: turnRight(90);
                        ahead(64);
                        break;
                }


            } else if (directions[i] == 90) {
            	
                switch((int) getHeading()) {

                    case 90: ahead(64);
                        break;
                    case 180: turnLeft(90);
                        ahead(64);
                        break;
                    case 270: turnLeft(180);
                    	ahead(64);
                    	break;
                    case 0: turnRight(90);
                        ahead(64);
                        break;
                }

            } else if (directions[i] == 180) {
            	
                switch((int) getHeading()) {

                    case 180: ahead(64);
                        break;
                    case 270: turnLeft(90);
                        ahead(64);
                        break;
                    case 0: turnLeft(180);
                    	ahead(64);
                    	break;
                    case 90: turnRight(90);
                        ahead(64);
                        break;
                }

            } else if (directions[i] == 270) {
            	
                switch((int) getHeading()) {

                    case 270: ahead(64);
                        break;
                    case 0: turnLeft(90);
                        ahead(64);
                        break;
                    case 90: turnLeft(180);
                    	ahead(64);
                    	break;
                    case 180: turnRight(90);
                        ahead(64);
                        break;
                }
            }
        }
        while (true){
        	turnLeft(90);
        }
    }


        //Generate map
         private Point[] seed(){

            int NumPixelRows = 13 * 64;
            int NumPixelCols = 10 * 64;
            int NumObstacles = (int) Math.floor(((NumPixelRows / 64) * (NumPixelCols / 64)) * 30 / 100);
            long seed = (long) 238859;
            ObstacleGenerator m = new ObstacleGenerator(NumPixelRows, NumPixelCols, NumObstacles + 2, seed);
            return m.getObs();
        }


    //A* algorithm----------------------------------------------------------------------
    public Node aStar() {
        //Initialize closed set
        ArrayList<Point> closed = new ArrayList<>();
        for (int c = 0; c < map.length - 2; c++) {
            closed.add(map[c]);
        }

        HashMap<Point, Node> open = new HashMap<Point,Node>();
        Node current = new Node(map[map.length-2]);
        open.put(current.getPoint(), current);
        boolean b = false;
        while (!open.isEmpty() && !b) {
            current = cheapest(open);
            if(current.getCost()==5){
            	int i =0;
            	System.out.println(i);
            }
            if (isGoal(current.getPoint())) {
                b = true;
            }else {
            	open.remove(current.getPoint());
                closed.add(current.getPoint());
                for (Node neighbour : current.neighbours()) {
                    if (neighbour != null){
                    	if (!closed.contains(neighbour.getPoint())) {
                            if (!contain(open, neighbour.getPoint())) {
                                open.put(neighbour.getPoint(), neighbour);
                            } else {
                                if (neighbour.getCost() < gett(open, neighbour.getPoint()).getCost()) {
                                	open.remove(neighbour.getPoint());
                                    open.put(neighbour.getPoint(), neighbour);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!b){
        	throw new RuntimeException("Something went wrong while searching for a path");
        }
        return current;
    }
    
    private boolean contain (HashMap<Point, Node> map, Point p){
    	boolean b = false; 
    	Iterator<Node> it = map.values().iterator();
    	while (!b && it.hasNext()){
    		if (it.next().getPoint().equals(p)){
    			b = true;
    		}
    	}
    	return b;
    }
    
    private Node gett (HashMap<Point, Node> map, Point p){
    	boolean b = false;
    	Node n = null;
    	Iterator<Node> it = map.values().iterator();
    	while (!b && it.hasNext()){
    		n = it.next();
    		if (n.getPoint().equals(p)){
    			b = true;
    			
    		}
    	}
    	if (!b){
    		n = null;
    	}
    	return n;
    }
    

    //Cheapest Node function ----------------------------------------------------------------------
    private Node cheapest(HashMap<Point, Node> op) {
        Node cheap = null, current = null;
        int g = 0, cur;
        Iterator<Node> it = op.values().iterator();
        if (!it.hasNext()) {
            throw new RuntimeException("Search error (No nodes in openset)");
        }
        current = it.next();
        cheap = current;
        g = cheap.getCost() + heuristic(cheap.getPoint());
        while (it.hasNext()) {
            current = it.next();
            cur = current.getCost() + heuristic(current.getPoint());
            if (cur < g) {
                cheap = current;
                g = cheap.getCost() + heuristic(cheap.getPoint());
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

        int[] directions = new int[child.cost];

        while (child.parent != null) {

            if (child.getPoint().getY() > child.parent.getPoint().getY()) {

                directions[child.cost-1] = 0;

            } else if (child.getPoint().getY() < child.parent.getPoint().getY()) {

                directions[child.cost-1] = 180;

            } else if (child.getPoint().getX() > child.parent.getPoint().getX()) {

                directions[child.cost-1] = 90;

            } else {

                directions[child.cost-1] = 270;
            }


            child = child.parent;

        }

        return directions;
    }





    //Node declaration----------------------------------------------------------------------
    public class Node {

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
            if (up.getY() < NumCols*64) {
                Node a = new Node(this, up);
                neighbours[c] = a;
                c++;
            }
            if (down.getY() >= 0) {
                Node b = new Node(this, down);
                neighbours[c] = b;
                c++;
            }
            if (right.getX() < NumRows*64) {
                Node cn = new Node(this, right);
                neighbours[c] = cn;
                c++;
            }
            if (left.getX() >= 0) {
                Node d = new Node(this, left);
                neighbours[c] = d;
            }
            return neighbours;
        }
        
        public String toString(){
        	String s = "";
        	s+= "(" + p.getX() + ", " + p.getY() + ")";
        	if (this.parent != null){
        		s += this.parent.toString();
        	}
        	return s;
        }
        
    }
    
    public Point[] getMap(){
    	return map;
    }
    
    public static void main(String[] args){
    	AllStar a = new AllStar();
    	System.out.println(a.aStar());
    }
    
}