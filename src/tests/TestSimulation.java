package tests;

import java.awt.Color;

import carte.Incendie;
import dijkstra.Dijkstra;
import evenement.ArroserIncendie;
import evenement.DeplacerRobotChemin;
import gui.GUISimulator;
import robots.Robot;
import simulation.SimulationRobotsPompiers;
import strategie.Chemin;

public class TestSimulation {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
		SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/carteSujet.map");

		Robot robot = simu.donSimu.robots[0];
		Incendie incendie = simu.donSimu.incendies[0];
		
		Dijkstra dijk = new Dijkstra(simu.donSimu.carte, robot); dijk.calculeDijkstra();
		Chemin chemin = dijk.plusCourtChemin(simu.donSimu.carte.getCase(incendie.getLigne(), incendie.getColonne()) );
		simu.ajouteEvenement(new DeplacerRobotChemin(0, simu, robot, chemin));
		simu.ajouteEvenement(new ArroserIncendie(20, simu, robot, incendie));
	}
}