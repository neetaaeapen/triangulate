package progDynamique;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import main.GestionCordes;
import main.GestionDistances;
import main.RechercheTriangulationInterface;

import org.javatuples.Pair;
import org.javatuples.Triplet;

public class Dynamique extends Polygon implements RechercheTriangulationInterface {

	private GestionDistances distances;
	//private GestionCordes cordes;


	private ArrayList<Triplet<Integer, Integer, Double>> sol = new ArrayList<Triplet<Integer,Integer,Double>>(this.npoints-3);
	private ArrayList<Triplet<Integer, Integer, Double>> solSpb ;
	private double distanceSol;
	private int nbAppels;

	// stockage en 2*n^2 soit O(n^2)
	/** le resultat de l'algorithme : les distances optimales des sous-problèmes (calculés une fois uniquement pour chaque sous-problème)**/
	double t[][] =  new double[npoints][npoints];	
	
	/** le resultat de l'algorithme : les k stockés permettant de retrouver les cordes tracées **/
	int s[][] =  new int[npoints][npoints];



	/**
	 * constructeur
	 * @param xpoints
	 * @param ypoints
	 * @param npoints
	 */
	public Dynamique(int[] xpoints, int[] ypoints, int npoints) {
		super(xpoints, ypoints, npoints);	

		if(npoints < 4) {
			System.out.println("Erreur  : le polygone est trop petit");
		}

		distances = new GestionDistances(xpoints, ypoints, npoints);
		//cordes = new GestionCordes(npoints);



	}

	/// getters and setters ///

	@Override
	public ArrayList<Triplet<Integer, Integer, Double>> getSolution(){
		return sol;
	}

	@Override
	public double getDistanceSolution(){
		return distanceSol;
	}

	@Override
	public int getNbAppels() {
		return nbAppels;
	}
	/// fin getters and setters ///


	@Override
	public void rechercheTriangulation() {
		// programmation dynamique
		triangulation_minimale();
		distanceSol = t[0][this.npoints-1];
	}


	/**
	 * Recherche une triangulation minimale par un algorithme dynamique
	 */
	private void triangulation_minimale () {

		int n = this.npoints;

		// strategie : on construit le tableau de solutions
		// en commencant par les plus petits sous-problèmes
		// ainsi les problemes plus gros peuvent utiliser les resultats precedents

		// Note : 
		// si on commence la resolution a partir de la taille 4
		// alors il faut initialiser a 0 toutes les solutions precedentes (ie de taille inferieure ou egale a 3)
		// car ces sous-problemes de petite taille ne necessitent pas de resolution
		// t initialise a 0 par defaut  (fait automatiquement en Java, doubles initialises a 0)
		
		for(int L=4 ; L<n+1 ; L++ ) {	
			for(int i=0 ; i<n-L+1  ; i++) {
				// parcours des sommets
				int j = i+L-1;
				t[i][j]= Double.POSITIVE_INFINITY;
				// parcours des triangles
				for(int k=i+1 ; k<j ; k++) {
					nbAppels ++;
					double q = 	t[i%n][k%n]+ t[k%n][j%n] + cout(i%n, j%n, k%n);
					if(q < t[i%n][j%n]) {
						t[i%n][j%n] = q;
						s[i%n][j%n] = k%n;
					}
				}
			}
		}
	}




	/**
	 * Fonction de cout d'un triangle (i, j, k)
	 * @param i		le numéro du premier sommet du triangle
	 * @param j		le numéro du deuxième sommet du triangle
	 * @param k		le numéro du troisième sommet du triangle
	 * @return	Cout(i, j, k) = Distance(i, j) + Distance(j, k) + Distance(k, i)
	 * 			Avec Distance(A,B) = longueur du segment reliant les
				sommets A et B s’ils ne sont pas adjacents sur le
				sous-problème T(i, j), 0 sinon
	 */
	private double cout(int i, int j, int k) {
		double cout = 0;
		int d;

		// cree le tableau de sommets du sous-probleme
		int[] tabP = new int[j-i+1];
		for(int z=0; z<tabP.length ; z++) {
			tabP[z] = i+z;
		}

		// si les cordes potentielles ne sont pas des côtés du sous-probleme considere T[i, j]
		// on ajoute leur distance
		d = DynamiqueHelper.distanceNumSommets(i, j, tabP);	
		if ( d > 1 && d < tabP.length -1){
			// TODO getDistance sans deja trace
			cout = cout + distances.getDistance(i, j);
		}

		d = DynamiqueHelper.distanceNumSommets(j, k, tabP);

		if ( d > 1 && d < tabP.length -1){
			cout = cout + distances.getDistance(j, k);		
		}

		d = DynamiqueHelper.distanceNumSommets(k, i, tabP);
		if ( d > 1 && d < tabP.length -1){
			cout = cout + distances.getDistance(k, i);
		}
		return cout;
	}




//	/**
//	 * Affiche le resultat de la triangulation minimale du sous-problème T(i, j)
//	 * TODO mettre au point
//	 * Il faut i >= j
//	 * @param i		le premier sommet du sous-problème
//	 * @param j		le deuxième sommet du sous-problème
//	 */
//	public void AfficheTriangulation (int i, int j) {
//		int n = this.npoints;
//		System.out.println("Solution par programmation dynamique au problème : T(" + i + ", " + j + ")");
//		System.out.println(": d=" + this.getDistanceSolution());
//		construireVectSol(i, j);
//		for (Triplet<Integer, Integer, Double> t : solSpb) {
//			System.out.println("\t"+  t);
//		}
//	}
//
//	/**
//	 *  construit le vecteur solution d'un sous-problème donné
//	 * @param i		le premier sommet du sous-problème
//	 * @param j		le deuxième sommet du sous-problème
//	 */
//	private void construireVectSol(int i, int j) {
//		int[] tabP = new int[j-i+1];
//		for(int z=0; z<tabP.length ; z++) {
//			tabP[z] = i+z;
//		}
//		int d = DynamiqueHelper.distanceNumSommets(i, j, tabP);
//		solSpb = new ArrayList<Triplet<Integer,Integer,Double>>(d+1);
//		rechercheCordes(i, j);
//	}
//
//	/**
//	 * Recherche et ajoute recursivement les cordes a solSpb solution d'un sous-problème donné T(i, j)
//	 * @param i			numéro du sommet du premier sous-problème
//	 * @param j			numéro du sommet du deuxième sous-problème
//	 */
//	private void rechercheCordes(int i, int j) {
//
//		int[] tabP = new int[j-i+1];
//		for(int z=0; z<tabP.length ; z++) {
//			tabP[z] = i+z;
//		}
//		int d = DynamiqueHelper.distanceNumSommets(i, j, tabP);
//
//		// recherche du k du sous-problème
//		int k = s[i][j];
//		// condition d'arret
//		if(k <= 0 || Double.isInfinite(k)) {
//			return;
//		}
//
//		d = DynamiqueHelper.distanceNumSommets(i, j, tabP);			
//		if ( d > 1 && d < tabP.length -1){
//			Triplet<Integer, Integer, Double> cordeSol = new Triplet<Integer, Integer, Double>(i, j, distances.getDistance(i, j));
//			solSpb.add(cordeSol);
//		}
//
//		d = DynamiqueHelper.distanceNumSommets(j, k, tabP);
//		if ( d > 1 && d < tabP.length -1){
//			Triplet<Integer, Integer, Double> cordeSol = new Triplet<Integer, Integer, Double>(j, k, distances.getDistance(j, k));
//			solSpb.add(cordeSol);
//		}
//
//		d = DynamiqueHelper.distanceNumSommets(k, i, tabP);
//		if ( d > 1 && d < tabP.length -1){
//			Triplet<Integer, Integer, Double> cordeSol = new Triplet<Integer, Integer, Double>(k, i, distances.getDistance(k, i));
//			solSpb.add(cordeSol);
//		}
//
//		int n = this.npoints;
//		// spb 1
//		rechercheCordes(i%n, k%n);
//		// spb 2
//		rechercheCordes(k%n, j%n);
//	}
	

	/**
	 * Affiche la matrice carrée S
	 */
	public void affiches() {
		System.out.println("-----S-----");
		for (int i=0 ; i<s.length ; i++) {
			for(int j=0 ; j<s.length ; j++) {
				System.out.print(+s[i][j] + "\t");
			}
			System.out.println();
		}
	}


	/**
	 * Affiche la matrice carrée t
	 */
	public void affichet() {
		System.out.println("-----t-----");
		for (int i=0 ; i<t.length ; i++) {
			for(int j=0 ; j<t.length ; j++) {
				if(t[i][j] == Double.POSITIVE_INFINITY) {
					System.out.print("\t Inf");
				}
				else{
					System.out.print("\t "+ (double) Math.round(t[i][j] * 10) / 10);
				}
			}
			System.out.println();
		}
	}

}//~Polygone


