package be.hepl.rna.math;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

public class DoubleVectorTest {

	static {
		Locale.setDefault(Locale.US);
	}

	@Test
	public void createFromSize() {
		DoubleVector v = new DoubleVector(3);
		assertEquals(3, v.dimension());
		assertEquals(0, v.element(0), Constants.EPSILON);
		assertEquals(0, v.element(1), Constants.EPSILON);
		assertEquals(0, v.element(2), Constants.EPSILON);
	}

	@Test
	public void createFromArray() {
		DoubleVector v = new DoubleVector(new double[] { 1.0, 2.0, 3.0 });
		assertEquals(3, v.dimension());
		assertEquals(1.0, v.element(0), Constants.EPSILON);
		assertEquals(2.0, v.element(1), Constants.EPSILON);
		assertEquals(3.0, v.element(2), Constants.EPSILON);
	}

	@Test
	public void createFromEllipse() {
		DoubleVector v = new DoubleVector(1.0, 2.0, 3.0);
		assertEquals(3, v.dimension());
		assertEquals(1.0, v.element(0), Constants.EPSILON);
		assertEquals(2.0, v.element(1), Constants.EPSILON);
		assertEquals(3.0, v.element(2), Constants.EPSILON);
	}

	@Test
	public void eqDimEqVal() {
		DoubleVector u = new DoubleVector(new double[] { 1.0, 2.0, 1.0 });
		DoubleVector v = new DoubleVector(new double[] { 1.0, 2.0, 1.0 });
		assertTrue(u.equals(v));
	}

	@Test
	public void neqDimEqVal() {
		DoubleVector u = new DoubleVector(new double[] { 1.0, 2.0, 1.0 });
		DoubleVector v = new DoubleVector(new double[] { 1.0, 2.0, 1.0, 2.0 });
		assertFalse(u.equals(v));
	}

	@Test
	public void eqDimNeqVal() {
		DoubleVector u = new DoubleVector(new double[] { 1.0, 2.0, 1.0 });
		DoubleVector v = new DoubleVector(new double[] { 1.0, 3.0, 1.0 });
		assertFalse(u.equals(v));
	}

	@Test
	public void computesCrossProduct() {
		DoubleVector u = new DoubleVector(new double[] { 1.0, 2.0, 1.0 });
		DoubleVector v = new DoubleVector(new double[] { 2.0, 1.0, 1.0 });
		assertEquals(5, u.crossProduct(v), Constants.EPSILON);
	}

	@Test
	public void crossProductFailsIfDifferentDimensions() {
		DoubleVector u = new DoubleVector(new double[] { 1.0, 2.0, 1.0 });
		DoubleVector v = new DoubleVector(new double[] { 2.0, 1.0, 1.0, 2.0 });
		try {
			u.crossProduct(v);
			fail("Exception not thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("You cannot cross vectors with different dimensions.", e.getMessage());
		}
	}

	@Test
	public void toStringWorks() {
		DoubleVector v = new DoubleVector(new double[] { 2.0, 1.0, 1.0 });
		assertEquals("[2.00; 1.00; 1.00]", v.toString());
	}
}
