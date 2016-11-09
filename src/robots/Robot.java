package robots;

import carte.CaseCarte;
import carte.NatureTerrain;
import physique.Temps;

public abstract class Robot {
	private int ligne, colonne, vitesse;
	private EtatRobot etat;

	protected Robot(int ligne, int colonne, int vitesse) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.vitesse = vitesse;
		this.etat = EtatRobot.INACTIF;
	}

	public abstract int getVitesseTerrain (NatureTerrain terrain); 
	
	public long tempsDeplacement(CaseCarte depart, CaseCarte destination, int tailleCases) {
		long temps=0;
		try {
			temps+=tailleCases/(2*this.getVitesseTerrain(depart.getNature()));
			temps+=tailleCases/(2*this.getVitesseTerrain(destination.getNature()));			
		}
		catch (ArithmeticException e) {
			temps=Temps.tempsInfini;
		}
		return temps;
	}

	public void deplacerCase(CaseCarte destination) {
		this.setLigne(destination.getLigne());
		this.setColonne(destination.getColonne());
	}
	
	public int getLigne() {
		return ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public int getVitesse() {
		return vitesse;
	}

	public EtatRobot getEtat() {
		return this.etat;
	}

	public void setLigne(int ligne) {
		if (ligne<0)
			throw new IllegalArgumentException("Argument incorrect : ligne = " + ligne);
		this.ligne = ligne;
	}
	
	public void setColonne(int colonne) {		
		if (colonne<0)
			throw new IllegalArgumentException("Argument incorrect : colonne = " + colonne);
		this.colonne = colonne;
	}
	
	public void setVitesse(int vitesse) {
		if (vitesse<0)
			throw new IllegalArgumentException("Argument incorrect : vitesse = " + vitesse);
		this.vitesse = vitesse;
	}
	
	public void setEtat(EtatRobot etat) {
		this.etat = etat;
	}
	
	@Override
	public String toString() {
		return new String("[" + ligne + "," + colonne + "] vitesse : " + vitesse);
	}
}
