package dijkstra;

import carte.CaseCarte;
import carte.Direction;
import physique.Temps;

/**
 * Extension d'une case de carte avec ajout des attributs utiles a l'algorithme
 * de Dijkstra
 *
 */
public class CaseCarteDijkstra extends CaseCarte {
	private Direction prec;
	private long poids;
	private boolean traitee;

	public CaseCarteDijkstra(CaseCarte caseCarte) {
		super(caseCarte.getLigne(), caseCarte.getColonne(), caseCarte.getNature());
		this.setBerge(caseCarte.isBerge());
		this.prec = null;
		this.poids = Temps.tempsInfini;
		this.traitee = false;
	}

	public long getPoids() {
		return poids;
	}

	public boolean isTraitee() {
		return traitee;
	}

	public Direction getPrec() {
		return prec;
	}

	public void setPoids(long poids) {
		this.poids = poids;
	}

	public void setTraitee(boolean traitee) {
		this.traitee = traitee;
	}

	public void setPrec(Direction prec) {
		this.prec = prec;
	}

	@Override
	public String toString() {
		return new String(super.toString() + " prec = " + this.getPrec() + ", poids = " + this.getPoids() + ", berge = "
				+ this.isBerge());
	}
}
