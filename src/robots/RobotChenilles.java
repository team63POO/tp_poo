package robots;

import carte.NatureTerrain;

public class RobotChenilles extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private static int vitesseBase=60;
	/** temps nécessaire au remplissage complet du réservoir en minutes */
	private static int tempsRemplissage=5;
	/** capacité de stockage en litres du robot */
	private static int capaciteReservoir=2000;
	/** débit d'extinction de l'incendie en L/s */
	private static float debitExtinction=100/8;
	
	public RobotChenilles(int ligne, int colonne) {
		super(ligne, colonne, vitesseBase);
	}
	
	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		switch (terrain) {
        case EAU:
        	return 0;
        case ROCHE:
        	return 0;
        case FORET:
        	return this.getVitesse()/2;
        default: 
        	return this.getVitesse();
        }
	}
}
