package evenement;

import carte.Chemin;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

public class DeplacerRobotChemin extends Evenement {
	private Robot robot;
	private Chemin chemin;

	public DeplacerRobotChemin(long date, SimulationRobotsPompiers simu, Robot robot, Chemin chemin) {
		super(date, simu);
		this.robot = robot;
		this.chemin = chemin;
	}

	@Override
	public void execute() {
		for (int i = 0; i < chemin.getNombreDirections(); i++) {
			simu.ajouteEvenement(new DeplacerRobot(this.getDate(), this.simu, robot, chemin.getDirection(i)));
		}

		simu.supprimeEvenement(this);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "depl chemin");
	}
}
