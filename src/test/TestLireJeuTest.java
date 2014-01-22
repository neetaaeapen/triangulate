package test;

import static org.junit.Assert.*;
import jeuxDeTest.LireJeuTest;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestLireJeuTest {

	@Test
	public void test() {

		int numPoly = 1;

		LireJeuTest nouveauJeu = new LireJeuTest("JeuxDeTest/test");
		for(int i=0 ; i<(nouveauJeu.getTailleJeu()) ; i++ ) {
			int[][] polygon = nouveauJeu.getNextPolygon();
			int[] X = polygon[0];
			int[] Y = polygon[1];



			System.out.println("polynome " + numPoly );
			// affiche coordonnees X
			System.out.print("\t coordonnees X : ");
			for (int j : X) {
				System.out.print(j + " ");
			}
			System.out.println();

			// affiche coordonnees Y
			System.out.print("\t coordonnees Y : ");
			for (int j : Y) {
				System.out.print(j + " ");
			}
			System.out.println("\n");


			numPoly++;
		}


	}

}
