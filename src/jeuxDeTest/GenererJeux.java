package jeuxDeTest;
public class GenererJeux {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		long start= System.currentTimeMillis(); 
		// Génère un jeu de test dans un fichier

		// note : les coordonnees devraient etre largement supérieures au le nombre de sommets afin de ne pas finir avec un carré,
		// ce qui pourrait faire boucler l'algorithme a l'infini
		// generer un jeu de tests peut donc etre tres long !
		// en pratique, nous avons constaté que des valeurs entre 3 et 5 fois le nombre de sommets sont un bon compromis
		// Cependant les polygones ayant une taille superieure a 20 sont longs a generer, et ceux ayant un e taille superieure a 25 sont tres longs


		// Exemple d'appel qui génère un jeu de 10 polygones à 5 sommets
		// chaque sommet ayant ses coordonnées x et y comprises entre 0 et 20 
		// le résultat étant stocké dans le fichier test10_5_20
		// GenererUnjeu(10, 5, 20, "JeuxDeTest/test10_5_20");


		int nbSommets = 10;
		int nbPolygones = 10;
		int coordMax = 1000;

		if(coordMax < 2*nbSommets) {
			System.out.println("Warning : les coordonnees maximum ne sont peut etre pas adaptees");
		}

		GenererJeuxHelper.GenererUnjeu(nbPolygones, nbSommets, coordMax, "JeuxDeTest/test", false);




		//		int nbSommets;
		//		int nbPolygones = 100;
		//		int coordMax = 1000;
		//		for(int i=26; i<100 ; i++) {
		//			System.out.println("GENERATION " + i + " SOMMETS");
		//			nbSommets = i;
		//			GenererJeuxHelper.GenererUnjeu(nbPolygones, nbSommets, coordMax,
		//					"JeuxDeTest/testGaussienne/" +  nbSommets + "_" + nbPolygones + "_" + coordMax, false);
		//		}



		// pour completer un jeu...		
		//		int nbPolygones = 1;
		//		int nbSommets = 20;
		//		int coordMax = 500;
		//		
		//		if(coordMax < 2*nbSommets) {
		//			System.out.println("Warning : les coordonnees maximum ne sont peut etre pas adaptees");
		//		}
		//
		//		GenererJeuxHelper.GenererUnjeu(nbPolygones, nbSommets, coordMax, "JeuxDeTest/test" + nbSommets , true);

		long time= System.currentTimeMillis();

		System.out.println("temps d'execution : " + (time - start) + "ms"); 
		System.out.println("temps d'execution : " + ((time - start)/1000) + "s"); 
		System.out.println("temps d'execution : " + (((time - start)/1000)/3600) + "s"); 		
	}

}
