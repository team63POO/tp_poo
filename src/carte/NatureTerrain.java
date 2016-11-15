package carte;

import java.awt.Color;

/**
 * Type enum implementant les natures de terrain disponibles et leurs couleurs
 * pour l'affichage graphique
 */
public enum NatureTerrain {
	TERRAIN_LIBRE(Color.decode("#ffeead")), FORET(Color.decode("#028900")), ROCHE(Color.decode("#696969")), EAU(
			Color.decode("#63ace5")), HABITAT(Color.decode("#645188"));

	private Color couleur;

	private NatureTerrain(Color couleur) {
		this.couleur = couleur;
	}

	public Color getCouleur() {
		return couleur;
	}
}
