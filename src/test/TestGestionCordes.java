package test;
import junit.framework.Assert;

import org.javatuples.Pair;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestGestionCordes {

	@Rule
	  public ExpectedException exception = ExpectedException.none();
	

	@Test
	public void testValideCorde4() {
		//test sur un polygone a 4 sommets
		main.GestionCordes corde = new main.GestionCordes(4);

		Assert.assertEquals(false,  corde.valideCorde(3, 2));
		Assert.assertEquals(true,  corde.valideCorde(1, 3));
		
		corde.enregistrerCorde(1, 3);
		Assert.assertEquals(false,  corde.valideCorde(3, 1));
		Assert.assertEquals(2,  corde.getNbCordes());
		
		System.out.println("Cordes possibles du polygone a 4 sommets : " + corde.getNbCordes());
		
		Assert.assertEquals(corde.getNbCordes(), (4  * (4 -3)) /2);
		
		for (Pair<Integer, Integer> p : corde.getCordesValides()) {
			System.out.println(p.toString());
		}
		
	}

	
	@Test
	public void testValideCorde5() {
		//test sur un polygone a 5 sommets
		main.GestionCordes corde = new main.GestionCordes(5);
		
		Assert.assertEquals(false,  corde.valideCorde(3, 2));
	
		
		Assert.assertEquals(true,  corde.valideCorde(1, 3));
		corde.enregistrerCorde(1, 3);
		Assert.assertEquals(false,  corde.valideCorde(3, 1));		

		
		System.out.println("Cordes possibles du polygone a 5 sommets : " + corde.getNbCordes());
		Assert.assertEquals(corde.getNbCordes(), (5  * (5 -3)) /2);
		for (Pair<Integer, Integer> p : corde.getCordesValides()) {
			System.out.println(p.toString());
		}
	}
	
	@Test
	public void testValideCorde10() {
		//test sur un polygone a 10 sommets
		main.GestionCordes corde = new main.GestionCordes(10);
		
		Assert.assertEquals(false,  corde.valideCorde(3, 2));
		Assert.assertEquals(true,  corde.valideCorde(1, 9));
		
		
		Assert.assertEquals(true,  corde.valideCorde(1, 3));
		corde.enregistrerCorde(1, 3);
		Assert.assertEquals(false,  corde.valideCorde(3, 1));		

		
		System.out.println("Cordes possibles du polygone a 10 sommets : " + corde.getNbCordes());
		Assert.assertEquals(corde.getNbCordes(), (10  * (10 -3)) /2);
		for (Pair<Integer, Integer> p : corde.getCordesValides()) {
			System.out.println(p.toString());
		}
	}
	
	@Test
	public void testValideCorde10Exception() {
		main.GestionCordes corde = new main.GestionCordes(10);
		// les exceptions sont normales
		// puisqu'on cherche a acceder au sommet no 10 alors que le plus grand sommet est 9
		try {
			corde.valideCorde(1, 10);
		} catch (Exception e) {
		}
	}
	
}
