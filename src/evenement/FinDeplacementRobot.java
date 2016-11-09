package evenement;

import carte.CaseCarte;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

public class FinDeplacementRobot extends Evenement{
	private Robot robot;
	private CaseCarte destination;

	public FinDeplacementRobot(long date, SimulationRobotsPompiers simu, Robot robot, CaseCarte destination) {
		super(date, simu);
		this.robot=robot;
		this.destination=destination;
	}

	@Override
	public void execute() {
		robot.deplacerCase(destination);
		robot.setEtat(EtatRobot.INACTIF);
		simu.supprimeEvenement(this);
	}
	
	@Override
	public String toString () {
		return new String(super.toString() + "finDepl");
	}
}
