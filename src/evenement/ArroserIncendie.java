package evenement;

import robots.Robot;
import simulation.SimulationRobotsPompiers;
import carte.Incendie;
import robots.EtatRobot;

public class ArroserIncendie extends Evenement {
	private Robot robot;
	private Incendie incendie;

	public ArroserIncendie(long date, SimulationRobotsPompiers simu, Robot robot, Incendie incendie) {
		super(date, simu);
		this.robot = robot;
		this.incendie = incendie;
	}

	@Override
	public void execute() {

		if (robot.getEtat() != EtatRobot.INACTIF) {
			simu.ajouteEvenement(new ArroserIncendie(robot.getFinAction().getDate(), simu, robot, incendie));
			simu.supprimeEvenement(this);
		} else {
			if (incendie.getIntensite()!=0) {
				robot.setEtat(EtatRobot.ARROSAGE);
				robot.setDateDebutArrosage(simu.getDateSimulation());
				incendie.ajouteRobot(robot, simu);
			}
			simu.supprimeEvenement(this);
		}
	}

	@Override
	public String toString() {
		return new String(super.toString() + "arros");
	}
}
