package test;

import org.junit.Assert;
import org.junit.Test;

import progDynamique.DynamiqueHelper;

public class TestDynamiqueHelper {

	@Test
	public void test() {
		Assert.assertEquals(6, DynamiqueHelper.distanceNumSommets(0, 6, 0, 6));
		Assert.assertEquals(6, DynamiqueHelper.distanceNumSommets(0, 6, 6, 5));
		Assert.assertEquals(6, DynamiqueHelper.distanceNumSommets(0, 6, 6, 5));
		Assert.assertEquals(1, DynamiqueHelper.distanceNumSommets(0, 6, 6, 0));
		Assert.assertEquals(3, DynamiqueHelper.distanceNumSommets(0, 3, 2, 1));
		Assert.assertEquals(2, DynamiqueHelper.distanceNumSommets(0, 3, 0, 2));
		Assert.assertEquals(6, DynamiqueHelper.distanceNumSommets(0, 6, 5, 4));
	}

}
