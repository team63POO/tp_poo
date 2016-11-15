package carte;

/**
 * Type enum implementant les 4 points cardinaux
 */
public enum Direction {
	NORD, SUD, EST, OUEST;

	/**
	 * Methode qui retourne la direction opposee a dir
	 * @param dir
	 * @return direction opposee
	 */
	public static Direction getOppose(Direction dir) {
		switch (dir) {
		case NORD:
			return SUD;
		case SUD:
			return NORD;
		case EST:
			return OUEST;
		case OUEST:
			return EST;
		default:
			throw new IllegalArgumentException("Direction inconnue");
		}
	}
}
