package strategie;

import carte.Carte;
import carte.Incendie;
import robots.Robot;
import dijkstra
import carte

public class StrategieElementaire {

	public StrategieElementaire(SimulationRobotsPompiers simu, Incendie incendies[], Robot robots[], Carte carte){
		
		for(Incendie incendie : incendies) {
			for(Robot robot : robots) {
				if (robot.etat == INACTIF){//fonction attente ordre
					new Dijkstra dijk = Dijkstra(carte, robot);
					dijk.calculerDijkstra();
					new Chemin chemin = dijk.plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
					robot.deplacerChemin(simu,chemin);
					robot.arroser(simu,incendie);
					break;
				}
			}
		}
	}
}