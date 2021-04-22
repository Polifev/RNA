package be.hepl.rna.api.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import static be.hepl.rna.api.impl.Predicates.*;

public class TestPredicates {
	
	@Test
	public void testGreater() {
		assertTrue(greaterThan(1).test(2.0));
		assertFalse(greaterThan(2.0).test(2.0));
		assertFalse(greaterThan(3).test(2.0));
	}

	@Test
	public void testGreaterOrEqual() {
		assertTrue(greaterOrEqualTo(1).test(2.0));
		assertTrue(greaterOrEqualTo(2.0).test(2.0));
		assertFalse(greaterOrEqualTo(3).test(2.0));
	}
	
	@Test
	public void testLess() {
		assertFalse(lessThan(1).test(2.0));
		assertFalse(lessThan(2.0).test(2.0));
		assertTrue(lessThan(3).test(2.0));
	}

	@Test
	public void testLessOrEqual() {
		assertFalse(lessOrEqualTo(1).test(2.0));
		assertTrue(lessOrEqualTo(2.0).test(2.0));
		assertTrue(lessOrEqualTo(3).test(2.0));
	}
}
