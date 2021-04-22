package be.hepl.rna.api.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.function.Function;

import org.junit.Test;

public class TestActivationFunctions {
	
	private static final double EPSILON = 0.00_000_1;
	
	@Test
	public void gettingAnUnknownFunctionThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> ActivationFunctions.get("unknown"));
	}
	
	@Test
	public void gettingAnUnknownDerivateFunctionThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> ActivationFunctions.getDerivated("unknown"));
	}

	@Test
	public void identityWorks() {
		Function<Double, Double> identity = ActivationFunctions.get("identity");
		assertEquals(1.0, identity.apply(1.0), EPSILON);
		assertEquals(0.0, identity.apply(0.0), EPSILON);
		assertEquals(-1.0, identity.apply(-1.0), EPSILON);
	}
	
	@Test
	public void identityDerivatedWorks() {
		Function<Double, Double> identityDerivated = ActivationFunctions.getDerivated("identity");
		assertEquals(1.0, identityDerivated.apply(1.0), EPSILON);
		assertEquals(1.0, identityDerivated.apply(0.0), EPSILON);
		assertEquals(1.0, identityDerivated.apply(-1.0), EPSILON);
	}
	
	@Test
	public void sigmoidWorks() {
		Function<Double, Double> sigmoid = ActivationFunctions.get("sigmoid");
		assertEquals(0.0, sigmoid.apply(-100.0), EPSILON);
		assertEquals(0.5, sigmoid.apply(0.0), EPSILON);
		assertEquals(1.0, sigmoid.apply(100.0), EPSILON);
	}
	
	@Test
	public void sigmoidDerivatedWorks() {
		Function<Double, Double> sigmoidDerivated = ActivationFunctions.getDerivated("sigmoid");
		assertEquals(0.0, sigmoidDerivated.apply(-100.0), EPSILON);
		assertEquals(0.25, sigmoidDerivated.apply(0.0), EPSILON);
		assertEquals(0.0, sigmoidDerivated.apply(100.0), EPSILON);
	}
	
	@Test
	public void tanhWorks() {
		Function<Double, Double> tanh = ActivationFunctions.get("tanh");
		assertEquals(-1.0, tanh.apply(-100.0), EPSILON);
		assertEquals(0.0, tanh.apply(0.0), EPSILON);
		assertEquals(1.0, tanh.apply(100.0), EPSILON);
	}
	
	@Test
	public void tanhDerivatedWorks() {
		Function<Double, Double> tanhDerivated = ActivationFunctions.getDerivated("tanh");
		assertEquals(0.0, tanhDerivated.apply(-100.0), EPSILON);
		assertEquals(1, tanhDerivated.apply(0.0), EPSILON);
		assertEquals(0.0, tanhDerivated.apply(100.0), EPSILON);
	}
	
	@Test
	public void thresholdWorks() {
		Function<Double, Double> threshold = ActivationFunctions.get("threshold");
		assertEquals(0.0, threshold.apply(-2.0), EPSILON);
		assertEquals(0.0, threshold.apply(-1.0), EPSILON);
		assertEquals(0.0, threshold.apply(0.0), EPSILON);
		assertEquals(1, threshold.apply(1.0), EPSILON);
		assertEquals(1, threshold.apply(2.0), EPSILON);
	}
}
