package tests;

import java.awt.Color;

import carte.Direction;
import evenement.DeplacerRobot;
import gui.GUISimulator;
import simulation.SimulationRobotsPompiers;
import strategie.Dijkstra;

public class TestSimulation {

	public static void main(String[] args) {
        // crée la fenêtre graphique dans laquelle dessiner
        GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
        SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/carteSujet.map");
        Dijkstra dijk = new Dijkstra(simu.donSimu.carte, simu.donSimu.robots[0]);
        dijk.calculeDijkstra();
        for (int i=0; i<dijk.carte.getNbLignes(); i++)
            for (int j=0; j<dijk.carte.getNbColonnes(); j++)
	        	System.out.println(dijk.carte.getCase(i, j));
        System.out.println(dijk.plusCourtChemin(simu.donSimu.carte.getCase(0, 0)));
        
        simu.ajouteEvenement(new DeplacerRobot(1, simu, simu.donSimu.robots[0], Direction.NORD));
        simu.ajouteEvenement(new DeplacerRobot(1, simu, simu.donSimu.robots[0], Direction.SUD));
        simu.ajouteEvenement(new DeplacerRobot(1, simu, simu.donSimu.robots[0], Direction.NORD));
        simu.ajouteEvenement(new DeplacerRobot(1, simu, simu.donSimu.robots[0], Direction.NORD));
        simu.ajouteEvenement(new DeplacerRobot(1, simu, simu.donSimu.robots[0], Direction.NORD));
	}
}
