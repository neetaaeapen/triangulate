package main;

/**  classe gerant les fonctionnalités relatives aux distances  **/
public class GestionDistances {
	
	
	/**  matrice supérieure droite des distances (tableau triangulaire)  **/
	private double Distance[][];
	
	/** nombre de points dans le polygone **/
	int nbpoints;


	/** constructeur
	 * @param xpoints 			les coordonnées en x des points
	 * @param ypoints			les coordonnées en y des points
	 * @param nbpoints			le nombre de points dans le polygone
	 * **/
	public GestionDistances(int[] xpoints, int[] ypoints, int nbpoints) {
		this.nbpoints = nbpoints;
		
		if(xpoints.length != ypoints.length) {
			System.out.println("matrice : données d'entrée invalides");
		}
		
		initDistances(xpoints, ypoints);
	}




	/** initialise les distance entre les sommets stocké dans la variable Distance,
	 * afin de les calculer une unique fois
	 **/
	private void initDistances(int[] xpoints, int[] ypoints) {
		Distance = new double[nbpoints][];
		for (int i = 0; i < Distance.length; i++) {
			Distance[i] = new double[i];
			for (int j = 0; j < i; j++) {
				// racine(deltaX2 + deltaY2)
				Distance[i][j] = Math.sqrt( Math.pow(((xpoints[i])-(xpoints[j])), 2)  +  Math.pow(((ypoints[i])-(ypoints[j])), 2) );
			}
		}
	}


	/** stocke la distance relative à une corde donnée (numSommet1, numSommet2)
	 * @param numSommet1 		le numero du premier sommet
	 * @param numSommet2 		le numero du deuxieme sommet
	 * @param distance			la distance a stocker
	 * **/
	public void setDistance(int numSommet1, int numSommet2, int distance) throws Exception {

		int a = Math.min(numSommet1, numSommet2);
		int b = Math.max(numSommet1, numSommet2);

		// parametre incorrect
		if ( a < 0 ) {
			throw new Exception("case memoire inexistante : " + a);
		}

		if (b >= this.nbpoints) {
			throw new Exception("case memoire inexistante : " + b);
		}

		if((a == b) && ( distance != 0 )) {
			throw new Exception("case " + a + " : la distance d'un point a lui-même doit être 0");
		}

		// stocke le résultat dans le tableau triangulaire
		Distance[b][a] = distance;
	}



	/** renvoie la distance relative à une corde donnée (numSommet1, numSommet2)
	 * @param numSommet1 		le numero du premier sommet de la corde
	 * @param numSommet2 		le numero du deuxieme sommet de la corde
	 * @return					la longueur de la corde (numSommet1, numSommet2)
	 * **/
	public double getDistance(int numPt1, int numPt2) {

		int a = Math.min(numPt1, numPt2);
		int b = Math.max(numPt1, numPt2);

		// parametre incorrect
		if ( a < 0 ) {
			System.out.println("Erreur : case memoire inexistante : " + a);
			return 0.0;
		}

		if (b >= this.nbpoints) {
			System.out.println("Erreur : case memoire inexistante : " + b);
			return 0.0;
		}

		// la distance d'un point a lui-même est 0
		if(a == b) {
			return 0.0;
		}
		// renvoie le résultat précalculé du tableau triangulaire
		return Distance[b][a];
	}


	/** affiche les distances stockées pour chaque corde **/
	public void affiche() {
		//affiche distances
		for(int i = 0; i<this.nbpoints-1 ;i ++) {
			for(int j = 0; j<i ;j ++) {					// sans doublons
			//for(int j = 0; j<this.nbpoints-1 ;j ++) {		// avec doublons (a,b) (b,a)
				try {
					System.out.println("distance de " + i + " a : " + j + " : " + this.getDistance(i, j));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/** retourne le nombre de points du polygone **/
	public int  getLenght() {
		return nbpoints;
	}

}
