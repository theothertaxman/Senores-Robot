package LIJrobs;

import robocode.Robot;

import java.awt.*;
import java.util.HashMap;
import java.util.TreeSet;


public class AllStar extends robocode.Robot {

    private int heurist(int x, int y) {

    return (Math.abs(x) + Math.abs(y));
    }
    private Node aStar(){
        TreeSet<Point> closed = new TreeSet<>();
        HashMap<Point, Node> open = new HashMap<>();
        Node parent;
        Node current;
        while (!open.isEmpty()){
            current = cheapest(open);
            if (isGoal(current.getPoint())){
                return current;
            }
            open.remove(current.getPoint());
            closed.add(current.getPoint());
        for (Node neighbour : current.neighbours()){
            if(!closed.contains(neighbour.getPoint())){
                if (!open.containsKey(neighbour.getPoint())){
                    open.put(neighbour.getPoint(),neighbour);
                }else{
                    if(neighbour.getCost() < open.get(neighbour.getPoint()).getCost()){
                        open.put(neighbour.getPoint(), neighbour);
                    }
                }
            }
        }
        }
        throw new RuntimeException("Something went wrong while searching for a path");
    }

    private Node cheapest (HashMap op){
        return null;
    }

    private boolean isGoal(Point p){
        return true;
    }

    private class Node {

        Point p;
        int cost;

        public Point getPoint(){
            return p;
        }
        public int getCost(){
            return cost;
        }

        public Node[] neighbours(){
            return null;
        }
    }
      }

