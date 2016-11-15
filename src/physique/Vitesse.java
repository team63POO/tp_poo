package physique;

/**
 * Classe permettant de convertir et de stocker des constantes de vitesse
 */
public class Vitesse {
	/**
	 * Convertit une vitesse en km/h vers une vitesse en m/s
	 * 
	 * @param kmph
	 * @return vitesse en m/s
	 */
	public static double kmphVersMps(double kmph) {
		return kmph * (double) 1 / (double) 3.6;
	}
}
