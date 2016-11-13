package carte;

import java.util.LinkedList;
import java.util.ListIterator;

import evenement.DeplacerRobotChemin;
import evenement.Evenement;
import evenement.FinIncendie;
import evenement.Remplissage;
import evenement.ReservoirVide;
import physique.Temps;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;
import strategie.Chemin;

public class Incendie {
	private int ligne, colonne, intensite;
	private LinkedList<Robot> intervenants;
	private Evenement FinIncendie;

	public Incendie(int ligne, int colonne, int intensite) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.intensite = intensite;
		this.intervenants = null;
	}

	public void ajouteRobot(Robot robot, SimulationRobotsPompiers simu) {
		if (intervenants == null)
			intervenants = new LinkedList<Robot>();
		intervenants.add(robot);
		
		long dateFinIncendie, temps = Temps.tempsInfini;
		Robot robotMin = null;
		int volumeTotalDeverse = 0;
		float debitTotal = 0;
		long dateSimu = simu.getDateSimulation();

		ListIterator<Robot> iterateur = intervenants.listIterator();
		while (iterateur.hasNext()) {
			Robot robotCourant = iterateur.next();
			long tempsArrosage = robotCourant.getTempsArrosage();
			float debit = robotCourant.getDebitArrosage();
			int volumeDeverse = (int) ((dateSimu - robotCourant.getDateDebutArrosage()) * debit);
			robotCourant.decrementeNiveauReservoir(volumeDeverse);

			if (tempsArrosage < temps) {
				robotMin = robotCourant;
				temps = tempsArrosage;
			}

			volumeTotalDeverse += volumeDeverse;
			debitTotal += debit;
		}

		this.setIntensite(this.getIntensite() - volumeTotalDeverse);
		if (FinIncendie != null) {
			simu.supprimeEvenement(this.FinIncendie);
			FinIncendie = null;
		}
		if (debitTotal != 0) {
			dateFinIncendie = dateSimu + (long) (this.getIntensite() / debitTotal);
			FinIncendie = new FinIncendie(dateFinIncendie, simu, this);
			simu.ajouteEvenement(FinIncendie);	
		}
		else {
			dateFinIncendie = Temps.tempsInfini;
		}

		
		if (dateFinIncendie > (dateSimu + temps)) {
			simu.ajouteEvenement(new ReservoirVide(dateSimu + temps, simu, this, robotMin));
		} 	
		System.out.println("(dateSimu - robot.getDateDebutArrosage()) : " + (dateSimu - robot.getDateDebutArrosage()) 
				+ ", debit : " + robot.getDebitArrosage()
				+ ", VolumeTotalDeverse : " + volumeTotalDeverse 
				+ ", dateSimu : " + dateSimu 
				+ ", dateDebutArrosage : " + robot.getDateDebutArrosage() 
				+ "\n");
		System.out.print("intensiteIncendie : " + this.getIntensite() + "\n");
	}

	public void retireRobot(Robot robot, SimulationRobotsPompiers simu) {
		intervenants.remove(robot);

		long dateSimu = simu.getDateSimulation();
		long dateFinIncendie, temps = Temps.tempsInfini;
		Robot robotMin = null;
		int volumeTotalDeverse = (int) ((dateSimu - robot.getDateDebutArrosage()) * robot.getDebitArrosage());
		float debitTotal = 0;

		ListIterator<Robot> iterateur = intervenants.listIterator();
		while (iterateur.hasNext()) {
			Robot robotCourant = iterateur.next();
			long tempsArrosage = robotCourant.getTempsArrosage();
			float debit = robotCourant.getDebitArrosage();
			int volumeDeverse = (int) ((dateSimu - robotCourant.getDateDebutArrosage()) * debit);
			robotCourant.decrementeNiveauReservoir(volumeDeverse);

			if (tempsArrosage < temps) {
				robotMin = robotCourant;
				temps = tempsArrosage;
			}

			volumeTotalDeverse += volumeDeverse;
			debitTotal += debit;
		}

		this.setIntensite(this.getIntensite() - volumeTotalDeverse);
		if (FinIncendie != null) {
			simu.supprimeEvenement(this.FinIncendie);
			FinIncendie = null;
		}
		if (debitTotal != 0) {
			dateFinIncendie = dateSimu + (long) (this.getIntensite() / debitTotal);
			FinIncendie = new FinIncendie(dateFinIncendie, simu, this);
			simu.ajouteEvenement(FinIncendie);	
		}
		else {
			dateFinIncendie = Temps.tempsInfini;
		}
		
		if (dateFinIncendie > (dateSimu + temps)) {
			simu.ajouteEvenement(new ReservoirVide(dateSimu + temps, simu, this, robotMin));
		} 
	}

	public void finIncendie(SimulationRobotsPompiers simu) {
		long dateSimu = simu.getDateSimulation();
		this.setIntensite(0);
		ListIterator<Robot> iterateur = intervenants.listIterator();
		while (iterateur.hasNext()) {
			Robot robot = iterateur.next();
			float debit = robot.getDebitArrosage();
			int volumeDeverse = (int) ((dateSimu - robot.getDateDebutArrosage()) * debit);
			robot.decrementeNiveauReservoir(volumeDeverse);
			robot.setEtat(EtatRobot.INACTIF);
			Chemin chemin = robot.cheminRemplissage(simu);		
			simu.ajouteEvenement(new DeplacerRobotChemin(dateSimu, simu, robot, chemin));
			simu.ajouteEvenement(new Remplissage(dateSimu + chemin.getPoids(), simu, robot));
		}
		intervenants = null;
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

	public void setIntensite(int intensite) {
		if (intensite < 0)
			throw new IllegalArgumentException("Argument incorrect : intensite = " + intensite);
		this.intensite = intensite;
	}

	@Override
	public String toString() {
		return new String("[" + ligne + "," + colonne + "] intensité : " + intensite);
	}
}
