package carte;

public class CaseCarteDijkstra extends CaseCarte {
	public static long poidsInfini=1000000;
	private Direction prec;
	private long poids;
	private boolean traitee;

	public CaseCarteDijkstra(CaseCarte caseCarte) {
		super(caseCarte.getLigne(), caseCarte.getColonne(), caseCarte.getNature());
		this.prec=null;
		this.poids=poidsInfini ;
		this.traitee=false;
	}
	
	public CaseCarteDijkstra(CaseCarte caseCarte, long poids) {
		super(caseCarte.getLigne(), caseCarte.getColonne(), caseCarte.getNature());
		this.prec=null;
		this.poids=poids;
		this.traitee=false;
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
		return new String(super.toString() + " prec = " + this.getPrec() + ", poids = " + this.getPoids());
	}
}
