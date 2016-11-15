package robots;

import carte.CaseCarte;
import carte.Chemin;
import carte.NatureTerrain;
import dijkstra.Dijkstra;
import evenement.Evenement;
import physique.Temps;
import physique.Vitesse;
import simulation.SimulationRobotsPompiers;

/**
 * Classe abstraite robot contenant la position, la vitesse de base du robot et
 * son état
 *
 */

public abstract class Robot {
	private int ligne, colonne, vitesse;
	private long dateDebutArrosage;
	private EtatRobot etat;
	private Evenement finAction;

	protected Robot(int ligne, int colonne, int vitesse) {
		this.setLigne(ligne);
		this.setColonne(colonne);
		this.setVitesse(vitesse);
		this.setEtat(EtatRobot.ATTENTE_ORDRES);
		this.dateDebutArrosage = -1;
		this.setFinAction(null);
	}

	/**
	 * Retourne la vitesse du robot en fonction due la nature du terrain
	 * 
	 * @param terrain
	 *            nature du terrain
	 * @return vitesse du robot
	 */
	public abstract int getVitesseTerrain(NatureTerrain terrain);

	public long tempsDeplacement(CaseCarte depart, CaseCarte destination, int tailleCases) {
		long temps = 0;

		double vitesseCase1 = (double) this.getVitesseTerrain(depart.getNature());
		double vitesseCase2 = (double) this.getVitesseTerrain(destination.getNature());
		if (vitesseCase1 == 0 || vitesseCase2 == 0)
			return Temps.tempsInfini;

		temps += (long) ((double) tailleCases / ((double) 2 * Vitesse.kmphVersMps(vitesseCase1)));
		temps += (long) ((double) tailleCases / ((double) 2 * Vitesse.kmphVersMps(vitesseCase2)));

		return temps;
	}

	/**
	 * Methode modifiant la position du robot pour le plcer sur la case
	 * destination
	 * 
	 * @param destination
	 */
	public void deplacerCase(CaseCarte destination) {
		this.setLigne(destination.getLigne());
		this.setColonne(destination.getColonne());
	}

	/**
	 * Retourne le chemin le plus court pour aller se remplir
	 * 
	 * @param simu
	 *            simulation
	 * @return chemin de remplissage
	 */
	public Chemin cheminRemplissage(SimulationRobotsPompiers simu) {
		Dijkstra dijk = new Dijkstra(simu.donSimu.carte, this);
		dijk.calculeDijkstra();
		Chemin chemin, cheminMin = null;
		for (int i = 0; i < simu.donSimu.carte.getNbLignes(); i++) {
			for (int j = 0; j < simu.donSimu.carte.getNbColonnes(); j++) {
				CaseCarte caseCourante = simu.donSimu.carte.getCase(i, j);
				if (this.conditionRemplissage(caseCourante)) {
					chemin = dijk.plusCourtChemin(caseCourante);
					if (cheminMin == null || chemin.getPoids() < cheminMin.getPoids()) {
						cheminMin = chemin;
					}
				}
			}
		}
		return cheminMin;
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
		return this.finAction;
	}

	public long getDateDebutArrosage() {
		if (dateDebutArrosage<0)
			throw new UnsupportedOperationException("date de début d'arrosage non initialisée");
		return dateDebutArrosage;
	}

	public abstract long getTempsRemplissage();

	public abstract boolean isReservoirPlein();

	public abstract float getDebitArrosage();

	public abstract long getTempsArrosage();

	/**
	 * Retourne un booleen representant le fait que le robot se remplisse sur ou
	 * a cote d'une case d'eau
	 * 
	 * @param caseCarte
	 * @return true si le robot peut se remplir sur caseCarte false sinon
	 */
	public abstract boolean conditionRemplissage(CaseCarte caseCarte);

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

	private void setVitesse(int vitesse) {
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

	public void setDateDebutArrosage(long dateDebutArrosage) {
		if (dateDebutArrosage < 0)
			throw new IllegalArgumentException("Argument incorrect : dateDebutArrosage = " + dateDebutArrosage);
		this.dateDebutArrosage = dateDebutArrosage;
	}

	public abstract void decrementeNiveauReservoir(int volumeDeverse);

	public abstract void setReservoirPlein();

	public abstract void setReservoirVide();

	public abstract String getFichierImage();

	@Override
	public String toString() {
		return new String("[" + ligne + "," + colonne + "] vitesse : " + vitesse + ", etat = " + this.getEtat());
	}
}
