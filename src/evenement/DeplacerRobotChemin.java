package evenement;

import carte.Chemin;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Evenement de deplacement d'un robot selon un chemin en utilisant une serie de
 * deplacements elementaires
 *
 */
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
		simu.supprimeEvenement(this);
		for (int i = 0; i < chemin.getNombreDirections(); i++) {
			simu.ajouteEvenement(new DeplacerRobot(this.getDate(), this.simu, robot, chemin.getDirection(i)));
		}
	}

	@Override
	public String toString() {
		return new String(super.toString() + "deplChemin");
	}
}
