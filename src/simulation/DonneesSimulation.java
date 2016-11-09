package simulation;

import robots.Robot;

import carte.Carte;
import carte.Incendie;

public class DonneesSimulation {
	public Carte carte;
	public Robot robots[];
	public Incendie incendies[];
	
	public DonneesSimulation(Carte carte, Incendie[] incendies, Robot[] robots) {
		this.carte=carte;
		this.incendies=incendies;
		this.robots=robots;
	}
	
	@Override
	public String toString() {
		String s = new String();
		s+="Carte :\n" + this.carte.toString();
		s+="\nRobots :\n";
		for (Robot robot : robots)
			s+=robot.toString() + "\n";
		s+="Incendies :\n";
		for (Incendie incendie : incendies)
			s+=incendie.toString() + "\n";
		return s; 
	}
}
