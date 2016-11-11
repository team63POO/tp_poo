package dijkstra;

import carte.Direction;
import physique.Temps;

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
			try {
				CaseCarteDijkstra caseCarte = carte.minDijkstra();
				caseCarte.setTraitee(true);
				this.majVoisins(caseCarte);
			} catch (UnsupportedOperationException e) {
				continue;
			}
		}
	}

	public Chemin plusCourtChemin(CaseCarte destination) {
		Chemin chemin;
		long poids = ((CaseCarteDijkstra) carte.getCase(destination.getLigne(), destination.getColonne())).getPoids();
		LinkedList<Direction> directions = new LinkedList<Direction>();
		CaseCarteDijkstra caseCourante = (CaseCarteDijkstra) carte.getCase(destination.getLigne(),
				destination.getColonne());

		while (caseCourante != carte.getCase(robot.getLigne(), robot.getColonne())) {
			System.out.println("caseCourante : " + caseCourante);
			Direction dir = caseCourante.getPrec();
			directions.add(dir);
			caseCourante = (CaseCarteDijkstra) carte.getVoisin(caseCourante.getLigne(), caseCourante.getColonne(),
					Direction.getOppose(dir));
		}

		chemin = new Chemin(directions, poids);

		return chemin;
	}

	private void majVoisins(CaseCarteDijkstra caseCarte) {
		for (Direction dir : Direction.values()) {
			try {
				CaseCarteDijkstra voisin = (CaseCarteDijkstra) carte.getVoisin(caseCarte.getLigne(),
						caseCarte.getColonne(), dir);
				if (!(voisin.isTraitee())) {
					long poids = robot.tempsDeplacement(caseCarte, voisin, carte.getTailleCases());
					if (poids != Temps.tempsInfini) {
						voisin.setPoids(caseCarte.getPoids() + poids);
						voisin.setPrec(dir);
					}
				}
			} catch (UnsupportedOperationException e) {

			}
		}
	}
}
