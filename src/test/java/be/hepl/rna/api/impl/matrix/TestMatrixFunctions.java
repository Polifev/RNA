package be.hepl.rna.api.impl.matrix;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import be.hepl.rna.api.impl.matrix.MatrixFunctions;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;

public class TestMatrixFunctions {
	private static final double EPSILON = 0.00_000_1;
	
	@Test
	public void additionFunctionWorks() {
		DoubleDoubleFunction addition = MatrixFunctions.ADDITION;
		assertEquals(3.0, addition.apply(1.0, 2.0), EPSILON);
		assertEquals(-1.0, addition.apply(-2.0, 1.0), EPSILON);
	}
	
	@Test
	public void substractionFunctionWorks() {
		DoubleDoubleFunction product = MatrixFunctions.SUBSTRACTION;
		assertEquals(1.0, product.apply(3.0, 2.0), EPSILON);
		assertEquals(-1.0, product.apply(2.0, 3.0), EPSILON);
		assertEquals(1.0, product.apply(0.0, -1.0), EPSILON);
	}
	
	@Test
	public void productFunctionWorks() {
		DoubleDoubleFunction product = MatrixFunctions.PRODUCT;
		assertEquals(6.0, product.apply(3.0, 2.0), EPSILON);
		assertEquals(-3.0, product.apply(-1.0, 3.0), EPSILON);
		assertEquals(0.0, product.apply(0.0, -1.0), EPSILON);
	}
	
	@Test
	public void absFunctionWorks() {
		DoubleFunction abs = MatrixFunctions.ABS;
		assertEquals(1.0, abs.apply(1.0), EPSILON);
		assertEquals(1.0, abs.apply(-1.0), EPSILON);
		assertEquals(0.0, abs.apply(0.0), EPSILON);
	}
	
	@Test
	public void squareFunctionWorks() {
		DoubleFunction square = MatrixFunctions.SQUARE;
		assertEquals(1.0, square.apply(1.0), EPSILON);
		assertEquals(4.0, square.apply(2.0), EPSILON);
		assertEquals(4.0, square.apply(-2.0), EPSILON);
		assertEquals(0.0, square.apply(0.0), EPSILON);
	}
}
