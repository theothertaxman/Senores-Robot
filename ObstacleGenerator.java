package searchpractice;
import robocode.control.*;
import java.util.Random;

public class ObstacleGenerator {
	
	private RobotSetup[] robots;
	private BattlefieldSpecification battlefield;
	private int numRobots;
	private Random rand;
	
	public ObstacleGenerator (BattlefieldSpecification battlefield, int numObstacles, long seed){
		this.robots = new RobotSetup[numObstacles];
		this.battlefield = battlefield;
		this.numRobots = 0;
		this.rand = new Random(seed);
	}
	
	public RobotSetup getObstacle (int pos){
		return robots[pos];
	}
	
	public void generate (){
		for (int c=0; c<robots.length; c++){
			this.generatePos(c);
			numRobots++;
		}
	}
	
	private void generatePos (int pos){
		RobotSetup setup;
		do {
			double x = (rand.nextInt(battlefield.getWidth()/64) * 64) + 32;
			double y = (rand.nextInt(battlefield.getHeight()/64) * 64) + 32;
			setup = new RobotSetup(x, y, 0.0);
		} while (this.reapeted(setup));
		robots[pos] = setup;
	}
	
	private boolean reapeted (RobotSetup setup){
		boolean b = false;
		int c = 0;
		while (!b && c < numRobots) {
			if (robots[c].getX().equals(setup.getX()) && robots[c].getY().equals(setup.getY())){
				b = true;
			}
			c++;
		}
		
		return b;
	}
	
}
