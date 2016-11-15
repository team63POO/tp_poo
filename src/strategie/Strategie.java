package strategie;

import robots.Robot;
import simulation.SimulationRobotsPompiers;

public interface Strategie {	
	public void init(SimulationRobotsPompiers simu);
	public void donnerOrdresRobots(Robot[] robots);
	public void donnerOrdresRobots(Robot robot);
	public void donnerOrdresRobots();
}
