package be.hepl.rna.api.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.hepl.rna.api.IWeightsInitializer;

public class TestZeroWeightsInitializer {
	
	private static final double EPSILON = 0.00_000_1;
	
	@Test
	public void returnsZero() {
		IWeightsInitializer initializer = new ZeroWeightsInitializer();
		assertEquals(0, initializer.getWeight(0, 0), EPSILON);
		assertEquals(0, initializer.getWeight(4, 8), EPSILON);
		assertEquals(0, initializer.getWeight(-1, 50), EPSILON);
	}
	
}
