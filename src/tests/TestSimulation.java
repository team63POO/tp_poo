package tests;

import java.awt.Color;

import carte.Direction;
import dijkstra.Dijkstra;
import evenement.DeplacerRobot;
import evenement.DeplacerRobotChemin;
import gui.GUISimulator;
import simulation.SimulationRobotsPompiers;
import strategie.Chemin;

public class TestSimulation {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
		SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/carteSujet.map");

		Dijkstra dijk = new Dijkstra(simu.donSimu.carte, simu.donSimu.robots[2]); dijk.calculeDijkstra();
		Chemin chemin = dijk.plusCourtChemin(simu.donSimu.carte.getCase(0, 0));
		simu.ajouteEvenement(new DeplacerRobotChemin(1, simu, simu.donSimu.robots[2], chemin));
		
		dijk = new Dijkstra(simu.donSimu.carte, simu.donSimu.robots[1]); dijk.calculeDijkstra();
		chemin = dijk.plusCourtChemin(simu.donSimu.carte.getCase(0, 0));
		simu.ajouteEvenement(new DeplacerRobotChemin(1, simu, simu.donSimu.robots[1], chemin));
		
		dijk = new Dijkstra(simu.donSimu.carte, simu.donSimu.robots[0]); dijk.calculeDijkstra();
		chemin = dijk.plusCourtChemin(simu.donSimu.carte.getCase(0, 0));
		simu.ajouteEvenement(new DeplacerRobotChemin(1, simu, simu.donSimu.robots[0], chemin));
	}
}