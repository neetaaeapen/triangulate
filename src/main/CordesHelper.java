package main;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

import org.javatuples.Pair;
import org.javatuples.Triplet;

/** classe contenant des methodes d'extension relatives aux cordes **/
public class CordesHelper {

	/** renvoie une chaine de la forme (s1, s2) 
	 * @return 
	 *
	 **/
	public static String cordeString (int s1, int s2) {
		return "(" + s1 + ", " + s2 + ")";
	}

	
	
	/** vérifie si la corde nouvelleCorde est valide
	 *  Hypothese : les sommets sont numerotes de 0 à (nbSommets-1)
	 * 
	 * @param nouvelleCorde			corde dont on teste la validite
	 * @param cordesExistantes		les cordes deja tracées
	 * @param nbSommets				le nombre de sommets du polygone
	 * @return						vrai si la corde n'a pas encore été tracée et ne coupe aucune corde dájà tracée, faux sinon
	 */
	public static boolean cordeValide(Pair<Integer, Integer> nouvelleCorde,
			List<Pair<Integer, Integer>> cordesExistantes, int nbSommets) {

		if (cordesExistantes.contains(nouvelleCorde)) {
			// la corde n'est pas valide puisqu'elle a deja ete tracee
			return false;
		}

		// vérifications de la validité des paramètres
		if (valideCordeSimple(nouvelleCorde.getValue0(), nouvelleCorde.getValue1(), nbSommets) == false) {
			return false;
		}

		// recherche si cette nouvelle corde coupe une corde existante
		for (Pair<Integer, Integer> corde : cordesExistantes) {
			if( coupe (nouvelleCorde.getValue0(), nouvelleCorde.getValue1(), 
					corde.getValue0(), corde.getValue1(), nbSommets) == true) {
				// elle coupe une corde, et n'est pas valide
				return false;
			}
		}

		// arrive a ce point on sait que la nouvelle corde n'existe pas
		// et qu'elle ne coupe aucune corde existante
		return true;
		
	}
	
	
	/** vérifie si la corde nouvelleCorde est valide
	 * 
	 * @param nouvelleCorde			corde dont on teste la validite
	 * @param cordesExistantes		les cordes deja tracées
	 * @param nbSommets				le nombre de sommets du polygone
	 * @return						vrai si la corde n'a pas encore été tracée et ne coupe aucune corde dájà tracée, faux sinon
	 */
	public static boolean cordeValide(Triplet<Integer, Integer, Double> nouvelleCorde,
			List<Triplet<Integer, Integer, Double>> cordesExistantes, int nbSommets) {


		if (cordesExistantes.contains(nouvelleCorde)) {
			// la corde n'est pas valide puisqu'elle a deja ete tracee
			return false;
		}

		// vérifications de la validité des paramètres
		if (valideCordeSimple(nouvelleCorde.getValue0(), nouvelleCorde.getValue1(), nbSommets) == false) {
			return false;
		}

		// recherche si cette nouvelle corde coupe une corde existante
		for (Triplet<Integer, Integer, Double> corde : cordesExistantes) {
			if( coupe (nouvelleCorde.getValue0(), nouvelleCorde.getValue1(), 
					corde.getValue0(), corde.getValue1(), nbSommets) == true) {
				// elle coupe une corde, et n'est pas valide
				return false;
			}
		}

		// arrive a ce point on sait que la nouvelle corde n'existe pas
		// et qu'elle ne coupe aucune corde existante
		return true;

	}


	/** vérifie si les cordes (numSommetA1, numSommetA2) et (numSommetB1, numSommetB2) se coupent dans un polynôme concave
	 * 
	 * @param numSommetA1			premiere coordonnée de la corde A
	 * @param numSommetA2			deuxième coordonnée de la corde A
	 * @param numSommetB1			premiere coordonnée de la corde B
	 * @param numSommetB2			deuxième coordonnée de la corde B
	 * @param nbpoints				le nombre de sommets du polygône
	 * @return						vrai si les cordes se coupent, faux sinon
	 */
	public static boolean coupe (int numSommetA1, int numSommetA2, int numSommetB1, int numSommetB2, int nbSommets) {
		int sA1 = Math.min(numSommetA1, numSommetA2);
		int sA2 = Math.max(numSommetA1, numSommetA2);
		int sB1 = Math.min(numSommetB1, numSommetB2);
		int sB2 = Math.max(numSommetB1, numSommetB2);

		
		// verifications

		if (valideCordeSimple(sA1, sA2, nbSommets) == false) {
			return true;
		}
		if (valideCordeSimple(sB1, sB2, nbSommets) == false) {
			return true;
		}


		if ( (sA1 == sB1) && (sA2 == sB2) ){
			Assert.assertEquals(false , (sA1 == sB1) && (sA2 == sB2));
			System.out.println("les deux cordes sont les memes ! : (" + sA1 + ", " + sA2 + ") ;" + " (" + sB1 + "," + sB2 + ")");
			return true;
		}

		
		// recherche 
		if(sB1 == sA1) {
			//System.out.println("premier point de B sur A, les cordes ne se coupent pas");
			return false;
		}

		if(sB2 == sA2) {
			//System.out.println("deuxième point de B sur A, les cordes ne se coupent pas");
			return false;
		}

		boolean coteA, coteB = false;

		// memorise de quel cote est le point sB1
		if(  (sB1 > sA1 )   &&   (sB1 < sA2 )  ) {
			coteA = true;
		}
		else {
			coteA = false;
		}

		// memorise de quel cote est le point sB2
		if(  (sB2 > sA1 )   &&   (sB2 < sA2 )  ) {
			coteB = true;
		}
		else {
			coteB = false;
		}

		// si les deux points de (sB1, sB2) sont de part et d'autre des deux polygones délimités par (sA1, sA2) alors les cordes se coupent
		// sinon elles ne se coupent pas
		if(coteA == coteB) {
			return false;
		}
		else {
			return true;
		}

	}





	/** verifie la validite simple d'une corde
	 * Une corde est valide si ce n'est pas un point, un cote du polygone et qu'elle appartient au polygone
	 * **/
	public static boolean valideCordeSimple(int numSommetA1, int numSommetA2, int nbSommets) {

		int sA1 = Math.min(numSommetA1, numSommetA2);
		int sA2 = Math.max(numSommetA1, numSommetA2);

		if(sA1 == sA2) {
			//System.out.println("Erreur  : la corde (" + sA1 + "," + sA2 + ") est un point");
			return false;
		}

		boolean sA2Dernier = (sA2 == (nbSommets -1));
		if ( (sA1 == sA2  - 1) || (sA2Dernier &&  sA1 == 0 ) ){
			//System.out.println("Erreur  : la corde (" + sA1 + "," + sA2 + ") est un côté du polygone");
			return false;
		}

		if ( numSommetA1 < 0 ) {
			//System.out.println("sommet inexistant : " + numSommetA1);
			return false;
		}

		if ( numSommetA2 >= nbSommets ) {
			//System.out.println("sommet inexistant : " + numSommetA2);
			return false;
		}

		return true;
	}


//	/**
//	 * génération du vecteur de cordes C
//	 * @param cordes 
//	 * @param distances 
//	 * @return 
//	 */
//	public static ArrayList<Triplet<Integer, Integer, Double>> initC(GestionDistances distances, GestionCordes cordes) {
//
//		ArrayList<Triplet<Integer, Integer, Double>> C = new ArrayList<Triplet<Integer,Integer,Double>>(cordes.getNbCordes());
//		
//		for (int i= 0 ; i < cordes.nbCordes ; i++ ) {
//			int s0 = cordes.getCordesValides().get(i).getValue0();
//			int s1 = cordes.getCordesValides().get(i).getValue1();
//			double d = distances.getDistance(s0, s1);
//			Triplet<Integer, Integer, Double> e = new Triplet<Integer, Integer, Double>(s0, s1, d);
//			C.add(e);
//		}
//		return C;
//	}

	/**
	 * affiche le vecteur d'entree
	 * @param vect 		le vecteur a afficher
	 */
	public static void afficheVecteur(List<Triplet<Integer, Integer, Double>> vect) {
		for (Triplet<Integer, Integer, Double> i : vect) {
			System.out.println("corde : " + cordeString(i.getValue0(), i.getValue1()) + " distance : " + i.getValue2().toString());

		}
	}



	/**
	 * affiche le vecteur d'entree
	 * @param vect 		le vecteur a afficher
	 */
	public static void afficheVecteur(ArrayList<Triplet<Integer, Integer, Double>> vect) {
		for (Triplet<Integer, Integer, Double> i : vect) {
			System.out.println("corde : " + cordeString(i.getValue0(), i.getValue1()) + " distance : " + i.getValue2().toString());
		}
	}

}