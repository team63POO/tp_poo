package strategie;

import carte.Carte;
import carte.Chemin;
import carte.Incendie;
import dijkstra.Dijkstra;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;


public class StrategieElementaire {

	public StrategieElementaire(SimulationRobotsPompiers simu, Incendie incendies[], Robot robots[]){
		
		Carte carte = simu.donSimu.carte;
		for(Incendie incendie : incendies) {
			for(Robot robot : robots) {
				if (robot.getEtat() == EtatRobot.INACTIF){//fonction attente ordre
					Dijkstra dijk = new Dijkstra(carte, robot);
					dijk.calculeDijkstra();
					Chemin chemin = dijk.plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
					robot.deplacerChemin(chemin,simu);
					robot.arroser(simu,incendie);
					break;
				}
			}
		}
	}
}