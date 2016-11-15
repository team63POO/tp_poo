package dijkstra;

import carte.Carte;
import physique.Temps;

public class CarteDijkstra extends Carte {

	public CarteDijkstra(Carte carte) {
		super(carte.getNbLignes(), carte.getNbColonnes(), carte.getTailleCases());
		for (int i = 0; i < this.getNbLignes(); i++)
			for (int j = 0; j < this.getNbColonnes(); j++)
				this.setCase(i, j, new CaseCarteDijkstra(carte.getCase(i, j)));
	}

	public void init(int lig, int col) {
		for (int i = 0; i < this.getNbLignes(); i++)
			for (int j = 0; j < this.getNbColonnes(); j++)
				((CaseCarteDijkstra) this.getCase(i, j)).setPoids(Temps.tempsInfini);
		
		((CaseCarteDijkstra) this.getCase(lig, col)).setPoids(0);
	}

	public CaseCarteDijkstra minDijkstra() {
		CaseCarteDijkstra caseMin = null;
		long poidsMin = Temps.tempsInfini;

		for (int i = 0; i < this.getNbLignes(); i++) {
			for (int j = 0; j < this.getNbColonnes(); j++) {
				CaseCarteDijkstra caseCourante = (CaseCarteDijkstra) this.getCase(i, j);

				if (!(caseCourante.isTraitee()) && (caseCourante.getPoids() < poidsMin)) {
					caseMin = caseCourante;
					poidsMin = caseMin.getPoids();
				}
			}
		}
		
		if (caseMin == null)
			throw new UnsupportedOperationException("Aucune case accessible");

		return caseMin;
	}
}