package carte;

/**
 * Carte implementee par un tableau 2D de cases
 * 
 */
public class Carte {
	private CaseCarte cases[][];
	private int tailleCases, nbLignes, nbColonnes;

	/**
	 * Unique constructeur de cette classe qui cree le tableau de cases sans le
	 * remplir
	 * 
	 * @param nbLignes
	 *            nombre de lignes du tableau
	 * @param nbColonnes
	 *            nombre de colonnes du tableau
	 * @param tailleCases
	 *            taille des cases en metres
	 */
	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		if (nbColonnes < 1)
			throw new IllegalArgumentException("Argument incorrect : nbColonnes = " + nbColonnes);
		this.nbColonnes = nbColonnes;

		if (nbLignes < 1)
			throw new IllegalArgumentException("Argument incorrect : nbLignes = " + nbLignes);
		this.nbLignes = nbLignes;

		if (tailleCases < 1)
			throw new IllegalArgumentException("Argument incorrect : nbLignes = " + tailleCases);
		this.tailleCases = tailleCases;

		this.cases = new CaseCarte[nbLignes][nbColonnes];
	}

	/**
	 * Cette methode permet de marquer les berges (cases voisines d'une case
	 * d'eau) dans le but de faciliter le remplissage des robots qui se
	 * remplissent sur des berges
	 */
	public void decouverteBerges() {
		try {
			CaseCarte caseCourante, voisin;
			for (int i = 0; i < this.getNbLignes(); i++) {
				for (int j = 0; j < this.getNbColonnes(); j++) {
					caseCourante = this.getCase(i, j);
					if (caseCourante.getNature() == NatureTerrain.EAU) {
						for (Direction dir : Direction.values()) {
							if (this.existeVoisin(caseCourante.getLigne(), caseCourante.getColonne(), dir)) {
								voisin = this.getVoisin(caseCourante.getLigne(), caseCourante.getColonne(), dir);
								if (voisin.getNature() != NatureTerrain.EAU)
									voisin.setBerge(true);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new UnsupportedOperationException("Echec de la découverte des berges");
		}
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
		if (0 > lig || lig >= this.getNbLignes() || 0 > col || col >= this.getNbColonnes())
			throw new IllegalArgumentException("Limites de la carte atteintes");

		return cases[lig][col];
	}

	/**
	 * Retourne la case voisine
	 * 
	 * @param lig
	 *            ligne de la case
	 * @param col
	 *            colonne de la case
	 * @param dir
	 *            direction du voisin
	 * @return case voisine
	 */
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

		return this.getCase(lig, col);
	}

	/**
	 * Retourne true si la case voisine existe false sinon
	 * 
	 * @param lig
	 *            ligne de la case
	 * @param col
	 *            colonne de la case
	 * @param dir
	 *            direction du voisin
	 * @return booleen
	 */
	public boolean existeVoisin(int lig, int col, Direction dir) {
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

		if (0 > lig || lig >= getNbLignes() || 0 > col || col >= getNbColonnes())
			return false;

		return true;
	}

	public void setCase(int ligne, int colonne, CaseCarte caseCarte) {
		if (ligne < 0)
			throw new IllegalArgumentException("Argument incorrect : ligne = " + ligne);
		if (colonne < 0)
			throw new IllegalArgumentException("Argument incorrect : colonne = " + colonne);
		this.cases[ligne][colonne] = caseCarte;
	}

	@Override
	public String toString() {
		String s = new String(
				this.nbLignes + "*" + this.nbColonnes + " cases de taille " + this.tailleCases + "m de côté");
		for (int i = 0; i < this.getNbLignes(); i++)
			for (int j = 0; j < this.getNbColonnes(); j++)
				s += "\n    " + this.getCase(i, j).toString();
		return s;
	}
}
