package jeuxDeTest;

import progDynamique.Dynamique;
import algoGlouton.Glouton;
import essaisSuccessifs.EssaisSuccessifs;
import main.coordonneesHelper;


/** classe qui va lire des fichiers de jeux de test,
 *  - lancer les différents algorithmes avec ces jeux
 *  - comparer leurs resultats (ex. glouton, dynamique ?)
 *  - comparer le nombre d'appels recursifs
 *  - comparer les temps d'execution et sortir des graphes
 *  **/

/**
 * Lit un fichier de test, effectue une triangulation
 * avec un des trois algorithmes
 * et renvoie les mesures
 */
public class ExecuterJeuDeTest {


	public ExecuterJeuDeTest() {

	}

	/**
	 *  effectue une triangulation sur tous les polygones d'un fichier donné 
	 *  en utilisant les trois algorithmes, et renvoie des mesures 
	 * @param cheminFichier		le chemin du fichier contenant les polygones
	 * @param algoChoisi 
	 * @return					le temps moyen d'execution et le nombre moyen d'appels
	 */
	public static double[] executerJeu(String cheminFichier, boolean exES, boolean exESE, boolean exDY, boolean exGL) {

		long start = 0;
		long end = 0;
		long tpsExec = 0 ;


		long essaisSuccessifsTpsMoyenAvecElagage = 0;
		long essaisSuccessifsTpsMoyenSansElagage = 0;
		long dynamiqueTpsMoyen = 0;
		long gloutonTpsMoyen = 0;

		long essaisSusccessifsNbAppelMoyenSansElagage = 0;		
		long essaisSusccessifsNbAppelMoyenAvecElagage = 0 ;
		long dynamiqueNbAppelMoyen = 0;
		long gloutonNbAppelMoyen = 0;

		int gloutonNbResultatsSubOptimaux = 0;
		double plusGrandeDifference = 0;
		double gloutonDiffResMoyen = 0 ;
		double resMoyenExact = 0;
		int numPolyPlusGrandeDifference = 0;
		
		// lancer tous les algos 
		// renvoyer leurs resultats
		EssaisSuccessifs es = null;
		Dynamique dy = null;
		Glouton gl = null;


		//execution sur un fichier de test complet
		int numPoly = 1;
		LireJeuTest nouveauJeu = new LireJeuTest(cheminFichier);
		for(int i=0 ; i<(nouveauJeu.getTailleJeu()) ; i++ ) {
			int[][] polygon = nouveauJeu.getNextPolygon();
			int[] X = polygon[0];
			int[] Y = polygon[1];


			System.out.println("polynome " + numPoly );

			System.out.print("\t coordonnees X : ");
			coordonneesHelper.afficheCoord(X);
			System.out.println();

			System.out.print("\t coordonnees Y : ");
			coordonneesHelper.afficheCoord(Y);
			System.out.println("\n");


			numPoly++;


			//// Essais successifs sans elagage  /////
			if(exES){
				System.out.println("Algorithme d'essais successifs sans elegage");
				es = new EssaisSuccessifs(X, Y, Math.min(X.length, Y.length));
				es.avecElagage = false;

				////    partie interessante        ////
				// pas d'entrees/sorties (ex. affichage, lecture fichier...) ici afin de ne pas biaiser le test
				start = System.currentTimeMillis();
				es.rechercheTriangulation();
				end = System.currentTimeMillis();
				////                              ////

				tpsExec = end - start;
				System.out.println("\t tps d'execution : " + tpsExec + " ms, " + es.getNbAppels() + " appels");
				System.out.println("\t ES : " + es.getDistanceSolution());
				essaisSuccessifsTpsMoyenSansElagage += tpsExec;
				essaisSusccessifsNbAppelMoyenSansElagage += es.getNbAppels(); // pourrait etre lu une seule fois, car ne depend que du nombre de sommets
			}



			//// Essais successifs avec elagage  /////
			if(exESE) {
				System.out.println("Algorithme d'essais successifs avec elagage");
				es = new EssaisSuccessifs(X, Y, Math.min(X.length, Y.length));
				es.avecElagage = true;

				////    partie interessante        ////
				// pas d'entrees/sorties (ex. affichage, lecture fichier...) ici afin de ne pas biaiser le test
				start = System.currentTimeMillis();
				es.rechercheTriangulation();
				end = System.currentTimeMillis();
				////                              ////

				tpsExec = end - start;
				System.out.println("\t tps d'execution : " + tpsExec + " ms, " + es.getNbAppels() + " appels");
				System.out.println("\t ES : " + es.getDistanceSolution());
				essaisSuccessifsTpsMoyenAvecElagage += tpsExec;
				essaisSusccessifsNbAppelMoyenAvecElagage += es.getNbAppels();
			}



			//// Programmation dynamique  /////
			if(exDY){
				System.out.println("Algorithme par programmation dynamique");
				dy = new Dynamique(X, Y, Math.min(X.length, Y.length));


				////    partie interessante        ////
				// pas d'entrees/sorties (ex. affichage, lecture fichier...) ici afin de ne pas biaiser le test
				
				start = System.nanoTime();
				dy.rechercheTriangulation();
				end = System.nanoTime();
				////                              ////

				tpsExec = (end - start);
				System.out.println("\t tps d'execution : " + tpsExec + " ms, " + dy.getNbAppels() + " appels");
				System.out.println("\t DY : " + dy.getDistanceSolution());
				dynamiqueTpsMoyen += tpsExec;
				dynamiqueNbAppelMoyen += dy.getNbAppels();

				// si les resultats sont differents (a 5 decimales près, alors c'est une erreur
				if(exES || exESE) {
					if( ((double) Math.round(dy.getDistanceSolution() * 10000) / 10000) != ((double) Math.round(es.getDistanceSolution() * 10000) / 10000)) {
						try {
							throw new Exception("Erreur : resultat different entre l'algo dynamique et les essais successifs !");
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(0);
						}
					}
				}
			}


			//// Glouton  /////
			if(exGL){
				System.out.println("Algorithme glouton");
				gl = new Glouton(X, Y, Math.min(X.length, Y.length));

				////    partie interessante        ////
				// pas d'entrees/sorties (ex. affichage, lecture fichier...) ici afin de ne pas biaiser le test
				start = System.nanoTime();
				gl.rechercheTriangulation();
				end = System.nanoTime();
				////                              ////

				tpsExec = (end - start);
				System.out.println("\t tps d'execution : " + tpsExec + " ns, " + gl.getNbAppels() + " appels");
				System.out.println("\t GL : " + gl.getDistanceSolution());
				gloutonTpsMoyen += tpsExec;
				gloutonNbAppelMoyen += gl.getNbAppels();

				
				// comparaison de resultat avec un algorithme exact si possible
				if(exES || exESE) {
					if(gl.getDistanceSolution() > es.getDistanceSolution()) {
						gloutonNbResultatsSubOptimaux ++;
						double diff = gl.getDistanceSolution() - es.getDistanceSolution();
						if(diff > plusGrandeDifference) {
							plusGrandeDifference = diff;
							numPolyPlusGrandeDifference = numPoly;
						}
						gloutonDiffResMoyen = gloutonDiffResMoyen + diff;
					}
				}
				else if(exDY) {
					if(gl.getDistanceSolution() > dy.getDistanceSolution()) {
						gloutonNbResultatsSubOptimaux ++;
						double diff = gl.getDistanceSolution() - dy.getDistanceSolution();
						if(diff > plusGrandeDifference) {
							plusGrandeDifference = diff;
							numPolyPlusGrandeDifference = numPoly;
						}
						gloutonDiffResMoyen = gloutonDiffResMoyen + diff;
					}
				}

			}
		}


		// calcul des moyennes

		// ES avec elagage
		essaisSuccessifsTpsMoyenAvecElagage = (essaisSuccessifsTpsMoyenAvecElagage / (numPoly-1));	
		essaisSusccessifsNbAppelMoyenAvecElagage = (essaisSusccessifsNbAppelMoyenAvecElagage / (numPoly-1));	

		// ES sans elagage
		essaisSuccessifsTpsMoyenSansElagage = (essaisSuccessifsTpsMoyenSansElagage / (numPoly-1));	
		essaisSusccessifsNbAppelMoyenSansElagage = (essaisSusccessifsNbAppelMoyenSansElagage / (numPoly-1));	

		// dynamique
		dynamiqueTpsMoyen = (dynamiqueTpsMoyen / (numPoly-1));	
		dynamiqueNbAppelMoyen = (dynamiqueNbAppelMoyen / (numPoly-1));

		// Glouton
		gloutonTpsMoyen = (gloutonTpsMoyen / (numPoly-1));	
		gloutonNbAppelMoyen = (gloutonNbAppelMoyen / (numPoly-1));		
		gloutonDiffResMoyen = (gloutonDiffResMoyen  / (numPoly-1));	



		return new double[] {essaisSuccessifsTpsMoyenAvecElagage, (int) essaisSusccessifsNbAppelMoyenAvecElagage,
				essaisSuccessifsTpsMoyenSansElagage, (int) essaisSusccessifsNbAppelMoyenSansElagage,
				dynamiqueTpsMoyen,  (int)dynamiqueNbAppelMoyen ,
				gloutonTpsMoyen, (int) gloutonNbAppelMoyen, (int) gloutonNbResultatsSubOptimaux,  gloutonDiffResMoyen, plusGrandeDifference
		};
	}







}
