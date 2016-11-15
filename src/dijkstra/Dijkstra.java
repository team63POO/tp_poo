package dijkstra;

import carte.Direction;
import physique.Temps;

import java.util.LinkedList;
import carte.Carte;
import carte.CaseCarte;
import carte.Chemin;
import robots.Robot;

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
				
			}
		}
	}

	public Chemin plusCourtChemin(CaseCarte destination) {
		Chemin chemin;
		CaseCarteDijkstra caseCourante = (CaseCarteDijkstra) carte.getCase(destination.getLigne(),
				destination.getColonne());
		long poids = caseCourante.getPoids();
		if (poids >= Temps.tempsInfini)
			throw new UnsupportedOperationException("Case inaccessible : pas de plus court chemin");
		LinkedList<Direction> directions = new LinkedList<Direction>();

		while (caseCourante != carte.getCase(robot.getLigne(), robot.getColonne())) {
			Direction dir = Direction.getOppose(caseCourante.getPrec());
			directions.add(dir);
			caseCourante = (CaseCarteDijkstra) carte.getVoisin(caseCourante.getLigne(), caseCourante.getColonne(), dir);
		}

		chemin = (new Chemin(directions, poids)).inverse();

		return chemin;
	}

	private void majVoisins(CaseCarteDijkstra caseCarte) {
		int lig = caseCarte.getLigne(), col = caseCarte.getColonne();
		for (Direction dir : Direction.values()) {
			if (carte.existeVoisin(lig, col, dir)) {
				CaseCarteDijkstra voisin = (CaseCarteDijkstra) carte.getVoisin(lig, col, dir);
				if (!(voisin.isTraitee())) {
					long poids = robot.tempsDeplacement(caseCarte, voisin, carte.getTailleCases());
					if ((poids + caseCarte.getPoids()) < voisin.getPoids()) {
						voisin.setPoids(caseCarte.getPoids() + poids);
						voisin.setPrec(dir);
					}
				}
			}
		}
	}
}
