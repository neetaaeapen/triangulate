package main;

import java.util.ArrayList;

import org.javatuples.Triplet;

/**
 * Les algorithmes de recherche d'un triangulation doivent implementer cette interface
 */
public interface RechercheTriangulationInterface {
	
	/**
	 * Permet de demander l'application de conditions d'elegage si possible
	 */
	public boolean avecElagage = false;
	
	/**
	 * Lance la recherche en elle-même
	 * Ceci permet de mesurer son temps d'execution
	 */
	public void rechercheTriangulation();
	
	/**
	 * Renvoie l'ensemble de cordes solutions
	 * @return			l'ensemble de cordes sous forme d'un triplet (sommet1, sommet2, distance)
	 */
	public ArrayList<Triplet<Integer, Integer, Double>> getSolution();
	
	/**
	 * retourne la distance de la solution trouvée
	 * @return			la distance
	 */
	public double getDistanceSolution();
	
	
	/**
	 * Retourne le nombre d'appels qui ont ete necessaires pour trouver la solution
	 * @return		le nombre d'appels
	 */
	public int getNbAppels();

}
