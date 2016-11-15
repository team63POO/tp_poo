package robots;

import carte.CaseCarte;
import carte.NatureTerrain;

/**
 * Classe robot a chenilles
 *
 */
public class RobotChenilles extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private final static int vitesseBase = 60;
	/** temps nécessaire au remplissage complet du réservoir en minutes */
	private final static long tempsRemplissage = 5;
	/** capacité de stockage en litres du robot */
	private final static long capaciteReservoir = 2000;
	/** débit d'extinction de l'incendie en L/s */
	private final static float debitArrosage = 100 / 8;
	/** niveau du réservoir en L */
	private long niveauReservoir;
	/** chemin du fichier image representant le robot */
	private final static String fichierImage = "resources/imagesrobots//robot_chenilles.png";

	public RobotChenilles(int lig, int col) {
		super(lig, col, vitesseBase);
		this.setReservoirPlein();
	}

	public RobotChenilles(int lig, int col, int vitesse) {
		super(lig, col, vitesse);
		this.setReservoirPlein();
	}

	public long getNiveauReservoir() {
		return niveauReservoir;
	}

	private void setNiveauReservoir(long niveauReservoir) {
		if (niveauReservoir > capaciteReservoir || niveauReservoir < 0)
			throw new IllegalArgumentException(
					"Le niveau du réservoir ne peut ni être négatif ni dépasser la capacité du robot");
		this.niveauReservoir = niveauReservoir;
	}

	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		switch (terrain) {
		case EAU:
			return 0;
		case ROCHE:
			return 0;
		case FORET:
			return this.getVitesse() / 2;
		default:
			return this.getVitesse();
		}
	}

	@Override
	public long getTempsRemplissage() {
		return tempsRemplissage;
	}

	@Override
	public boolean isReservoirPlein() {
		if (this.getNiveauReservoir() == RobotChenilles.capaciteReservoir)
			return true;
		return false;
	}

	@Override
	public float getDebitArrosage() {
		return debitArrosage;
	}

	@Override
	public long getTempsArrosage() {
		return (long) (this.getNiveauReservoir() / this.getDebitArrosage());
	}

	@Override
	public void decrementeNiveauReservoir(int volumeDeverse) {
		this.setNiveauReservoir(this.getNiveauReservoir() - volumeDeverse);
	}

	@Override
	public void setReservoirPlein() {
		this.setNiveauReservoir(capaciteReservoir);
	}

	@Override
	public void setReservoirVide() {
		this.setNiveauReservoir(0);
	}

	@Override
	public boolean conditionRemplissage(CaseCarte caseCarte) {
		return caseCarte.isBerge();
	}

	@Override
	public String getFichierImage() {
		return RobotChenilles.fichierImage;
	}

	@Override
	public String toString() {
		return new String(super.toString() + ", type = robot à chenilles");
	}
}
