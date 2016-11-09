package strategie;

import java.util.Iterator;
import java.util.LinkedList;

import carte.Direction;

public class Chemin {
	Direction[] directions;

	public Chemin(LinkedList<Direction> listeDirections) {
		directions = new Direction[listeDirections.size()];
		Iterator<Direction> itrDir=listeDirections.iterator();
		int i=0;
		while (itrDir.hasNext()) {
			directions[i]=itrDir.next();
			i++;
		}
	}	
	
	@Override
	public String toString() {
		String s = new String("Chemin :");
		for (int i=0; i<directions.length; i++)
			s+=" " + directions[i].toString();
		return s;
	}
}
