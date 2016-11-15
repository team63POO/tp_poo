package carte;

import java.util.LinkedList;

/**
 * Implementation d'un chemin par un tableau de directions et un poids
 * correspondant à la duree de parcours du chemin
 */
public class Chemin {
	private Direction[] directions;
	private int nombreDirections;
	private long poids;

	public Chemin(LinkedList<Direction> listeDirections, long poids) {
		this.poids = poids;
		this.nombreDirections = listeDirections.size();
		directions = listeDirections.toArray(new Direction[this.getNombreDirections()]);
	}

	/**
	 * Retourne le chemin inverse
	 * 
	 * @return chemin inverse
	 */
	public Chemin inverse() {
		LinkedList<Direction> listeDirections = new LinkedList<Direction>();
		for (int i = this.getNombreDirections() - 1; i >= 0; i--)
			listeDirections.add(Direction.getOppose(directions[i]));
		return new Chemin(listeDirections, this.getPoids());
	}

	public int getNombreDirections() {
		return nombreDirections;
	}

	public long getPoids() {
		return this.poids;
	}

	public Direction getDirection(int i) {
		return directions[i];
	}

	@Override
	public String toString() {
		String s = new String("directions :");
		for (int i = 0; i < directions.length; i++)
			s += " " + directions[i].toString();
		s += " , poids = " + this.getPoids();
		return s;
	}
}
