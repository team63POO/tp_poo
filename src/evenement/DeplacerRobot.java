package evenement;

/**
 * Evenement de deplacement elementaire du robot dd'une case vers une voisine
 */
import carte.CaseCarte;
import carte.Direction;
import robots.Robot;
import simulation.SimulationRobotsPompiers;
import robots.EtatRobot;

public class DeplacerRobot extends Evenement {
	private Robot robot;
	private Direction dir;

	public DeplacerRobot(long date, SimulationRobotsPompiers simu, Robot robot, Direction dir) {
		super(date, simu);
		this.robot = robot;
		this.dir = dir;
	}

	@Override
	public void execute() {
		CaseCarte destination, depart;
		long tempsDeplacement;
		Evenement finDeplacement;

		simu.supprimeEvenement(this);
		// si le robot est occupe on reporte
		if (robot.getEtat() != EtatRobot.INACTIF) {
			simu.ajouteEvenement(new DeplacerRobot(robot.getFinAction().getDate(), simu, robot, dir));
		} else {
			robot.setEtat(EtatRobot.DEPLACEMENT);
			depart = simu.donSimu.carte.getCase(robot.getLigne(), robot.getColonne());
			destination = simu.donSimu.carte.getVoisin(robot.getLigne(), robot.getColonne(), dir);

			tempsDeplacement = robot.tempsDeplacement(depart, destination, simu.donSimu.carte.getTailleCases());

			finDeplacement = new FinDeplacementRobot(this.getDate() + tempsDeplacement, simu, robot, destination);
			simu.ajouteEvenement(finDeplacement);
			robot.setFinAction(finDeplacement);
		}
	}

	@Override
	public String toString() {
		return new String(super.toString() + "depl");
	}
}
