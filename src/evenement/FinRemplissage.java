package evenement;

import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Evenement de fin de remplissage avec mise a jour effective du reservoir du
 * robot
 */
public class FinRemplissage extends Evenement {
	private Robot robot;

	public FinRemplissage(long date, SimulationRobotsPompiers simu, Robot robot) {
		super(date, simu);
		this.robot = robot;
	}

	@Override
	public void execute() {
		simu.supprimeEvenement(this);
		robot.setReservoirPlein();
		robot.setEtat(EtatRobot.INACTIF);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "finRempl");
	}
}
