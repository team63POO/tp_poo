package strategie;

import carte.Carte;
import carte.Incendie;
import robots.Robot;
import physique.Temps;

public class StrategieUnPeuPlusEvoluee {

	public StrategieElementaire(Incendie incendies[], Robot robots[], Carte carte){
		new Dijkstra[] tableauDijk;
		for ( int i=0 , i<robots.length , i++ ){
			Dijkstra[i] = new Dijkstra(carte, robots[i]);
		}
		
		int noRobot;
		
		for(Incendie incendie : incendies) {
			
			long poids = Temps.tempsInfini;
			for(int j=0, j<robots.length , j++) {
				if (robots[noRobot].etat == INACTIF){//fct attene d'ordre a faire ds robots
					new Chemin chemin = dijk.plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
					if (chemin.poids < poids){
						noRobot = j;
					}
				}
			robots[noRobot].deplacerChemin(simu,chemin);
			robots[noRobot].arroser(simu,incendie);
			}
		}
	}
}