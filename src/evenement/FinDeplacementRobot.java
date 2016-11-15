package evenement;

import carte.CaseCarte;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Evenement de fin de deplacement elementaire d'un robot avec mise a jour
 * effective de la position du robot
 */
public class FinDeplacementRobot extends Evenement {
	private Robot robot;
	private CaseCarte destination;

	public FinDeplacementRobot(long date, SimulationRobotsPompiers simu, Robot robot, CaseCarte destination) {
		super(date, simu);
		this.robot = robot;
		this.destination = destination;
	}

	@Override
	public void execute() {
		simu.supprimeEvenement(this);
		robot.deplacerCase(destination);
		robot.setEtat(EtatRobot.INACTIF);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "finDepl");
	}
}
