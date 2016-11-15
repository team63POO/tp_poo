package simulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.zip.DataFormatException;

import carte.Incendie;
import evenement.Evenement;
import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;
import io.LecteurDonnees;
import robots.Robot;
import strategie.Strategie;

/**
 * Classe simulation qui implemente le gestionnaire d'evenements discrets
 *
 */
public class SimulationRobotsPompiers implements Simulable {
	/** Chemin du fichier de carte */
	private String fichierCarte;
	/** L'interface graphique associée */
	private GUISimulator gui;
	/** Donnees de la simulation */
	public DonneesSimulation donSimu;
	/** Strategie */
	public Strategie strat;
	/** Date actualisee par le simulateur */
	private long dateSimulation;
	/** Liste ordonnee des évènements */
	private SortedSet<Evenement> events = new TreeSet<Evenement>();

	/** La couleur du texte */
	private Color couleurTexte = Color.BLACK;
	/** La couleur de la grille */
	private Color couleurGrille = Color.BLACK;
	/** Facteur d'echelle des robots */
	private final static double echelleRobot = 0.8;
	/** Position de la carte */
	private final static int ligPosCarte = 80, colPosCarte = 50;
	/**
	 * Taille des cases dans la fenetre graphique, a modifier si la carte est
	 * grande et depasse de la fenetre
	 */
	private final static int tailleCase = 20;

	public SimulationRobotsPompiers(GUISimulator gui, String fichierCarte) {
		this.fichierCarte = fichierCarte;
		this.gui = gui;
		this.strat = null;
		gui.setSimulable(this); // association a la gui

		this.lireFichierDonnees(); // lecture des donnees de la simulation dans
									// le fichier
		this.donSimu.carte.decouverteBerges(); // marquage des cases qui sont
												// des berges
		dateSimulation = 0;
		draw();
	}

	public SimulationRobotsPompiers(GUISimulator gui, String fichierCarte, Strategie strat) {
		this(gui, fichierCarte);
		this.strat = strat;
		strat.init(this);
		strat.donnerOrdresRobots();
	}

	@Override
	public void restart() {
		this.lireFichierDonnees();
		this.donSimu.carte.decouverteBerges(); // marquage des cases qui sont
												// des berges
		dateSimulation = 0;
		draw();
		strat.init(this);
		strat.donnerOrdresRobots();
	}

	private void draw() {
		int nbColonnes = donSimu.carte.getNbColonnes(), nbLignes = donSimu.carte.getNbLignes();

		gui.reset(); // nettoyage de la fenetre
		gui.addGraphicalElement(
				new Text((nbColonnes / 2 * tailleCase + colPosCarte), 10, couleurTexte, "carte : " + fichierCarte));
		gui.addGraphicalElement(
				new Text((nbColonnes / 2 * tailleCase + colPosCarte), 30, couleurTexte, "date : " + dateSimulation));

		// affiichage de la carte
		for (int lig = 0; lig < nbLignes; lig++) {
			for (int col = 0; col < nbColonnes; col++) {
				Color couleurCase = donSimu.carte.getCase(lig, col).getNature().getCouleur();
				gui.addGraphicalElement(new Rectangle(col * tailleCase + colPosCarte, lig * tailleCase + ligPosCarte,
						couleurGrille, couleurCase, tailleCase, tailleCase));
			}
		}

		// affichage des incendies d'intensite non nulle
		for (Incendie incendie : donSimu.incendies) {
			if (incendie.getIntensite() != 0)
				gui.addGraphicalElement(
						new ImageElement((int) ((incendie.getColonne() - 0.5) * tailleCase + colPosCarte),
								(int) ((incendie.getLigne() - 0.5) * tailleCase + ligPosCarte),
								"resources/images/flamme.png", tailleCase, tailleCase, gui));
		}

		// affichage des robots
		for (Robot robot : donSimu.robots) {
			gui.addGraphicalElement(new ImageElement(
					(int) ((robot.getColonne() - echelleRobot / 2) * tailleCase + colPosCarte),
					(int) ((robot.getLigne() - echelleRobot / 2) * tailleCase + ligPosCarte), robot.getFichierImage(),
					(int) (tailleCase * echelleRobot), (int) (tailleCase * echelleRobot), gui));
		}
	}

	public void ajouteEvenement(Evenement event) {
		events.add(event);
	}

	public void supprimeEvenement(Evenement event) {
		if (!this.events.remove(event))
			throw new IllegalArgumentException("erreur suppression evenement");
	}

	/**
	 * incremente la date de la simulation actuelle vers celle de l'evenement le
	 * plus proche
	 */
	private void incrementeDate() {
		if (this.events.size() == 0)
			throw new UnsupportedOperationException("Plus d'évènements");
		this.dateSimulation = this.events.first().getDate();
	}

	private boolean simulationTerminee() {
		if (this.events.isEmpty() || this.events.first().getDate() > this.dateSimulation)
			return true;
		return false;
	}

	private void lireFichierDonnees() {
		try {
			donSimu = LecteurDonnees.creerDonnees(fichierCarte);
		} catch (FileNotFoundException e) {
			this.afficheErreur("Fichier de carte introuvable");
		} catch (DataFormatException e) {
			this.afficheErreur("Fichier de carte non valide");
		}
	}

	private void afficheErreur(String nomErreur) {
		gui.reset();
		gui.addGraphicalElement(new Text(5, 5, Color.RED, nomErreur));
	}

	/**
	 * 
	 * @return date actuelle de la simulation
	 */
	public long getDateSimulation() {
		return dateSimulation;
	}

	@Override
	public void next() {
		Evenement event;
		System.out.println("next...events : " + events);
		try {
			this.incrementeDate();
		} catch (UnsupportedOperationException e) {

		}
		while (!this.simulationTerminee()) {
			event = this.events.first();
			event.execute();
			draw();
		}
	}
}
