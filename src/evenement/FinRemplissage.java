package evenement;

import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

public class FinRemplissage extends Evenement{
	private Robot robot;

	public FinRemplissage(long date, SimulationRobotsPompiers simu, Robot robot) {
		super(date, simu);
		this.robot=robot;
	}

	@Override
	public void execute() {
		robot.setReservoirPlein();
		robot.setEtat(EtatRobot.INACTIF);
		simu.supprimeEvenement(this);
	}
	
	@Override
	public String toString () {
		return new String(super.toString() + "finRempl");
	}
}
