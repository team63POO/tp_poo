package robots;

import carte.NatureTerrain;

public class Drone extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private static int vitesseBase=100;
	/** temps nécessaire au remplissage complet du réservoir en minutes */
	private static int tempsRemplissage=30;
	/** capacité de stockage en litres du robot */
	private static int capaciteReservoir=10000;
	/** temps nécessaire au vidage du réservoir en s */
	private static int tempsExtinction=30;

	public Drone(int ligne, int colonne) {
		super(ligne, colonne, vitesseBase);
	}

	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		// vitesse constante sur tout terrain
		return this.getVitesse();
	}
} 
