package algoGlouton;

import java.util.Comparator;
import org.javatuples.Triplet;

/** permet de comparer des triplets (sommet1, sommet2, distance) en vue de les trier par distance décroissante **/
public class CordeComparator implements Comparator<Triplet<Integer, Integer, Double>>{

	    @Override
	    /** compare les distances de deux triplets **/
	    public int compare(Triplet<Integer, Integer, Double> t1, Triplet<Integer, Integer, Double> t2) {
	    	return t1.getValue2().compareTo(t2.getValue2());
	    }

}
