package carte;

import java.util.LinkedList;
import java.util.ListIterator;

import evenement.FinIncendie;
import evenement.ReservoirVide;
import physique.Temps;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

public class Incendie {
	private int ligne, colonne, intensite;
	private LinkedList<Robot> intervenants;

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
		this.majFinIncendie(simu);
	}

	public void retireRobot(Robot robot, SimulationRobotsPompiers simu) {
		intervenants.remove(robot);
		this.majFinIncendie(simu);
		System.out.print(this.getIntensite() + "\n");
	}

	public void majFinIncendie(SimulationRobotsPompiers simu) {
		long dateFinIncendie, temps = Temps.tempsInfini;
		Robot robotMin = null;
		int volumeTotalDeverse = 0;
		float debitTotal = 0;
		long dateSimu = simu.getDateSimulation();

		ListIterator<Robot> iterateur = intervenants.listIterator();
		while (iterateur.hasNext()) {
			Robot robot = iterateur.next();
			long tempsArrosage = robot.getTempsArrosage();
			float debit = robot.getDebitArrosage();
			int volumeDeverse = (int) ((dateSimu - robot.getDateDebutArrosage()) / debit);
			robot.decrementeNiveauReservoir(volumeDeverse);

			if (tempsArrosage < temps) {
				robotMin = robot;
				temps = tempsArrosage;
			}

			volumeTotalDeverse += volumeDeverse;
			debitTotal += debit;
			System.out.println("volume déversé : " + volumeDeverse + ", dateSimu : " + dateSimu + "\n");
		}

		this.setIntensite(this.getIntensite() - volumeTotalDeverse);
		dateFinIncendie = dateSimu + (long) (this.getIntensite() / debitTotal);

		if (dateFinIncendie < (dateSimu + temps)) {
			simu.ajouteEvenement(new FinIncendie(dateFinIncendie, simu, this));
		} else {
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
			int volumeDeverse = (int) ((dateSimu - robot.getDateDebutArrosage()) / debit);
			robot.decrementeNiveauReservoir(volumeDeverse);
			robot.setEtat(EtatRobot.INACTIF);
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
