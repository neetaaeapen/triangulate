package main;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import junit.framework.Assert;

public class GestionCordes {

	/** matrice supérieure droite des cordes **/
	private boolean TabCorde[][];

	/** nombre de sommets dans le polygone **/
	private int nbSommets;

	/** nombre de cordes qu'il est possible de tracer **/
	int nbCordes;

	/** vecteur de couples de cordes valides (a,b)
	 * par convention, le plus petit sommet est en premier (a < b)
	 * **/
	 private ArrayList<Pair<Integer, Integer>> cordesValides;

	/** constructeur 
	 * @param nbSommets			le nombre de sommets du polygone
	 **/
	public GestionCordes(int nbSommets) {
		this.setNbSommets(nbSommets);
		initTabCordes();
		initNbCordes();
		
		
	}

	/** initialise le nombre de cordes qu'il est possible de tracer ainsi que le vecteur c de coordonnees valides **/
	private void initNbCordes() {
		
		// nombre de cordes valides : n(n-3)/2
		int nbC = (this.getNbSommets()  * (this.getNbSommets() -3)) /2;
		cordesValides = new ArrayList<Pair<Integer,Integer>>(nbC);
		
		for (int i = 0; i < TabCorde.length; i++) {
			for (int j = 0; j < i; j++) {
				if(valideCorde(i, j)) {
					// convention : les plus petits sommets en premier
					//System.out.println("corde valide " + nbCordes + ": " + coord(Math.min(i, j), Math.max(i, j)));
					cordesValides.add(new Pair<Integer, Integer>(Math.min(i, j), Math.max(i, j)));
				}
			}
		}
		
		cordesValides.trimToSize();					//normalement sans effet
		this.nbCordes = cordesValides.size();		// pour vérifications lors des tests unitaires
		
		//initialise a false par defaut
		//http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html	
	}


	/** retournr le nombre de cordes qu'il est possible de tracer **/
	public int getNbCordes() {
		return nbCordes;
	}


	public int getNbSommets() {
		return nbSommets;
	}

	public void setNbSommets(int nbSommets) {
		this.nbSommets = nbSommets;
	}



	/**  initialisele tableau de cordes
	 *  les cases sont initialise a faux <=> corde inexistante
	 **/
	private void initTabCordes() {
		// stocké dans un tableau triangulaire pour gagner de la place
		TabCorde = new boolean[getNbSommets()][];
		for (int i = 0; i < TabCorde.length; i++) {
			TabCorde[i] = new boolean[i];
			for (int j = 0; j < i; j++) {
				TabCorde[i][j] = false; 
			}
		}
	}

	
	
	public static String coord (int s1, int s2) {
		return "(" + s1 + ", " + s2 + ")";
	}

	/**  permet de definir l'existence ou non de la corde (numSommet1, numSommet2)
	 *  @param numSommet1 		le numero du premier sommet
	 *  @param numSommet2 		le numero du deuxieme sommet
	 *  @param valeur 			la valeur a stocker (faux <=> corde inexistante)
	 **/
	public void setCorde(int numSommet1, int numSommet2, boolean valeur) throws Exception {

		int a = Math.min(numSommet1, numSommet2);
		int b = Math.max(numSommet1, numSommet2);
		
		// parametre incorrect
		if ( a < 0 ) {
			throw new Exception("case memoire inexistante : " + a);
		}

		if (b >= this.getNbSommets()) {
			throw new Exception("case memoire inexistante : " + b);
		}

		if((a == b) && ( valeur != false )) {
			throw new Exception("case " + a + " : la distance d'un point a lui-même doit être 0");
		}

		// stocke le résultat dans le tableau triangulaire
		// vérifier si la corde est déjà dans le même etat ?
		TabCorde[b][a] = valeur;
	}



	public ArrayList<Pair<Integer, Integer>> getCordesValides() {
		return cordesValides;
	}


	/**  permet de savoir si la corde (numSommet1, numSommet2) existe ou non
	 *  @param numSommet1 		le numero du premier sommet
	 *  @param numSommet2 		le numero du deuxieme sommet
	 *  @return 				vrai si la corde existe, faux sinon
	 * @throws Exception 
	 **/
	public boolean getCorde(int numSommet1, int numSommet2) throws Exception {

		int a = Math.min(numSommet1, numSommet2);
		int b = Math.max(numSommet1, numSommet2);

		// parametre incorrect
		
		if ( a < 0 ) {
			throw new Exception("case memoire inexistante : " + a);
		}

		if (b >= this.getNbSommets()) {
			throw new Exception("case memoire inexistante : " + b);
		}
		 

		// la distance d'un point a lui-même est 0
		// pas besoin de le stocker en mémoire, la corde ne peut pas exister
		if(a == b) {
			return false;
		}
		// renvoie le résultat précalculé du tableau triangulaire
		return TabCorde[b][a];
	}


	/**  affiche l'état d'existence du tableau de cordes sur la sortie standard
	 **/
	public void affiche() {
		for(int i = 0; i<this.getNbSommets() ;i ++) {
			//for(int j = 0; j<i ;j ++) {	// version courte
			for(int j = 0; j<this.getNbSommets() ;j ++) {	//version longue
				try {
					System.out.println("corde (" + i + ", " + j + ") : " + this.getCorde(i, j));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**  enregistre la corde (numSommet1, numSommet2)
	 *  @param numSommet1 		le numero du premier sommet
	 *  @param numSommet2 		le numero du deuxieme sommet
	 **/
	public void enregistrerCorde(int numSommet1, int numSommet2) {
		try {
			this.setCorde(numSommet1, numSommet2, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int  getLenght() {
		return getNbSommets();
	}



	/** vérifie si la corde (numSommetA1, numSommetA2) est valide
	 * 
	 * @param numSommetA1			premier sommet de la corde
	 * @param numSommetA2			deuxième sommet de la corde
	 * @return						vrai si la corde (numSommetA1, numSommetA2) n'a pas encore été tracée et ne coupe aucune corde dájà tracée
	 */
	public boolean valideCorde(int numSommetA1, int numSommetA2){
		boolean dejaTrace = false;
		try {
			dejaTrace = this.getCorde(numSommetA1, numSommetA2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (dejaTrace == true) {
			// la corde n'est pas valide puisqu'elle a deja ete tracee
			return false;
		}
		
		// vérifications de la validité des paramètres
		if (CordesHelper.valideCordeSimple(numSommetA1, numSommetA2, this.getNbSommets()) == false) {
			return false;
		}
		
		boolean cordeExiste = false;
		// recherche si cette nouvelle corde coupe une corde existante
		for(int i = 0; i<this.getNbSommets()-1 ;i ++) {
			for(int j = 0; j<i ;j ++) {
				try {
					cordeExiste = this.getCorde(i, j);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (cordeExiste == true) {
					// la corde (i, j) existe. On vérifie si la nouvelle corde la coupe					
					if( CordesHelper.coupe (i, j, numSommetA1, numSommetA2, this.getNbSommets()) == true) {
						// elle coupe une corde, et n'est pas valide
						return false;
					}
				}
			}
		}

		// arrive a ce point on sait que la nouvelle corde n'existe pas et qu'elle ne coupe aucune corde existante
		return true;

	}


}
