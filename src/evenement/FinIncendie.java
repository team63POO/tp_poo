package evenement;

import carte.Incendie;
import simulation.SimulationRobotsPompiers;

public class FinIncendie extends Evenement {
	private Incendie incendie;

	public FinIncendie(long date, SimulationRobotsPompiers simu, Incendie incendie) {
		super(date, simu);
		this.incendie = incendie;
	}

	@Override
	public void execute() {
		incendie.finIncendie(simu);
		simu.supprimeEvenement(this);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "finIncendie");
	}
}
