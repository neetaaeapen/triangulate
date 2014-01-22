package progDynamique;

public class DynamiqueHelper {


	/**
	 * calcule la distance entre deux sommets du polygone initial.
	 * Les sommets sont donc supposes numerotes de 0 à n
	 * 
	 * @param s1			le numero du premier sommet
	 * @param s2			le numero du deuxieme sommet
	 * @param nbSommets 	le nombre de sommets du polygone
	 * @return				la distance entre s1 et s2 ex. Une distance entre deux sommets consécutifs dans l'ordre retrograde est égale à 1.
	 */
	public static int distanceNumSommets(int s1, int s2, int nbSommets){
		int distance = 0;
		if(s1 <= s2 ){
			distance = s2-s1;
		}
		else {
			distance = (nbSommets -s1 + s2);
		}

		return distance;
	}


	
	/**
	 * calcule la distance entre deux sommets du polygone initial.
	 * Les sommets sont donc supposes numerotes de 0 à n
	 * 
	 * @param s1			le numero du premier sommet
	 * @param s2			le numero du deuxieme sommet
	 * @param nbSommets 	le nombre de sommets du polygone
	 * @return				la distance entre s1 et s2 ex. Une distance entre deux sommets consécutifs dans l'ordre retrograde est égale à -1.
	 */
	public static int distanceNumSommetsOrdreIndirect(int s1, int s2, int nbSommets){
		 
		return nbSommets - distanceNumSommets(s1, s2, nbSommets);
	}
	

	/**
	 * calcule la distance entre deux sommets. Aucune hypothese n'est faite quand a l'ordonnancement des numeros.
	 * ex. Une distance entre deux sommets consécutifs dans l'ordre retrograde est égale à 1.
	 * @param s1			le numero du premier sommet
	 * @param s2			le numero du deuxieme sommet
	 * @param tabPoints 	la tableau contenant les numéros des sommets
	 * @return				la distance entre s1 et s2
	 */
	public static int distanceNumSommets(int s1, int s2, int tabPoints[]){
		if(s1 == s2) {
			return 0;
		}

		int distance = 0;
		int posS1 = -1;
		int posS2 = -1;

		// recherche position de s1 et de s2
		for (int i=0 ; i< tabPoints.length ; i++) {
			if(tabPoints[i] == s1) {
				posS1 = i;
			}
			else if(tabPoints[i] == s2) {
				posS2 = i;
			}

			if(posS1 != -1 && posS2 != -1) {
				break;
			}
		}

		if(posS1 == -1 || posS2 == -1) {
			return -1;
		}

		// calcul de la distance
		if(posS1 <= posS2 ){
			distance = posS2-posS1;
		}
		else {
			distance = (tabPoints.length -posS1 + posS2);
		}
		return distance;
	}



	
	
	
	
	
	/**
	 * calcule la distance entre deux sommets.  
	 * ex. Une distance entre deux sommets consécutifs dans l'ordre retrograde est égale à 1.
	 * ! Pas de verifications sur les paramètres
	 * @param t1            le numero du premier sommet du sous-problème
	 * @param t2			le numero du dernier sommet du sous-problème
	 * @param s1			le numero du premier sommet dont on veut la distance au deuxième
	 * @param s2			le numero du deuxieme sommet (>s1)
	 * @return				la distance entre s1 et s2
	 */
	public static int distanceNumSommets(int t1, int t2, int s1, int s2){
		if(s1 == s2) {
			return 0;
		}
		int taille = t2-t1+1;
		int distance = 0;
		int posS1 = t1 + s1;
		int posS2 = t1 + s2;

		// calcul de la distance
		if(posS1 <= posS2 ){
			distance = posS2-posS1;
		}
		else {
			distance = (taille - posS1 + posS2);
		}

		return distance;
	}
	
	
	
	


	/**
	 * Permet de savoir si le sous-problème T(s1, s2) est un cas de base qui arrête la récursivité
	 * @param s1		le numéro du premier sommet du polygone définissant le sous-problème
	 * @param s2		le numéro du deuxième sommet du polygone définissant le sous-problème
	 * @param tabSommet 
	 * @return			vrai si le sous-problème ne nécessite pas de résolution, faux sinon
	 */
	public static boolean finParcours(int s1, int s2, int[] tabSommet){
		int d = distanceNumSommets(s1, s2, tabSommet);

		//System.out.println("distance (" +s1 + ", " + s2 + ") : " + d);
		// si il y a moins de 3 sommets, on a fini
		if(d < 3) {
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Permet de decaler l'affichage de la sortie standard
	 * @param nbAppels		le nombre de decalages a effectuer
	 */
	public static void decaler(int nbAppels) {
		for(int i=0 ; i<nbAppels ; i++){
			System.out.print("  ");
		}

	}

}
