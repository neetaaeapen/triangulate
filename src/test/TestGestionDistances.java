package test;

import junit.framework.Assert;

import org.junit.Test;

public class TestGestionDistances {

	@Test
	public void testValideDistances7() {
		//test sur heptagone donné
		int tabcoordX[]={  0,  0,  8,  15, 27, 22, 10};
		int tabcoordY[]={  10, 20, 26, 26, 21, 12, 0};
		main.GestionDistances distances = new  main.GestionDistances(tabcoordX, tabcoordY, tabcoordX.length);
		//distances.affiche();	//pour comparaisons avec fichier tableur de reference

		double d = 0;
		d = d + distances.getDistance(0, 2);
		d = d + distances.getDistance(0, 3);
		d = d + distances.getDistance(0, 5);
		d = d + distances.getDistance(3, 5);

		System.out.println("Distance Triangulation 7 : " + d);
		Assert.assertEquals(d, 77.56345389633267);
	}
}
