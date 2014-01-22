package test;

import org.javatuples.Triplet;
import org.junit.Test;

import algoGlouton.Glouton;

public class TestGlouton {

	@Test
	public void test() {
		
		int X[]={ 0,  0,  8,  15, 27, 22, 10 };
		int Y[]={ 10, 20, 26, 26, 21, 12, 0 };
//
//				int X[]={ 617, 278, 227, 734, 676 };
//				int Y[]={256, 394, 736, 615, 426 };

		System.out.println("algorithme glouton");
		Glouton gl = new Glouton(X, Y, Math.min(X.length, Y.length));

		gl.rechercheTriangulation();

		System.out.println("Distance : " + gl.getDistanceSolution());
		for (Triplet<Integer, Integer, Double> i : gl.getSolution()) {
			System.out.println(i);
		}
	}
}
