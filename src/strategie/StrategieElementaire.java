package strategie;

import carte.Carte;
import carte.Chemin;
import carte.Incendie;
import dijkstra.Dijkstra;
import evenement.ArroserIncendie;
import evenement.DemandeOrdres;
import evenement.DeplacerRobotChemin;
import evenement.Remplissage;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Strategie consistant a attribuer a chaque incendie un robot quelconque
 *
 */
public class StrategieElementaire implements Strategie {
	private SimulationRobotsPompiers simu;

	public StrategieElementaire() {
		this.simu = null;
	}

	@Override
	public void init(SimulationRobotsPompiers simu) {
		this.simu = simu;
	}

	@Override
	public void donnerOrdresRobots(Robot[] robots) {
		if (simu == null)
			throw new UnsupportedOperationException("Stratégie non initialisée");

		long dateSimu = simu.getDateSimulation();
		Incendie[] incendies = simu.donSimu.incendies;
		Carte carte = simu.donSimu.carte;

		// on precalcule les cartes ponderees des robots
		Dijkstra[] tableauDijk = new Dijkstra[robots.length];
		for (int i = 0; i < tableauDijk.length; i++) {
			tableauDijk[i] = new Dijkstra(carte, robots[i]);
			tableauDijk[i].calculeDijkstra();
		}

		for (Incendie incendie : incendies) {
			// si l'incendie n'est pas encore eteint
			// et si personne n'intervient deja dessus
			if (incendie.getIntensite() > 0 && incendie.getIntervenant() == null) {
				for (int i = 0; i < robots.length; i++) {
					Robot robot = robots[i];
					if (robot.getEtat() == EtatRobot.ATTENTE_ORDRES) {
						long date = dateSimu;
						Chemin chemin;
						try {
							chemin = tableauDijk[i]
									.plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
						} catch (UnsupportedOperationException e) {
							chemin = null;
						}
						// si le robot peut atteindre l'incendie
						if (chemin != null) {
							robot.setEtat(EtatRobot.INACTIF);
							if (robot.isReservoirPlein()) {
								simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robot, chemin));
								date += chemin.getPoids();
								simu.ajouteEvenement(new ArroserIncendie(date, simu, robot, incendie));
								incendie.setIntervenant(robot);
								break;
							} else {
								Chemin cheminRemp = robot.cheminRemplissage(simu);
								simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robot, cheminRemp));
								date += cheminRemp.getPoids();
								simu.ajouteEvenement(new Remplissage(date, simu, robot));
								date += robot.getTempsRemplissage();
								simu.ajouteEvenement(new DemandeOrdres(date, simu, robot));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void donnerOrdresRobots(Robot robot) {
		if (simu == null)
			throw new UnsupportedOperationException("Stratégie non initialisée");

		Robot[] robots = new Robot[1];
		robots[0] = robot;
		this.donnerOrdresRobots(robots);
	}

	@Override
	public void donnerOrdresRobots() {
		if (simu == null)
			throw new UnsupportedOperationException("Stratégie non initialisée");

		this.donnerOrdresRobots(simu.donSimu.robots);
	}
}