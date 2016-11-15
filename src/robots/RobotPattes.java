package robots;

import carte.CaseCarte;
import carte.NatureTerrain;
import physique.Temps;

/**
 * Classe robot a pattes
 *
 */
public class RobotPattes extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private final static int vitesseBase = 30;
	/** débit d'extinction de l'incendie en L/s */
	private final static long debitArrosage = 10;
	/** chemin du fichier image representant le robot */
	private final static String fichierImage = "resources/images/robots/robot_pattes.png";

	public RobotPattes(int lig, int col) {
		super(lig, col, vitesseBase);
	}

	public RobotPattes(int lig, int col, int vitesse) {
		super(lig, col, vitesse);
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

	@Override
	public long getTempsRemplissage() {
		throw new UnsupportedOperationException("Un robot à pattes ne peut pas se remplir");
	}

	@Override
	public float getDebitArrosage() {
		return debitArrosage;
	}

	@Override
	public long getTempsArrosage() {
		return Temps.tempsInfini;
	}

	@Override
	public boolean isReservoirPlein() {
		return true;
	}

	@Override
	public void decrementeNiveauReservoir(int volumeDeverse) {
	}

	@Override
	public void setReservoirPlein() {
		throw new UnsupportedOperationException("Un robot à pattes ne peut pas se remplir");
	}

	@Override
	public void setReservoirVide() {
		throw new UnsupportedOperationException("Un robot à pattes ne peut pas se remplir");
	}

	@Override
	public boolean conditionRemplissage(CaseCarte caseCarte) {
		return caseCarte.isBerge();
	}

	@Override
	public String getFichierImage() {
		return RobotPattes.fichierImage;
	}

	@Override
	public String toString() {
		return new String(super.toString() + ", type = robot à pattes");
	}
}
