package tests;

import java.awt.Color;

import gui.GUISimulator;
import simulation.SimulationRobotsPompiers;
import strategie.StrategieElementaire;
import strategie.StrategieUnPeuPlusEvoluee;

@SuppressWarnings("unused")
public class TestStrategie {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 800, Color.WHITE);
		SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/desertOfDeath-20x20.map", new StrategieUnPeuPlusEvoluee());
	}
}