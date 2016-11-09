package robots;

import carte.NatureTerrain;

public class RobotPattes extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private static int vitesseBase=30;
	/** débit d'extinction de l'incendie en L/s */
	private static int debitExtinction=10;


	public RobotPattes(int ligne, int colonne) {
		super(ligne, colonne, vitesseBase);
	}


	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		switch (terrain) {
        case EAU:
        	return 0;
        case ROCHE:
        	return 10;
        default: 
        	return this.getVitesse();
        }
	}
}
