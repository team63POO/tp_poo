package carte;

public class Carte {
	private CaseCarte cases[][];
	private int tailleCases, nbLignes, nbColonnes;

	public Carte (int nbLignes, int nbColonnes, int tailleCases) {
		if (nbColonnes<1)
			throw new IllegalArgumentException("Argument incorrect : nbColonnes = " + nbColonnes);
		this.nbColonnes=nbColonnes;
		if (nbLignes<1)
			throw new IllegalArgumentException("Argument incorrect : nbLignes = " + nbLignes);
		this.nbLignes=nbLignes;		
		if (tailleCases<1)
			throw new IllegalArgumentException("Argument incorrect : nbLignes = " + tailleCases);
		this.tailleCases=tailleCases;
		
		this.cases = new CaseCarte [nbLignes][nbColonnes];
	}

	public int getTailleCases() {
		return tailleCases;
	}

	public int getNbLignes() {
		return nbLignes;
	}

	public int getNbColonnes() {
		return nbColonnes;
	}
	
	public CaseCarte getCase(int lig, int col) {
		return cases[lig][col];
	}
	
	public CaseCarte getVoisin(int lig, int col, Direction dir) {
		switch (dir) {
        case NORD:
        	lig--;
            break;
        case SUD:
        	lig++;
            break;
        case EST:
        	col++;
            break;
        case OUEST:
        	col--;
            break;
        default: 
        	throw new IllegalArgumentException("Direction inconnue");
        }
		
		if (0>lig || lig>=getNbLignes()  || 0>col || col>=getNbColonnes()) {
			throw new UnsupportedOperationException("Limites de la carte atteintes");
		}
		
		return cases[lig][col];
	}

	public void setCase(int ligne, int colonne, CaseCarte caseCarte) {
		if (ligne<0)
			throw new IllegalArgumentException("Argument incorrect : ligne = " + ligne);
		if (colonne<0)
			throw new IllegalArgumentException("Argument incorrect : colonne = " + colonne);
		this.cases[ligne][colonne]=caseCarte;
	}
	
	@Override
	public String toString() {
		return new String(this.nbLignes + "x" + this.nbColonnes + " cases de taille " + this.tailleCases);
	}
}
