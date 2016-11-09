package tests;

import io.LecteurDonnees;
import simulation.DonneesSimulation;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestLecteurDonnees {

    public static void main(String[] args) {
    	DonneesSimulation simu;
    	
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            simu=LecteurDonnees.creerDonnees(args[0]);

            System.out.println("Lecture terminée");
            System.out.println(simu);
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}

