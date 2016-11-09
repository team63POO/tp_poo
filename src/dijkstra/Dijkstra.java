package dijkstra;

import carte.Direction;

import java.util.LinkedList;

import carte.Carte;
import carte.CaseCarte;
import robots.Robot;
import strategie.Chemin;

public class Dijkstra {
	public CarteDijkstra carte;
	private Robot robot;

	public Dijkstra(Carte carte, Robot robot) {
		this.carte = new CarteDijkstra(carte);
		this.robot = robot;
	}

	public void calculeDijkstra() {
		carte.init(robot.getLigne(), robot.getColonne());

		for (int i = 0; i < (carte.getNbColonnes() * carte.getNbLignes()); i++) {
			CaseCarteDijkstra caseCarte = carte.minDijkstra();
			caseCarte.setTraitee(true);
			this.majVoisins(caseCarte);
		}
	}

	public Chemin plusCourtChemin(CaseCarte destination) {
		Chemin chemin;
		LinkedList<Direction> directions = new LinkedList<Direction>();
		CaseCarteDijkstra caseCourante = (CaseCarteDijkstra) carte.getCase(destination.getLigne(),
				destination.getColonne());

		this.calculeDijkstra();

		while (caseCourante != carte.getCase(robot.getLigne(), robot.getColonne())) {
			Direction dir = caseCourante.getPrec();
			directions.add(dir);
			caseCourante = (CaseCarteDijkstra) carte.getVoisin(caseCourante.getLigne(), caseCourante.getColonne(), dir);
		}

		chemin = new Chemin(directions);

		return chemin;
	}

	private void majVoisins(CaseCarteDijkstra caseCarte) {
		for (Direction dir : Direction.values()) {
			try {
				CaseCarteDijkstra voisin = (CaseCarteDijkstra) carte.getVoisin(caseCarte.getLigne(),
						caseCarte.getColonne(), dir);
				if (!(voisin.isTraitee())) {
					long poids = robot.tempsDeplacement(caseCarte, voisin, carte.getTailleCases());
					voisin.setPoids(caseCarte.getPoids()+poids);
					voisin.setPrec(dir);
					
				}
			} catch (UnsupportedOperationException e) {

			}
		}
	}
}
