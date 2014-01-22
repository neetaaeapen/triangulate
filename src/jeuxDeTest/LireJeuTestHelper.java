package jeuxDeTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  lit les jeux de test generes et renvoie des tableaux de coordonnees sous une forme compatible avec les algorithmes de recherche de triangulation
 *
 */
public class LireJeuTestHelper {


	/**
	 * Constructeur
	 */
	public LireJeuTestHelper() {
	}
	
	
	/**
	 * Lit un fichier de test et renvoie les coordonnees des polygones
	 * @param nomFichier
	 * @return
	 */
	public static ArrayList<int[]> lireFichierTest(String nomFichier) {
		ArrayList<String[]> tabCoordString = new ArrayList<String[]>();
		ArrayList<int[]> tabCoordInt = new ArrayList<int[]>();


		// lire le fichier
		try {
			BufferedReader in = new BufferedReader(new FileReader(nomFichier));
			String line = new String();
			while(((line = in.readLine())) != null) {
				tabCoordString.add(line.toString().split(" "));
			}

		} catch (FileNotFoundException e) {
			System.out.println("Erreur : fichier inexistant");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Erreur : erreur d'entree/sortie sur le buffer du fichier");
			e.printStackTrace();
		}



		// convertir String[] en int[]
		for (String[] ligne : tabCoordString) {
			int[] ligneInt = new int[ligne.length];
			for (int i = 0; i < ligne.length; i++) {
				try {
					ligneInt[i] = Integer.parseInt(ligne[i]);
				} catch (NumberFormatException e) {
					System.out.println("Erreur : conversion string --> entier echouee");
					e.printStackTrace();
				};
			}
			tabCoordInt.add(ligneInt);
		}

		return tabCoordInt;
	}
}
