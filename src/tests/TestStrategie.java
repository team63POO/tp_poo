package tests;

import java.awt.Color;

import carte.Incendie;
import gui.GUISimulator;
import robots.Robot;
import simulation.SimulationRobotsPompiers;
import strategie.StrategieElementaire;
import strategie.StrategieUnPeuPlusEvoluee;

public class TestStrategie {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
		SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/carteSujet.map");

		Robot[] robots = simu.donSimu.robots;
		Incendie[] incendies = simu.donSimu.incendies;
		
		StrategieUnPeuPlusEvoluee strat= new StrategieUnPeuPlusEvoluee(simu, incendies, robots);
	}
}