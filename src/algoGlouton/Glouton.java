package algoGlouton;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import main.CordesHelper;
import main.GestionCordes;
import main.GestionDistances;
import main.RechercheTriangulationInterface;

import org.javatuples.Triplet;

public class Glouton extends Polygon implements RechercheTriangulationInterface {

	/**
	 * Required for serialization support.
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = -3680160591998285927L;
	private GestionDistances distances;
	private GestionCordes cordes;
	/** vecteur de triplets (sommet1, sommet2, distance) de cordes valides (sommet1, sommet2) avec leur distance **/
	private ArrayList<Triplet<Integer, Integer, Double>> C;

	
 
	private double distanceSol;
	private ArrayList<Triplet<Integer, Integer, Double>> sol;
	private int nbAppels = 0;
	

	/**
	 * Constructeur
	 * @param xpoints
	 * @param ypoints
	 * @param npoints
	 */
	public Glouton(int[] xpoints, int[] ypoints, int npoints) {
		super(xpoints, ypoints, npoints);	

		if(npoints < 4) {
			System.out.println("Erreur  : le polygone est trop petit");
		}

		distances = new GestionDistances(xpoints, ypoints, npoints);
		cordes = new GestionCordes(npoints);
		initC();

		//algorithme glouton
		glouton();


	}


	
	/// getters and setters ///
	
	@Override
	public ArrayList<Triplet<Integer, Integer, Double>> getSolution() {
		return sol;
	}

	@Override
	public double getDistanceSolution() {
		return distanceSol;
	}

	@Override
	public int getNbAppels() {
		return nbAppels;
	}

	/// fin getters and setters ///
	

	/**
	 * gÃ©nÃ©ration du vecteur de cordes C
	 */
	private void initC() {
		C = new ArrayList<Triplet<Integer,Integer,Double>>(cordes.getNbCordes());
		for (int i= 0 ; i < cordes.getNbCordes() ; i++ ) {
			int s0 = cordes.getCordesValides().get(i).getValue0();
			int s1 = cordes.getCordesValides().get(i).getValue1();
			double d = distances.getDistance(s0, s1);
			Triplet<Integer, Integer, Double> e = new Triplet<Integer, Integer, Double>(s0, s1, d);
			C.add(e);
		}
	}


	@Override
	public void rechercheTriangulation() {
		glouton();
		
		// calcul de la distance finale
		distanceSol = 0;
		for (Triplet<Integer, Integer, Double> triplet : sol) {
			distanceSol = distanceSol + triplet.getValue2();
		}
	}
	
	
	/** Algorithme glouton de Triangulation**/
	public void glouton() {
		// copie locale
		ArrayList<Triplet<Integer, Integer, Double>> cGlouton = new ArrayList<Triplet<Integer, Integer, Double>>(C);
		sol = new ArrayList<Triplet<Integer, Integer, Double>>(cordes.getNbSommets()-3);
		int nbCordesTrouvee = 0;

		

		//tri de la copie locale par distance dÃ©croissante
		Collections.sort(cGlouton, new CordeComparator());

		/// extraction des n-3 premieres cordes qui ne se coupent pas ///
		//tant qu'on a pas ajoute n-3 cordes :
		while(nbCordesTrouvee < cordes.getNbSommets()-3){
			nbAppels++;
			// rechercher dans cGlouton la corde avec la plus petite distance
			Triplet<Integer, Integer, Double> nouvelleCorde = cGlouton.get(0);
			cGlouton.remove(0);
			if (cGlouton.isEmpty()) {
				System.out.println("Erreur : plus de cordes valides alors que la recherche n'est pas finie");
			}
			//verifier qu'elle ne coupe pas les cordes existantes
			if (CordesHelper.cordeValide(nouvelleCorde, sol, cordes.getNbSommets())) {
				// si la nouvelle corde est valide, on l'ajoute au resultat
				sol.add(nouvelleCorde);
				nbCordesTrouvee++;
			}
		}
	}




}//~Polygone

