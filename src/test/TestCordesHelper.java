package test;

import main.CordesHelper;

import org.junit.Assert;
import org.junit.Test;

public class TestCordesHelper {

	@Test
	public void testCoupe() {
		Assert.assertEquals(true, CordesHelper.coupe(1, 2, 3, 4, 7));
		Assert.assertEquals(true, CordesHelper.coupe(1, 7, 5, 9, 10));
		Assert.assertEquals(true, CordesHelper.coupe(1, 7, 5, 9, 6));
		Assert.assertEquals(false, CordesHelper.coupe(1, 7, 8, 12, 15));
		Assert.assertEquals(true, CordesHelper.coupe(1, 3, 0, 2, 4));
	}


}
