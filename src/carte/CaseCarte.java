package carte;

/**
 * Case de la carte qui stocke la nature du terrain qu'elle represente ainsi
 * qu'un booléen indiquant si c'est une berge ou non
 */
public class CaseCarte {
	private int ligne, colonne;
	private NatureTerrain nature;
	private boolean berge;

	public CaseCarte(int ligne, int colonne, NatureTerrain nature) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = nature;
		this.setBerge(false);
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

	public boolean isBerge() {
		return berge;
	}

	public void setBerge(boolean berge) {
		this.berge = berge;
	}

	@Override
	public String toString() {
		return new String("(" + this.getLigne() + ", " + this.getColonne() + ") " + this.getNature());
	}

	@Override
	public boolean equals(Object obj) {
		CaseCarte caseCarte = (CaseCarte) obj;
		return (this.getLigne() == caseCarte.getLigne() && this.getColonne() == caseCarte.getColonne());
	}
}
