package algoGlouton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GloutonHelper {

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
			//System.out.println("erreur : numero de sommet inexistant");
			return -1;
		}

		// calcul de la distance
		if(posS1 <= posS2 ){
			distance = posS2-posS1;
		}
		else {
			distance = (tabPoints.length -posS1 + posS2);
		}

		//System.out.println("distance tabPoints[" + posS1 +"] = " + tabPoints[posS1] + ", tabPoints[" + posS2 +"] = " + tabPoints[posS2] + " : " + distance) ;
		return distance;
	}


	/**
	 * conversion entre une List et int[]
	 * @param integers		l'objet List
	 * @return
	 */
	public static int[] convertIntegers(ArrayList<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
}
