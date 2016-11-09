package robots;

import carte.NatureTerrain;

public class RobotRoues extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private static int vitesseBase=80;
	/** temps nécessaire au remplissage complet du réservoir en minutes */
	private static int tempsRemplissage=10;
	/** capacité de stockage en litres du robot */
	private static int capaciteReservoir=5000;
	/** débit d'extinction de l'incendie en L/s */
	private static float debitExtinction=100/5;

	public RobotRoues(int ligne, int colonne) {
		super(ligne, colonne, vitesseBase);
	}

	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		if ((terrain==NatureTerrain.TERRAIN_LIBRE) || (terrain==NatureTerrain.HABITAT))
			return this.getVitesse();
		return 0;
	}
}
