package searchpractice;
import robocode.control.*;

public class BR {
	
	public static void main (String[] args){
		
		//Create the RobocodeEngine
		RobocodeEngine engine = new RobocodeEngine (new java.io.File("/Users/nacho/robocode"));		
		
		//Show the robocode battle view
		engine.setVisible(true);
		
		// Create the battlefield
		int NumPixelRows = 13*64;
		int NumPixelCols = 10*64;
		BattlefieldSpecification battlefield = new BattlefieldSpecification(NumPixelRows, NumPixelCols);
		
		// Setup battle parameters
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		
		//Create obstacles and place them at random so that no pair of obstacle are at the same position
		int NumObstacles = (int) Math.floor(((NumPixelRows/64) * (NumPixelCols/64)) * 30 / 100);
		RobotSpecification[] modelRobots = engine.getLocalRepository ("sample.SittingDuck,sample.Crazy");
		RobotSpecification[] existingRobots = new RobotSpecification[NumObstacles+1];
		RobotSetup[] robotSetups = new RobotSetup[NumObstacles+1];
		long seed = (long) 23.12345;
		ObstacleGenerator gen = new ObstacleGenerator(battlefield, NumObstacles+1, seed);
		gen.generate();
		for(int Obstacle=0;Obstacle<NumObstacles;Obstacle++){
			existingRobots[Obstacle] = modelRobots[0];
			robotSetups[Obstacle] = gen.getObstacle(Obstacle);
		}
		
		//Create the agent and place it in a random position without obstacle
		existingRobots[NumObstacles] = modelRobots[1];
		robotSetups[NumObstacles] = gen.getObstacle(NumObstacles); 
		 
		// Create and run the battle
		BattleSpecification battleSpec = new BattleSpecification(
		battlefield,
		numberOfRounds,
		inactivityTime,
		gunCoolingRate,
		sentryBorderSize,
		hideEnemyNames,
		existingRobots,
		robotSetups); 
		
		// Run our specified battle and let it run till it is over
		 engine.runBattle(battleSpec, true); // waits till the battle finishes
		 
		 // Cleanup our RobocodeEngine
		 engine.close();
		 
		 // Make sure that the Java VM is shut down properly
		 System.exit(0); 
	}
	
}
