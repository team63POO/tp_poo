package robots;

import carte.CaseCarte;
import carte.NatureTerrain;
import evenement.Evenement;
import physique.Temps;

public abstract class Robot {
	private int ligne, colonne, vitesse;
	private EtatRobot etat;
	private Evenement finAction;

	protected Robot(int ligne, int colonne, int vitesse) {
		this.setLigne(ligne);
		this.setColonne(colonne);
		this.setVitesse(vitesse);
		this.setEtat(EtatRobot.INACTIF);
		this.setFinAction(null);
	}

	public abstract int getVitesseTerrain(NatureTerrain terrain);

	public long tempsDeplacement(CaseCarte depart, CaseCarte destination, int tailleCases) {
		long temps = 0;
		try {
			temps += tailleCases / (2 * this.getVitesseTerrain(depart.getNature()) * 1000/3600);
			temps += tailleCases / (2 * this.getVitesseTerrain(destination.getNature()) * 1000/3600);
		} catch (ArithmeticException e) {
			temps = Temps.tempsInfini;
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

	public Evenement getFinAction() {
		if (finAction == null)
			throw new UnsupportedOperationException("Pas d'évènement de fin d'action");
		return finAction;
	}

	public void setLigne(int ligne) {
		if (ligne < 0)
			throw new IllegalArgumentException("Argument incorrect : ligne = " + ligne);
		this.ligne = ligne;
	}

	public void setColonne(int colonne) {
		if (colonne < 0)
			throw new IllegalArgumentException("Argument incorrect : colonne = " + colonne);
		this.colonne = colonne;
	}

	public void setVitesse(int vitesse) {
		if (vitesse < 0)
			throw new IllegalArgumentException("Argument incorrect : vitesse = " + vitesse);
		this.vitesse = vitesse;
	}

	public void setEtat(EtatRobot etat) {
		this.etat = etat;
	}

	public void setFinAction(Evenement finAction) {
		this.finAction = finAction;
	}

	@Override
	public String toString() {
		return new String("[" + ligne + "," + colonne + "] vitesse : " + vitesse);
	}
}
