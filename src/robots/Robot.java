package robots;

import carte.CaseCarte;
import carte.Chemin;
import carte.NatureTerrain;
import dijkstra.Dijkstra;
import evenement.Evenement;
import physique.Temps;
import simulation.SimulationRobotsPompiers;

public abstract class Robot {
	private int ligne, colonne, vitesse;
	private long dateDebutArrosage;
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
			temps += tailleCases / (2 * this.getVitesseTerrain(depart.getNature()) * 1000 / 3600);
			temps += tailleCases / (2 * this.getVitesseTerrain(destination.getNature()) * 1000 / 3600);
		} catch (ArithmeticException e) {
			temps = Temps.tempsInfini;
		}
		return temps;
	}

	public void deplacerCase(CaseCarte destination) {
		this.setLigne(destination.getLigne());
		this.setColonne(destination.getColonne());
	}

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
	
	public void deplacerChemin(Chemin chemin, SimulationRobotsPompiers simu){
		new DeplacerRobotChemin deplacement = DeplacerRobotChemin(simu.getDateSimulation(),simu,this,chemin);	
	}

	public void arroser(SimulationRobotsPompiers simu,Incendie incendie){
		new ArroserIncendie arrosage = ArroserIncendie(simu.getDateSimulation(),simu,this,incendie);
		arrosage.execute();
		new FinIncendie finIncendie = FinIncendie(simu.getDateSimulation(),simu,incendie);
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
		return dateDebutArrosage;
	}

	public abstract long getTempsRemplissage();

	public abstract float getDebitArrosage();

	public abstract long getTempsArrosage();

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

	@Override
	public String toString() {
		return new String("[" + ligne + "," + colonne + "] vitesse : " + vitesse);
	}
}
