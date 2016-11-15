package tests;

import java.awt.Color;

import gui.GUISimulator;
import simulation.SimulationRobotsPompiers;
import strategie.StrategieElementaire;

public class TestStrategie {

	public static void main(String[] args) {
		// crée la fenêtre graphique dans laquelle dessiner
		GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
		@SuppressWarnings("unused")
		SimulationRobotsPompiers simu = new SimulationRobotsPompiers(gui, "cartes/mushroomOfHell-20x20.map", new StrategieElementaire());
	}
}