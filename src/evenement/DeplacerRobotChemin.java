package evenement;

import robots.Robot;
import simulation.SimulationRobotsPompiers;
import strategie.Chemin;

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
		for (int i = 0; i < chemin.directions.length; i++) {
			simu.ajouteEvenement(new DeplacerRobot(this.getDate(), this.simu, robot, chemin.directions[i]));
		}

		simu.supprimeEvenement(this);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "depl chemin");
	}
}
