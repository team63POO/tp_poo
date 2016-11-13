package robots;

import carte.CaseCarte;
import carte.NatureTerrain;

public class RobotRoues extends Robot {
	/** vitesse de déplacement par défaut du robot */
	private final static int vitesseBase = 80;
	/** temps nécessaire au remplissage complet du réservoir en minutes */
	private final static long tempsRemplissage = 10;
	/** capacité de stockage en litres du robot */
	private final static long capaciteReservoir = 5000;
	/** débit d'extinction de l'incendie en L/s */
	private final static float debitArrosage = 100 / 5;
	/** niveau du réservoir en L */
	private long niveauReservoir;

	public RobotRoues(int lig, int col) {
		super(lig, col, vitesseBase);
		this.setReservoirPlein();
	}

	public RobotRoues(int lig, int col, int vitesse) {
		super(lig, col, vitesse);
		this.setReservoirPlein();
	}

	public long getNiveauReservoir() {
		return this.niveauReservoir;
	}

	private void setNiveauReservoir(long niveauReservoir) {
		if (niveauReservoir > capaciteReservoir || niveauReservoir < 0)
			throw new IllegalArgumentException(
					"Le niveau du réservoir ne peut ni être négatif ni dépasser la capacité du robot");
		this.niveauReservoir = niveauReservoir;
	}

	@Override
	public int getVitesseTerrain(NatureTerrain terrain) {
		if ((terrain == NatureTerrain.TERRAIN_LIBRE) || (terrain == NatureTerrain.HABITAT))
			return this.getVitesse();
		return 0;
	}

	@Override
	public long getTempsRemplissage() {
		return tempsRemplissage;
	}

	@Override
	public float getDebitArrosage() {
		return debitArrosage;
	}

	@Override
	public long getTempsArrosage() {
		return (long) (this.getNiveauReservoir()/this.getDebitArrosage());
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
}
