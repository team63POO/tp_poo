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

public class SimulationRobotsPompiers implements Simulable {
	/** Chemin du fichier de carte */
	private String fichierCarte;
	/** L'interface graphique associée */
	private GUISimulator gui;
	/** Données de la simulation */
	public DonneesSimulation donSimu;
	/** Date actualisée par le simulateur */
	private long dateSimulation;
	/** Liste ordonnée des évènements */
	private SortedSet<Evenement> events = new TreeSet<Evenement>();

	/** La couleur du texte */
	private Color couleurTexte = Color.BLACK;
	/** La couleur de la grille */
	private Color couleurGrille = Color.BLACK;
	/** Facteur d'échelle des robots */
	private final static double echelleRobot = 0.2;
	private final static int ligPosCarte = 80, colPosCarte = 50;
	private final static int tailleCase = 50;

	public SimulationRobotsPompiers(GUISimulator gui, String fichierCarte) {
		this.fichierCarte = fichierCarte;
		this.gui = gui;
		gui.setSimulable(this); // association a la gui
		this.lireFichierDonnees();
		this.donSimu.carte.decouverteBerges();
		dateSimulation = 0;
		draw();
	}

	private void draw() {
		int nbColonnes = donSimu.carte.getNbColonnes(), nbLignes = donSimu.carte.getNbLignes();

		gui.reset(); // clear the window
		gui.addGraphicalElement(
				new Text((nbColonnes / 2 * tailleCase + colPosCarte), 10, couleurTexte, "carte : " + fichierCarte));
		gui.addGraphicalElement(
				new Text((nbColonnes / 2 * tailleCase + colPosCarte), 30, couleurTexte, "date : " + dateSimulation));

		for (int lig = 0; lig < nbLignes; lig++) {
			for (int col = 0; col < nbColonnes; col++) {
				Color couleurCase = donSimu.carte.getCase(lig, col).getNature().getCouleur();
				gui.addGraphicalElement(new Rectangle(col * tailleCase + colPosCarte, lig * tailleCase + ligPosCarte,
						couleurGrille, couleurCase, tailleCase, tailleCase));
			}
		}

		for (Incendie incendie : donSimu.incendies) {
			gui.addGraphicalElement(new ImageElement((int) ((incendie.getColonne() - 0.5) * tailleCase + colPosCarte),
					(int) ((incendie.getLigne() - 0.5) * tailleCase + ligPosCarte), "resources/flamme.png", tailleCase,
					tailleCase, gui));
		}

		for (Robot robot : donSimu.robots) {
			gui.addGraphicalElement(new Rectangle(robot.getColonne() * tailleCase + colPosCarte,
					robot.getLigne() * tailleCase + ligPosCarte, Color.BLACK, Color.BLACK, (int) (tailleCase * echelleRobot), (int) (tailleCase * echelleRobot)));
		}
	}

	public void ajouteEvenement(Evenement event) {
		events.add(event);
	}

	public void supprimeEvenement(Evenement event) {
		if (!this.events.remove(event))
			throw new IllegalArgumentException("erreur suppression evenement");
	}

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

	@Override
	public void restart() {
		this.lireFichierDonnees();
		dateSimulation = 0;
		draw();
	}
}
