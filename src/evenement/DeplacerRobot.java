package evenement;

import carte.CaseCarte;
import carte.Direction;
import robots.Robot;
import simulation.SimulationRobotsPompiers;
import robots.EtatRobot;

public class DeplacerRobot extends Evenement{
	private Robot robot;
	private Direction dir;
	SimulationRobotsPompiers simu;

	public DeplacerRobot(long date, SimulationRobotsPompiers simu, Robot robot, Direction dir) {
		super(date, simu);
		this.robot=robot;
		this.dir=dir;
		this.simu=simu;
	}

	@Override
	public void execute() {
		CaseCarte destination, depart;
		long tempsDeplacement;
		
		if (robot.getEtat()!=EtatRobot.INACTIF) {
			simu.ajouteEvenement(new DeplacerRobot(this.getDate()+1, simu, robot, dir));
			simu.supprimeEvenement(this);
		}
		else {
			robot.setEtat(EtatRobot.DEPLACEMENT);
			depart=simu.donSimu.carte.getCase(robot.getLigne(), robot.getColonne());
			destination=simu.donSimu.carte.getVoisin(robot.getLigne(), robot.getColonne(), dir);
		
			tempsDeplacement = robot.tempsDeplacement(depart, destination, simu.donSimu.carte.getTailleCases());
		
			simu.ajouteEvenement(new FinDeplacementRobot(this.getDate() + tempsDeplacement, simu, robot, destination));
			simu.supprimeEvenement(this);
		}
	}
	
	@Override
	public String toString () {
		return new String(super.toString() + "depl");
	}
}
