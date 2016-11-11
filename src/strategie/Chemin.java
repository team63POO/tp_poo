package strategie;

import java.util.Iterator;
import java.util.LinkedList;

import carte.Direction;

public class Chemin {
	public Direction[] directions;
	private long poids;

	public Chemin(LinkedList<Direction> listeDirections, long poids) {
		this.poids = poids;
		int sizeDirections = listeDirections.size();
		directions = new Direction[sizeDirections];
		Iterator<Direction> itrDir = listeDirections.iterator();
		int i = 0;
		while (itrDir.hasNext()) {
			directions[(sizeDirections - 1) - i] = itrDir.next();
			i++;
		}
	}
	
	public long getPoids() {
		return this.poids;
	}

	@Override
	public String toString() {
		String s = new String("Chemin :");
		for (int i = 0; i < directions.length; i++)
			s += " " + directions[i].toString();
		return s;
	}
}
