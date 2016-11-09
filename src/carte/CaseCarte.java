package carte;

public class CaseCarte {
	private int ligne, colonne;
	private NatureTerrain nature;
	
	public CaseCarte(int ligne, int colonne, NatureTerrain nature) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = nature;
	}

	public int getLigne() {
		return ligne;
	}
	
	public int getColonne() {
		return colonne;
	}
	
	public NatureTerrain getNature() {
		return nature;
	}
	
	@Override
	public String toString() {
		return new String("(" + this.getLigne() + ", " + this.getColonne() + ") " + this.getNature());
	}
	
	@Override
	public boolean equals(Object obj){
		CaseCarte caseCarte = (CaseCarte) obj;
		return (this.getLigne()==caseCarte.getLigne() && this.getColonne()==caseCarte.getColonne() && this.getNature()==caseCarte.getNature());
	}
}
