package evenement;

import simulation.SimulationRobotsPompiers;

public abstract class Evenement implements Comparable<Evenement> {
	private static long eventCtr=0;
	private long date, id;
	protected SimulationRobotsPompiers simu;
	
	
	public Evenement(long date, SimulationRobotsPompiers simu) {
		this.id = eventCtr;
		this.date = date;
		this.simu = simu;
		eventCtr++;
	}

	public long getDate () {
		return date;
	}
	
	public long getId () {
		return this.id;
	}
	
	public void setDate (long date) {
		this.date = date;
	}
	
	public abstract void execute();
	
    @Override
    public int compareTo(Evenement event) {
    	long date=this.getDate() ,date2, id=this.getId(), id2;
    	
    	if (event == null)
    		throw new NullPointerException();
    	
    	id2 = event.getId();
    	if (id2==this.getId())
    		return 0;
    	
    	date2 = event.getDate();
    	if (date<date2)
    		return -1;
    	if (date>date2)
    		return 1;
    	if (id<id2)
    		return -1;
    	return 1;
    }
        
    @Override
    public String toString () {
    	return new String("(" + date + ")");
    }
}
