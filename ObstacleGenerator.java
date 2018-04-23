package Bonacosa;


import java.util.Random;

public class ObstacleGenerator {

    private Point[] robots;
    private int x;
    private int y;
    private int numRobots;
    private Random rand;


    public ObstacleGenerator (int x, int y, int numObstacles, long seed){
        this.robots = new Point[numObstacles];
        this.x = x;
        this.y = y;
        this.numRobots = 0;
        this.rand = new Random(seed);
    }

    public Point getObstacle (int pos){
        return robots[pos];
    }

    public void generate (){
        for (int c=0; c<robots.length; c++){
            this.generatePos(c);
            numRobots++;
        }
    }

    private void generatePos (int pos){
        Point setup;
        do {
            int x = (rand.nextInt(this.x/64) * 64) + 32;
            int y = (rand.nextInt(this.y/64) * 64) + 32;
            setup = new Point(x, y);
        } while (this.reapeted(setup));
        robots[pos] = setup;
    }

    private boolean reapeted (Point setup){
        boolean b = false;
        int c = 0;
        while (!b && c < numRobots) {
            if (robots[c].getX() == setup.getX() && robots[c].getY() == setup.getY()){
                b = true;
            }
            c++;
        }

        return b;
    }

    public Point[] getObs(){
        return robots;
    }
}