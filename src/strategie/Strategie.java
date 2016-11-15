package strategie;

import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Interface strategie permettant de changer facilement de strategie pour la
 * simulation
 */
public interface Strategie {
	/**
	 * Associe la simulation simu a la strategie
	 * @param simu
	 */
	public void init(SimulationRobotsPompiers simu);

	/**
	 * Associe incendies et robots
	 * @param robots
	 */
	public void donnerOrdresRobots(Robot[] robots);
	
	/**
	 * Execute donnerOrdresRobots() sur robot
	 * @param robot
	 */
	public void donnerOrdresRobots(Robot robot);

	/**
	 * Execute donnerOrdresRobots() sur tous les robots de la simulation
	 */
	public void donnerOrdresRobots();
}
