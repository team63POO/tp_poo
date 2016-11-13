package evenement;

import robots.Robot;
import simulation.SimulationRobotsPompiers;
import robots.EtatRobot;

public class Remplissage extends Evenement {
	private Robot robot;

	public Remplissage(long date, SimulationRobotsPompiers simu, Robot robot) {
		super(date, simu);
		this.robot = robot;
	}

	@Override
	public void execute() {
		long tempsRemplissage;
		Evenement finRemplissage;

		if (robot.getEtat() != EtatRobot.INACTIF) {
			simu.ajouteEvenement(new Remplissage(robot.getFinAction().getDate(), simu, robot));
			simu.supprimeEvenement(this);
		} else {
			robot.setEtat(EtatRobot.REMPLISSAGE);

			tempsRemplissage = robot.getTempsRemplissage();

			finRemplissage = new FinRemplissage(this.getDate() + tempsRemplissage, simu, robot);
			simu.ajouteEvenement(finRemplissage);
			robot.setFinAction(finRemplissage);
			simu.supprimeEvenement(this);
		}
	}

	@Override
	public String toString() {
		return new String(super.toString() + "rempl");
	}
}