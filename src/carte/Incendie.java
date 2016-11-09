package carte;

public class Incendie {
	private int ligne, colonne, intensite;

	public Incendie(int ligne, int colonne, int intensite) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.intensite = intensite;
	}
	
	public int getIntensite() {
		return intensite;
	}

	public void setIntensite(int intensite) {
		if (intensite<0)
			throw new IllegalArgumentException("Argument incorrect : intensite = " + intensite);
		this.intensite = intensite;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	@Override
	public String toString() {
		return new String("[" + ligne + "," + colonne + "] intensité : " + intensite);
	}
}
