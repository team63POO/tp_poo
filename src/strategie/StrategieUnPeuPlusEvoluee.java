package strategie;

public class StrategieUnPeuPlusEvoluee {
/*
	public StrategieUnPeuPlusEvoluee(SimulationRobotsPompiers simu , Incendie incendies[], Robot robots[]){
		Carte carte = simu.donSimu.carte;
		Dijkstra[] tableauDijk = new Dijkstra[robots.length];
		for ( int i = 0 ; i < robots.length ; i++ ){
			tableauDijk[i] = new Dijkstra(carte, robots[i]);
		}
		
		int noRobot=0;
		Chemin cheminSelect = null;
		for(Incendie incendie : incendies) {
			
			long poids = Temps.tempsInfini;
			for(int j=0 ; j<robots.length ; j++) {
				if (robots[noRobot].getEtat() == EtatRobot.INACTIF){//fct attene d'ordre a faire ds robots
					Chemin chemin = tableauDijk[j].plusCourtChemin(carte.getCase(incendie.getLigne(), incendie.getColonne()));
					if (chemin.getPoids() < poids){
						noRobot = j;
						cheminSelect = chemin;
					}
				}
			robots[noRobot].deplacerChemin(cheminSelect,simu);
			robots[noRobot].arroser(simu,incendie);
			}
		}
	}*/
}