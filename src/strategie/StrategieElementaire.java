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

public class StrategieElementaire implements Strategie {
	private SimulationRobotsPompiers simu;

	public StrategieElementaire() {
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
		for (Incendie incendie : incendies) {
			if (incendie.getIntensite() > 0 && incendie.getNbIntervenants() == 0) {
				for (Robot robot : robots) {
					if (robot.getEtat() == EtatRobot.ATTENTE_ORDRES) {
						long date = dateSimu;
						robot.setEtat(EtatRobot.INACTIF);
						Dijkstra dijk = new Dijkstra(carte, robot);
						dijk.calculeDijkstra();
						if (robot.isReservoirPlein()) {
							try {
								Chemin chemin = dijk
										.plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
								simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robot, chemin));
								date += chemin.getPoids();
								simu.ajouteEvenement(new ArroserIncendie(date, simu, robot, incendie));
							} catch (UnsupportedOperationException e) {
								
							}

						} else {
							Chemin cheminRemp = robot.cheminRemplissage(simu);
							simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robot, cheminRemp));
							date += cheminRemp.getPoids();
							simu.ajouteEvenement(new Remplissage(date, simu, robot));
							date += robot.getTempsRemplissage();
							simu.ajouteEvenement(new DemandeOrdres(date, simu, robot));
						}
						break;
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