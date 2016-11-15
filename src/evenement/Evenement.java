package evenement;

import simulation.SimulationRobotsPompiers;

/**
 * Classe abstraite evenement permettant de stocker la simulation associee ainsi
 * que la date de l'evenement
 *
 */
public abstract class Evenement implements Comparable<Evenement> {
	/**
	 * compteur d'instances permettant de comparer les evenement par ordre
	 * chronologique d'instanciation
	 */
	private static long eventCtr = 0;
	/** numero d'instance */
	private long id;
	/** date de l'eveneemnt */
	private long date;
	/** simulation sur laquelle agit l'evenement */
	protected SimulationRobotsPompiers simu;

	public Evenement(long date, SimulationRobotsPompiers simu) {
		this.id = eventCtr;
		this.date = date;
		this.simu = simu;
		eventCtr++;
	}

	public long getDate() {
		return date;
	}

	public long getId() {
		return this.id;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public abstract void execute();

	@Override
	public int compareTo(Evenement event) {
		long date = this.getDate(), date2, id = this.getId(), id2;

		if (event == null)
			throw new NullPointerException();

		/*
		 * si deux evenements representent la meme instance alors ils sont egaux
		 */
		id2 = event.getId();
		if (id2 == this.getId())
			return 0;

		/* comparaison des dates */
		date2 = event.getDate();
		if (date < date2)
			return -1;
		if (date > date2)
			return 1;
		/*
		 * comparaison des numeros d'instance : celui instancie en premier est
		 * le plus petit
		 */
		if (id < id2)
			return -1;
		return 1;
	}

	@Override
	public String toString() {
		return new String("(" + date + ")");
	}
}
