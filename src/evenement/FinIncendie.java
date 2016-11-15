package evenement;

import carte.Incendie;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Evenement de fin d'incendie mettant a jour le reservoir du robot en
 * intervention ainsi que l'intensite de l'incendie
 */
public class FinIncendie extends Evenement {
	private Incendie incendie;

	public FinIncendie(long date, SimulationRobotsPompiers simu, Incendie incendie) {
		super(date, simu);
		this.incendie = incendie;
	}

	@Override
	public void execute() {
		simu.supprimeEvenement(this);
		Robot robot = incendie.getIntervenant();

		int volumeDeverse = incendie.getIntensite();
		incendie.setIntensite(0);
		incendie.setIntervenant(null);

		robot.decrementeNiveauReservoir(volumeDeverse);
		robot.setEtat(EtatRobot.ATTENTE_ORDRES);
		simu.strat.donnerOrdresRobots(robot);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "finIncendie");
	}
}
