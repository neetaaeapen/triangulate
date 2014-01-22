package jeuxDeTest;

import java.util.ArrayList;

public class LireJeuTest {

	
	private ArrayList<int[]> tabCoord;
	private int jeuCourant = 0;
	private int tailleJeu = 0;
	
	public LireJeuTest(String chemin){
		this.tabCoord = LireJeuTestHelper.lireFichierTest(chemin);
		tabCoord.trimToSize();
		tailleJeu = (tabCoord.size()/2);
	}
	
	/// getters and setters ///
	
	public int getTailleJeu() {
		return tailleJeu;
	}

	/// fin getters and setters ///
	
	
	public int[][] getNextPolygon(){
		// on ne verifie pas la taille ici.
		int[] X = getXcourant();
		int[] Y = getYcourant();
		int[][] res = new int[][]{X, Y};
		jeuCourant = jeuCourant +2;	// car il y a 2 lignes par polygone
		return res;
	}
	
	private int[] getXcourant(){
		return tabCoord.get(jeuCourant);		
	}
	
	private int[] getYcourant(){
		return tabCoord.get(jeuCourant+1);	
	}

	
	
}
