package carte;

import robots.Robot;

/**
 * Incendie stockant une inetnsite egale au volume en Litres d'eau necessaire a
 * son extinction, une position (ligne et colonne ainsi qu'un evenement de fin
 * d'incendie
 */
public class Incendie {
	private int ligne, colonne, intensite;
	private Robot intervenant;

	public Incendie(int ligne, int colonne, int intensite) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.intensite = intensite;
		this.setIntervenant(null);
	}

	public int getIntensite() {
		return intensite;
	}

	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public Robot getIntervenant() {
		return intervenant;
	}

	public void setIntensite(int intensite) {
		if (intensite < 0)
			throw new IllegalArgumentException("Intensite negative : " + intensite);
		this.intensite = intensite;
	}

	public void setIntervenant(Robot intervenant) {
		this.intervenant = intervenant;
	}
	
	@Override
	public String toString() {
		return new String("(" + ligne + "," + colonne + ") intensité : " + intensite);
	}
}
