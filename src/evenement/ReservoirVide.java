package evenement;

import carte.Incendie;
import robots.EtatRobot;
import robots.Robot;
import simulation.SimulationRobotsPompiers;
import strategie.Chemin;

public class ReservoirVide extends Evenement {
	private Incendie incendie;
	private Robot robot;

	public ReservoirVide(long date, SimulationRobotsPompiers simu, Incendie incendie, Robot robot) {
		super(date, simu);
		this.incendie = incendie;
		this.robot = robot;
	}

	@Override
	public void execute() {
		long date = this.getDate();
		incendie.retireRobot(robot, simu);
		robot.setReservoirVide();
		robot.setEtat(EtatRobot.INACTIF);
		
		Chemin chemin = robot.cheminRemplissage(simu);		
		simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robot, chemin));
		date += chemin.getPoids();
		simu.ajouteEvenement(new Remplissage(date, simu, robot));
		date += robot.getTempsRemplissage();
		simu.ajouteEvenement(new DeplacerRobotChemin(date, simu, robot, chemin.inverse()));
		date += chemin.getPoids();
		simu.ajouteEvenement(new ArroserIncendie(date, simu, robot, incendie));
		
		simu.supprimeEvenement(this);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "reservoirVide");
	}
}
