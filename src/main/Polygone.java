package main;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import algoGlouton.CordeComparator;

public class Polygone extends Polygon{

	private GestionDistances distances;
	private GestionCordes cordes;
	/** vecteur de triplets (sommet1, sommet2, distance) de cordes valides (sommet1, sommet2) avec leur distance **/
	private ArrayList<Triplet<Integer, Integer, Double>> C;


	/// essais successifs ///
	private double distanceSol = Double.POSITIVE_INFINITY;		// initialise au max afin de trouver une meilleure valeur
	private double distanceSolCourante = 0;	
	private ArrayList<Triplet<Integer, Integer, Double>> sol;
	private ArrayList<Triplet<Integer, Integer, Double>> solCourante;
	private int nbAppels = 0;

	// constructeur
	public Polygone(int[] xpoints, int[] ypoints, int npoints) {
		super(xpoints, ypoints, npoints);	

		if(npoints < 4) {
			System.out.println("Erreur  : le polygone est trop petit");
		}

		distances = new GestionDistances(xpoints, ypoints, npoints);
		cordes = new GestionCordes(npoints);
		initC();




		// algorithme ES


		sol = new ArrayList<Triplet<Integer,Integer,Double>>(npoints-3);
		solCourante = new ArrayList<Triplet<Integer,Integer,Double>>(npoints-3);

		essaisSuccessifs(0);

		System.out.println("La meilleure solution trouvée : ");
		CordesHelper.afficheVecteur(sol);
		System.out.println("Distance totale : " + this.distanceSol);
		System.out.println("Nombre d'appels recursifs : " + nbAppels);


	

		//algorithme glouton
		//glouton();


	}


	/**
	 * génération du vecteur de cordes C
	 */
	private void initC() {
		C = new ArrayList<Triplet<Integer,Integer,Double>>(cordes.getNbCordes());
		for (int i= 0 ; i < cordes.nbCordes ; i++ ) {
			int s0 = cordes.getCordesValides().get(i).getValue0();
			int s1 = cordes.getCordesValides().get(i).getValue1();
			double d = distances.getDistance(s0, s1);
			Triplet<Integer, Integer, Double> e = new Triplet<Integer, Integer, Double>(s0, s1, d);
			C.add(e);
		}
	}




	public void essaisSuccessifs(int etape) {
		// strategie d'essai a trous

		System.out.println("\nEtape : " + etape);
		nbAppels++;
		
		//A l'étape i, on met ou pas la i-ème corde de Si
		// parcours de C (Si)
		for (Triplet<Integer, Integer, Double> xi : C) {
			if(satisfaisant(xi)) {
				enregistrer(xi);
				
				if (solTrouvee()) {
					System.out.println("Une Solution...");
					CordesHelper.afficheVecteur(solCourante);
					System.out.println("Distance totale : " + this.distanceSolCourante);
					if(meilleure()) {
						//copie integrale de la solution
						sol = new ArrayList<Triplet<Integer,Integer,Double>>(solCourante);
						majValOpt();
						System.out.println("Une meilleure solution trouvée : ");
						CordesHelper.afficheVecteur(sol);
						System.out.println("Distance totale : " + this.distanceSol);
					}
				}
				//else if(encorePossible(etape)) {		// a commenter pour supprimer la condition d'elagage
					essaisSuccessifs(etape + 1);
				//}
				
				defaire(xi);
			}
		}
	}


	private boolean meilleure() {
		// la solution est meilleure si sa distance est moindre que la solution en cours
		return (this.distanceSolCourante <= this.distanceSol);
	}


	private boolean satisfaisant(Triplet<Integer, Integer, Double> xi){
		// xi est satisfaisant si il ne coupe pas de cordes existantes et s'il n'a pas encore ete trace
		return CordesHelper.cordeValide(xi, this.solCourante, this.npoints);
	}



	private void defaire(Triplet<Integer, Integer, Double> xi) {
		// on retire la corde de la construction de la solution en cours
		solCourante.remove(xi);

		//met a jour la distance courante
		this.distanceSolCourante = distanceSolCourante - xi.getValue2();
	}


	private boolean encorePossible(int etape) {
		// l'arbre des solutions contient encore possiblement des feuilles valides
		// condition d'elagage : la distance en cours est plus grande que la distance de la solution courante
		return (this.distanceSolCourante <= this.distanceSol);

	}


	private void majValOpt() {
		// met a jour la valeur de distance actuelle
		// de la solution trouvee, qui a ete calculee au fur et a mesure
		this.distanceSol = this.distanceSolCourante;
	}


	private void enregistrer(Triplet<Integer, Integer, Double> xi) {
		// stocke la corde 
		solCourante.add(xi);
		//met a jour la distance courante
		this.distanceSolCourante = distanceSolCourante + xi.getValue2();
	}



	private boolean solTrouvee(){
		// une solution est trouvée si on a trouvé n-3 cordes
		return (solCourante.size() >= this.npoints- 3);
	}

	
	
	
	/** Algorithme glouton de Triangulation**/
	public void glouton() {
		// copie locale
		ArrayList<Triplet<Integer, Integer, Double>> cGlouton = new ArrayList<Triplet<Integer, Integer, Double>>(C);
		List<Triplet<Integer, Integer, Double>> res = new ArrayList<Triplet<Integer, Integer, Double>>(cordes.getNbSommets()-3);
		int nbCordesTrouvee = 0;

		//tri de la copie locale par distance décroissante
		Collections.sort(cGlouton, new CordeComparator());

		/// extraction des n-3 premieres cordes qui ne se coupent pas ///
		//tant qu'on a pas ajouté n-3 cordes :
		while(nbCordesTrouvee < cordes.getNbSommets()-3){
			// rechercher dans cGlouton la corde avec la plus petite distance
			Triplet<Integer, Integer, Double> nouvelleCorde = cGlouton.get(0);
			cGlouton.remove(0);
			if (cGlouton.isEmpty()) {
				System.out.println("Erreur : plus de cordes valides alors que la recherche n'est pas finie");
			}
			//verifier qu'elle ne coupe pas les cordes existantes
			if (CordesHelper.cordeValide(nouvelleCorde, res, cordes.getNbSommets())) {
				// si la nouvelle corde est valide, on l'ajoute au resultat
				res.add(nouvelleCorde);
				nbCordesTrouvee++;
			}

		}
		System.out.println("Resultat algorithme Glouton :");
		CordesHelper.afficheVecteur(res);
	}




}//~Polygone


