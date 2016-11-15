package strategie;

import carte.Carte;
import carte.Chemin;
import carte.Incendie;
import dijkstra.CaseCarteDijkstra;
import dijkstra.Dijkstra;
import evenement.ArroserIncendie;
import evenement.DemandeOrdres;
import evenement.DeplacerRobotChemin;
import evenement.Remplissage;
import physique.Temps;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

/**
 * Strategie consistant a attribuer a chaque incendie un robot quelconque
 *
 */
public class StrategieUnPeuPlusEvoluee implements Strategie {
	private SimulationRobotsPompiers simu;

	public StrategieUnPeuPlusEvoluee() {
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
				int iMin = -1;
				long poidsMin = Temps.tempsInfini;
				// on trouve le robot le plus proche de cet incendie
				for (int i = 0; i < robots.length; i++) {
					Robot robot = robots[i];
					CaseCarteDijkstra caseIncendie = (CaseCarteDijkstra) tableauDijk[i].carte.getCase(robot.getLigne(),
							robot.getColonne());
					if (caseIncendie.getPoids() < poidsMin && robot.getEtat() == EtatRobot.ATTENTE_ORDRES) {
						long date = dateSimu;
						robot.setEtat(EtatRobot.INACTIF);
						if (robot.isReservoirPlein()) {
							iMin = i;
							poidsMin = caseIncendie.getPoids();
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
				if (iMin != -1) {
					Chemin chemin;
					long date = dateSimu;
					Robot robotMin = robots[iMin];
					try {
						chemin = tableauDijk[iMin]
								.plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
					} catch (UnsupportedOperationException e) {
						chemin = null;
					}
					if (chemin != null) {
						robotMin.setEtat(EtatRobot.INACTIF);
						simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robotMin, chemin));
						date += chemin.getPoids();
						simu.ajouteEvenement(new ArroserIncendie(date, simu, robotMin, incendie));
						incendie.setIntervenant(robotMin);						
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