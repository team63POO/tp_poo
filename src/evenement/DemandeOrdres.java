package evenement;

/**
 * Evenement de demande d'ordres sur un robot particulier
 */
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

public class DemandeOrdres extends Evenement {
	private Robot robot;

	public DemandeOrdres(long date, SimulationRobotsPompiers simu, Robot robot) {
		super(date, simu);
		this.robot = robot;
	}

	@Override
	public void execute() {
		simu.supprimeEvenement(this);
		robot.setEtat(EtatRobot.ATTENTE_ORDRES);
		simu.strat.donnerOrdresRobots(robot);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "demandeOrdres");
	}
}
