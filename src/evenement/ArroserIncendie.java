package evenement;

import robots.Robot;
import simulation.SimulationRobotsPompiers;
import carte.Incendie;
import robots.EtatRobot;

/**
 * Evenement qui calcule l'evenement le plus prohe entre le reservoir du robot
 * qui est vide et l'incendie qui est eteint puis interagit avec la simulation en consequence
 *
 */
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
		simu.supprimeEvenement(this);
		// si le robot est occupe on reporte
		if (robot.getEtat() != EtatRobot.INACTIF)
			simu.ajouteEvenement(new ArroserIncendie(robot.getFinAction().getDate(), simu, robot, incendie));
		else {
			if (incendie.getIntensite() != 0) {
				robot.setEtat(EtatRobot.ARROSAGE);
				robot.setDateDebutArrosage(simu.getDateSimulation());
				long dureeArrosage = robot.getTempsArrosage(),
						dureeExtinction = (long) ((double) incendie.getIntensite() / (double) robot.getDebitArrosage());
				if (dureeArrosage < dureeExtinction) {
					simu.ajouteEvenement(
							new ReservoirVide(simu.getDateSimulation() + dureeArrosage, simu, incendie, robot));
				} else {
					simu.ajouteEvenement(new FinIncendie(simu.getDateSimulation() + dureeExtinction, simu, incendie));
				}
			} else {
				robot.setEtat(EtatRobot.ATTENTE_ORDRES);
				simu.strat.donnerOrdresRobots(robot);
			}
		}
	}

	@Override
	public String toString() {
		return new String(super.toString() + "arros");
	}
}
