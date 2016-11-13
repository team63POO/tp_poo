package tests;

import java.util.LinkedList;

import carte.Chemin;
import carte.Direction;

public class TestChemin {
	public static void main(String[] args) {
		LinkedList<Direction> directions = new LinkedList<Direction>();
		directions.add(Direction.NORD);
		directions.add(Direction.NORD);
		directions.add(Direction.EST);
		directions.add(Direction.EST);
		
		Chemin chemin = new Chemin(directions, 500);
		System.out.println("Chemin : " + chemin);
		System.out.println("Chemin inverse : " + chemin.inverse());
	}
}
