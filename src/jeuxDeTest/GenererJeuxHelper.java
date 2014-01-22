package jeuxDeTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;



public class GenererJeuxHelper {

	/**
	 * Constructeur d'une classe static - rien a initialiser
	 */
	public GenererJeuxHelper() {

		// Génère un jeu de test dans un fichier

		// note : les coordonnees devraient etre largement supérieures au le nombre de sommets afin de ne pas finir avec un carré, ce qui pourrait faire boucler l'algorithme a l'infini
		// generer un jeu de tests peut donc etre tres long !
		// en pratique, nous avons constaté que des valeurs entre 3 et 5 fois le nombre de sommets sont un bon compromis

		// Exemple d'appel qui génère un jeu de 10 polygones à 5 sommets
		// chaque sommet ayant ses coordonnées x et y comprises entre 0 et 20 
		// le résultat étant stocké dans le fichier test10_5_20
		// GenererUnjeu(10, 5, 20, "JeuxDeTest/test10_5_20");

		//		int nbPolygones = 1;
		//		int nbSommets = 20;
		//		int coordMax = 500;
		//		
		//		if(coordMax < 2*nbSommets) {
		//			System.out.println("Warning : les coordonnees maximum ne sont peut etre pas adaptees");
		//		}
		//
		//		GenererUnjeu(nbPolygones, nbSommets, coordMax, "JeuxDeTest/test" + nbPolygones + "_" + nbSommets + "_" + coordMax, false);
		//		
		//		int nbSommets;
		//		int nbPolygones = 100;
		//		int coordMax = 1000;
		//		for(int i=20; i<50 ; i++) {
		//			System.out.println("GENERATION " + i + " SOMMETS");
		//			nbSommets = i;
		//			GenererUnjeu(nbPolygones, nbSommets, coordMax, "JeuxDeTest/test" + nbPolygones + "_" + nbSommets + "_" + coordMax, false);
		//		}
		//  
		//		
		// pour completer un jeu...		
		//		int nbPolygones = 1;
		//		int nbSommets = 20;
		//		int coordMax = 500;
		//		
		//		if(coordMax < 2*nbSommets) {
		//			System.out.println("Warning : les coordonnees maximum ne sont peut etre pas adaptees");
		//		}
		//
		//		GenererUnjeu(nbPolygones, nbSommets, coordMax, "JeuxDeTest/test" + nbSommets , true);

	}


	/**
	 * générer un jeu de polygones concaves dans un fichier
	 * le format est tel que le polygone de l'enonce est ecrit comme suit :
	 * 0 0 8 15 27 22 10
	 * 10 20 26 26 21 12 0
	 * Soit un s1 = (0, 10), s2 = (0, 20), s3 = (8, 26)...
	 * @param nbPolygones			le nombre de polygones a generer
	 * @param nbSommets				le nombre de sommets de chaque polygone
	 * @param coordMax				les coordonnées maximum de chaque sommet (x,y) compris dans [0,CoordMax]²
	 * @param nomFichier			le nom du fichier dans lequel on doit écrire (le dossier doit déjà exister)
	 * @param append 				indique si les resultats sont mis a la suite dans le fichier
	 */
	public static void GenererUnjeu(int nbPolygones, int nbSommets, int coordMax, String nomFichier, boolean append){

		StringBuffer stringToWrite = new StringBuffer();

		if(coordMax < 3*nbSommets) {
			System.out.println("Warning : les coordonnees maximum ne sont peut etre pas adaptees");
		}



		System.out.println("Debut de la génération...");
		// generation des polygones a ecrire
		for(int i=0 ; i<nbPolygones ; i++) {
			Geometry g = GenererUnPolygone(nbSommets, coordMax);
			System.out.println("Generation du polygone a " + nbSommets + " sommets - no : " + (i+1));
			Coordinate[] coord = g.getCoordinates();

			// affichage et ecriture pour tests

			// contenu du fichier :
			// un polygone est sur 2 lignes, la ligne des X en premier puis la ligne des Y
			// les polygones sont ecris a la suite
			// les coordonnees sont separees par des espaces
			// ex. le polygone de l'enonce est ecrit comme suit :
			// 0 0 8 15 27 22 10
			// 10 20 26 26 21 12 0

			// ecriture des X du polygone en cours
			for (int j=0; j< coord.length-1 ;j ++) {
				stringToWrite.append((int)Math.round(coord[j].x) + " ");
			}	
			stringToWrite.append("\n");

			// ecriture des Y du polygone en cours
			for (int j=0; j< coord.length-1 ;j ++) {
				stringToWrite.append((int)Math.round(coord[j].y) + " ");
			}	
			stringToWrite.append("\n");

		}

		ecrirePolygone(nomFichier, stringToWrite, append );
	}





	/**
	 * Ecrit une chaine donnee dans un fichier. Le fichier peut ne pas exister, auquel cas il sera créé.
	 * Un fichier du même nom existant sera écrasé. L'arborescence éventuelle doit exister.
	 * @param nomFichier			le nom complet (absolu ou relatif) du fichier
	 * @param stringToWrite			la chaine a ecrire
	 * @param append                indique si l'ecriture dans le fichier se fait a la suite 
	 */
	public static void ecrirePolygone(String nomFichier, StringBuffer stringToWrite, boolean append ) {
		try {
			FileWriter fw = new FileWriter(nomFichier, append);
			BufferedWriter out = new BufferedWriter(fw);
			out.write(stringToWrite.toString());
			out.close();
			System.out.println("Ecriture dans " + nomFichier + " : ");
			System.out.println(stringToWrite.toString());
		} catch (IOException e) {
			System.out.println("Erreur : impossible d'ouvrir le fichier '" + nomFichier +"' en ecriture ");
			e.printStackTrace();
		}
	}


	/**
	 * Renvoie vrai si la geometrie a les bonnes propriétés : 
	 * @param g					la geometrie associee a un polygone
	 * @param nbSommets			le nombre de sommets voulus
	 * @return					vrai si la geometrie est simple et a le bon nombre de sommets
	 */
	public static boolean GeometrieValide(Geometry g, int nbSommets) {

		if(g == null) {
			return false;
		}

		if(! g.isSimple()) {
			return false;
		}

		if(g.getCoordinates().length != nbSommets) {
			return false;
		}

		return true;
	}





	/**
	 * Genere un polygone concave
	 * @param nbSommetsPolygone			le nombre de sommets du polygone
	 * @param coordMax					les coordonnées maximum des sommets du polygone
	 * @return							le polygone genere
	 */
	public static Geometry GenererUnPolygone(int nbSommetsPolygone, int coordMax) {

		int nbPoints = nbSommetsPolygone+1;


		int nbEssais = 0;
		final int nbEssaisMax = 500*nbSommetsPolygone;

		// génère le carré qui représente l'espace des coordonnées valides

		Coordinate[] carreCoord = new Coordinate[] {
				new Coordinate(0.0, 0.0),
				new Coordinate(0.0, coordMax),
				new Coordinate(coordMax, coordMax),
				new Coordinate(coordMax, 0.0),
				new Coordinate(0.0, 0.0)	// linear ring
		};



		GeometryFactory carreFact = new GeometryFactory();
		LinearRing carreLinear = carreFact.createLinearRing(carreCoord);
		Polygon carreP = new Polygon(carreLinear, null, carreFact);
		Geometry gCarre = carreP.convexHull();

		if(!gCarre.isRectangle()){
			System.out.println("Erreur : la geometrie generee n'est pas un rectangle");
			return null;
		}
		//		else{
		//			
		//			System.out.println("Coordonees carre :");
		//			for (Coordinate coordinate : gCarre.getCoordinates()) {
		//				System.out.println(coordinate.toString());
		//			}
		//			
		//		}

		// genere nbPoints sommets aleatoires
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(nbPoints);
		Random randomGenerator = new SecureRandom();		// aléatoire cryptographique, utilise des sources d'entropie => plus lent que Random, mais meilleure qualité d'aléatoire

		final int moyenne = coordMax/2 ;
		final int deviation = coordMax / 5 ;
		for(int i=0; i < nbPoints ; i++ ) {
			ajoutNbAleatoire(coordMax, coordinates, randomGenerator, moyenne,
					deviation);
		}


		// recherche d'un polygone valide
		Geometry g = null;
		Coordinate[] coordTab = null;
		do{

			coordTab = (Coordinate[]) coordinates.toArray(new Coordinate[coordinates.size()]);
			coordTab[coordinates.size()-1] = coordTab[0];	// linear ring


			//			// affichage
			//			for(int i=0; i < coordTab.length ; i++ ) {
			//				 System.out.println(i + " : " +coordTab[i]);
			//			}

			// trouve et renvoie le plus petit polygone convexe contenant les points
			// (Hull Convex) => il se peut qu'il y ait donc moins de points que désiré
			GeometryFactory fact = new GeometryFactory();
			LinearRing linear = fact.createLinearRing(coordTab);
			Polygon poly = new Polygon(linear, null, fact);

			g = poly.convexHull();

			// si le nombre de points est inférieur au résultat souhaité,
			// on ajoute un point aléatoire jusqu'à avoir un polygone ayant les bonnes propriétés


			// si le polygone est un rectangle,
			// c'est probablement que les nombres aléatoires ont rempli tous l'espace des coordonnées autorisées
			// on relance alors l'algorithme en espérant avoir plus de succès
			// de même si on a un nombre important d'essais
			nbEssais++;
			//System.out.println("nb essais : " + nbEssais + "sur " + nbEssaisMax);
			if(g.isRectangle()){
				if(g.equals(gCarre)){		// si le rectangle est maximal, il ne sert à rien de générer d'autres points
					System.out.println("rectangle maximal trouvé, nouvel appel");
					return GenererUnPolygone(nbSommetsPolygone, coordMax);
				}
				// sinon c'est juste une coincidence, un rectangle entoure nos points
				System.out.println("rectangle non maximal trouvé");

			}
			if((nbEssais>nbEssaisMax)){
				System.out.println(nbEssaisMax + " essais sur ce polygone, nouvel appel");
				return GenererUnPolygone(nbSommetsPolygone, coordMax);
			}


			// ajoute une nouvelle coordonnee lorsqu'il faut rechercher un polygone en utilisant plus de points
			//randomGenerator = new Random();		// nouveau generateur au cas ou le precedent ne permet pas de fournir les valeurs pour faire un rectangle
			//c = new Coordinate( randomGenerator.nextInt(coordMax),  randomGenerator.nextInt(coordMax));
			//			System.out.println("nouvelle coordonnee : " + c);
			//coordinates.add(c);





			//ajoutNbAleatoire(coordMax, coordinates, randomGenerator, moyenne,
			//		deviation);

			ajoutPointExterieurAuPolygone(coordMax, coordinates,
					randomGenerator, moyenne, deviation, fact, poly);
		}while(!GeometrieValide(g , nbPoints));


		//		System.out.println("Polygone valide : ");
		//		// affichage
		//		for(int i=0; i < coordTab.length ; i++ ) {
		//			 System.out.println(i + " : " +coordTab[i]);
		//		}


		return g;
	}


	/**
	 * Ajoute a coordinates un nouveau point en dehors du polygone
	 * @param coordMax
	 * @param coordinates
	 * @param randomGenerator
	 * @param moyenne
	 * @param deviation
	 * @param fact
	 * @param poly
	 */
	public static void ajoutPointExterieurAuPolygone(int coordMax,
			ArrayList<Coordinate> coordinates, Random randomGenerator,
			final int moyenne, final int deviation, GeometryFactory fact,
			Polygon poly) {
		// ajoute une nouvelle coordonnee lorsqu'il faut rechercher un polygone en utilisant plus de points
		Coordinate c ;
		int val1;
		int val2;
		Point p;

		do{
			// utilisation d'une gaussienne
			// on doit donc vérifier les valeurs extremes
			do {
				val1 =(int) Math.round((randomGenerator.nextGaussian() * deviation) + moyenne);
			} while((val1 < 0) || (val1 > coordMax));

			do {
				val2 =(int) Math.round(randomGenerator.nextGaussian() * deviation + moyenne);
			} while((val2 < 0) || (val2 > coordMax));

			c = new Coordinate( val1, val2);
			p = fact.createPoint(c);
			// ajout d'un point en dehors du polygone existant, afin d'eviter de recalculer le convex hull inutilement
			
		}while(poly.contains(p));
		//c = new Coordinate( randomGenerator.nnextInt(coordMax),  randomGenerator.nextInt(coordMax));
		//System.out.println("nouvelle coordonnee : " + c);
		coordinates.add(c);
	}


	/**
	 * Génère une coordonnée valide aléatoire
	 * @param coordMax
	 * @param coordinates
	 * @param randomGenerator
	 * @param moyenne
	 * @param deviation
	 */
	public static void ajoutNbAleatoire(int coordMax,
			ArrayList<Coordinate> coordinates, Random randomGenerator,
			int moyenne, int deviation) {
		Coordinate c;
		int val1;
		int val2;
		// utilisation d'une gaussienne
		// on doit donc vérifier les valeurs extremes
		do {
			val1 =(int) Math.round((randomGenerator.nextGaussian() * deviation) + moyenne);
		} while((val1 < 0) || (val1 > coordMax));

		do {
			val2 =(int) Math.round(randomGenerator.nextGaussian() * deviation + moyenne);
		} while((val2 < 0) || (val2 > coordMax));

		c = new Coordinate( val1, val2);
		//c = new Coordinate( randomGenerator.nnextInt(coordMax),  randomGenerator.nextInt(coordMax));
		//System.out.println("nouvelle coordonnee : " + c);
		coordinates.add(c);
	}


}
