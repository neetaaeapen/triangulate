package jeuxDeTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EnregistrerResultats {

	
	/**
	 * Ajoute une chaine donnee a la fin d'un fichier. Le fichier peut ne pas exister, auquel cas il sera créé.
	 * @param nomFichier			le nom complet (absolu ou relatif) du fichier
	 * @param stringToWrite			la chaine a ecrire
	 */
	public static void ajoutResultat(String nomFichier, String stringToWrite ) {
		try {
			FileWriter fw = new FileWriter(nomFichier, true);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(stringToWrite.toString());		// un resultat par ligne
			out.close();
			//System.out.println("Ecriture dans " + nomFichier + " : ");
			//System.out.println(stringToWrite.toString());
		} catch (IOException e) {
			System.out.println("Erreur : impossible d'ouvrir le fichier '" + nomFichier +"' en ecriture ");
			e.printStackTrace();
		}
	}
	
}
