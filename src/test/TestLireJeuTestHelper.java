package test;

import java.util.ArrayList;

import jeuxDeTest.LireJeuTestHelper;
import org.junit.Test;

public class TestLireJeuTestHelper {

	@Test
	public void test() {

		ArrayList<int[]> tabCoord = LireJeuTestHelper.lireFichierTest("JeuxDeTest/test");

		//affichage
		boolean isX = true;
		int[] is;
		int numPoly = 1;

		for (int i=0 ; i < tabCoord.size() ; i++) {

			is = tabCoord.get(i);

			// affiche si coord X ou Y
			if(isX){
				System.out.println();
				System.out.print("polynome " + numPoly + " coordonnees X : ");
				isX = false;

			}
			else {
				System.out.print("polynome " + numPoly + " coordonnees Y : ");
				isX = true;
				numPoly++;
			}

			// affiche coordonnees
			System.out.print("\t");
			for (int j : is) {
				System.out.print(j + " ");
			}
			System.out.println();
		}


	}

}
