package be.hepl.rna.api.impl;

import static be.hepl.rna.api.impl.Sweep.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestSweep {
	private static final double EPSILON = 0.00_000_1;
	
	@Test
	public void hasRightNumberOfValues() {
		Iterable<Double> sweep = sweep(0, 1, 0.1);
		
		int count = 0;
		for (double d : sweep) { count++; }
		assertEquals(10, count);
	}
	
	@Test
	public void valuesAreCorrect() {
		Iterable<Double> sweep = sweep(0, 1, 0.1);
		
		double expected = 0;
		for (double actual : sweep) {
			assertEquals(expected, actual, EPSILON);
			expected += 0.1;
			
		}
	}
	
	@Test
	public void reverseSweep() {
		Iterable<Double> reverseSweep = sweep(1, 0, -0.1);
		double expected = 1;
		for (double actual : reverseSweep) {
			assertEquals(expected, actual, EPSILON);
			expected -= 0.1;
		}
	}

}
