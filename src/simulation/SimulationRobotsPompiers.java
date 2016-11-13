package simulation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.zip.DataFormatException;

import carte.Incendie;
import evenement.Evenement;
import gui.GUISimulator;
import gui.Oval;
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
	/** La couleur de dessin */
	private Color defaultColor = Color.BLACK;
	/** Données de la simulation */
	public DonneesSimulation donSimu;
	/** Date actualisée par le simulateur */
	private long dateSimulation;
	/** Liste ordonnée des évènements */
	private SortedSet<Evenement> events = new TreeSet<Evenement>();

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
		gui.reset(); // clear the window
		gui.addGraphicalElement(new Text(120, 7, defaultColor, "carte : " + fichierCarte));
		gui.addGraphicalElement(new Text(120, 22, defaultColor, "date : " + dateSimulation));

		for (int lig = 0; lig < donSimu.carte.getNbLignes(); lig++) {
			for (int col = 0; col < donSimu.carte.getNbColonnes(); col++) {
				gui.addGraphicalElement(new Rectangle(col * 30 + 50, lig * 30 + 50, defaultColor,
						donSimu.carte.getCase(lig, col).getNature().getCouleur(), 30, 30));
			}
		}

		for (Incendie incendie : donSimu.incendies) {
			if (incendie.getIntensite() != 0)
				gui.addGraphicalElement(new Oval(incendie.getColonne() * 30 + 50, incendie.getLigne() * 30 + 50,
						Color.RED, Color.RED, 18, 18));
		}

		for (Robot robot : donSimu.robots) {
			gui.addGraphicalElement(new Rectangle(robot.getColonne() * 30 + 50, robot.getLigne() * 30 + 50, Color.BLACK,
					Color.BLACK, 7, 7));
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
		gui.addGraphicalElement(new Text(5, 5, defaultColor, nomErreur));
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
