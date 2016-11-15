package tests;

import java.awt.Color;

import carte.Carte;
import carte.Chemin;
import carte.Incendie;
import carte.NatureTerrain;
import dijkstra.Dijkstra;
import evenement.ArroserIncendie;
import evenement.DeplacerRobotChemin;
import gui.GUISimulator;
import physique.Vitesse;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;

public class TestSimulation {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
		SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/carteSujet.map");

		Robot robot = simu.donSimu.robots[1];
		robot.setEtat(EtatRobot.INACTIF);
		Incendie incendie = simu.donSimu.incendies[0];
		Carte carte = simu.donSimu.carte;
		
		
		Dijkstra dijk = new Dijkstra(carte, robot); dijk.calculeDijkstra();
		Chemin chemin = dijk.plusCourtChemin(carte.getCase(6, 0));
		System.out.println(chemin);
		System.out.println("tempsDeplacement = " + robot.tempsDeplacement(carte.getCase(6, 5), carte.getCase(6, 4), carte.getTailleCases()));
		//System.out.println("150kmph = " + Vitesse.kmphVersMps(150) + ", vitesseTerrainLibre = "
		//		+ robot.getVitesseTerrain(NatureTerrain.TERRAIN_LIBRE) + ", temps deplacement : "
		//		+ robot.tempsDeplacement(carte.getCase(6, 5), carte.getCase(5, 5), carte.getTailleCases()));
		
		simu.ajouteEvenement(new DeplacerRobotChemin(0, simu, robot, chemin));
		simu.ajouteEvenement(new ArroserIncendie(1, simu, robot, incendie));
		/*
		robot = simu.donSimu.robots[1];
		incendie = simu.donSimu.incendies[4];
		
		dijk = new Dijkstra(simu.donSimu.carte, robot); dijk.calculeDijkstra();
		chemin = dijk.plusCourtChemin(simu.donSimu.carte.getCase(incendie.getLigne(), incendie.getColonne()) );
		simu.ajouteEvenement(new DeplacerRobotChemin(0, simu, robot, chemin));
		simu.ajouteEvenement(new ArroserIncendie(20, simu, robot, incendie));*/
	}
}