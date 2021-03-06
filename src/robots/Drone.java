package robots;

import carte.CaseCarte;
import carte.NatureTerrain;

/**
 * Classe drone
 *
 */
public class Drone extends Robot {
	/** vitesse de déplacement par défaut du robot en km/h */
	private final static int vitesseBase = 100;
	/** temps nécessaire au remplissage complet du réservoir en minutes */
	private final static long tempsRemplissage = 30;
	/** capacité de stockage en litres du robot */
	private final static long capaciteReservoir = 10000;
	/** temps nécessaire au vidage du réservoir en s */
	private final static long tempsArrosage = 30;
	/** niveau du réservoir en L */
	private long niveauReservoir;
	/** chemin du fichier image representant le robot */
	private final static String fichierImage = "resources/images/robots/drone.png";

	public Drone(int lig, int col) {
		super(lig, col, vitesseBase);
		this.setReservoirPlein();
	}

	public Drone(int lig, int col, int vitesse) {
		super(lig, col, vitesse);
		this.setReservoirPlein();
	}

	public long getNiveauReservoir() {
		return niveauReservoir;
	}

	public void setNiveauReservoir(long niveauReservoir) {
		if (niveauReservoir > capaciteReservoir || niveauReservoir < 0)
			throw new IllegalArgumentException(
					"Le niveau du réservoir ne peut ni être négatif ni dépasser la capacité du robot");
		this.niveauReservoir = niveauReservoir;
	}

	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		// vitesse constante sur tout terrain
		return this.getVitesse();
	}

	@Override
	public long getTempsRemplissage() {
		return Drone.tempsRemplissage;
	}

	@Override
	public boolean isReservoirPlein() {
		if (this.getNiveauReservoir() == Drone.capaciteReservoir)
			return true;
		return false;
	}

	@Override
	public float getDebitArrosage() {
		return (float) capaciteReservoir / tempsArrosage;
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
		return (caseCarte.getNature() == NatureTerrain.EAU);
	}

	@Override
	public String getFichierImage() {
		return Drone.fichierImage;
	}

	@Override
	public String toString() {
		return new String(super.toString() + ", type = drone");
	}
}
