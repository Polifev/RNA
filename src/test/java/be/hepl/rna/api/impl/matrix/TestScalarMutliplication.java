package be.hepl.rna.api.impl.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.colt.function.DoubleFunction;


public class TestScalarMutliplication {
	private static final double EPSILON = 0.00_000_1;

	@Test
	public void multiplyByTheParameter()  {
		// Given
		DoubleFunction times2 = new ScalarMultiplication(2.0);
		DoubleFunction times3andHalf = new ScalarMultiplication(3.5);
		
		// Then
		assertEquals(4.0, times2.apply(2.0), EPSILON);
		assertEquals(7.0, times3andHalf.apply(2.0), EPSILON);
	}
}
